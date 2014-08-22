package com.noradltd.medusa.scrum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SprintClock implements SprintResultListener {

	private InputStream stream;
	private Integer cycles;
	private Set<SprintResultListener> resultListeners = new HashSet<SprintResultListener>();

	public SprintClock() {
		addResultListener(this);
	}
	
	private void log(String message) {
		// diag method while I'm messing around
		System.out.println(message);
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public void run() throws IOException {
		cycles = 0;
		String json = readEntireInputStream();
		try {
			JsonElement element = new JsonParser().parse(json);
			if (element.isJsonArray()) {
				JsonArray sprints = element.getAsJsonArray();
				for (int idx = 0; idx < sprints.size(); idx++) {
					JsonObject sprint = sprints.get(idx).getAsJsonObject();
					new SprintRunner(sprint, resultListeners).runSprint();
				}
			}
		} catch (JsonSyntaxException e) {
			System.err.println("Input was not in a readable JSON format");
		}
	}
	
	@Override
	public void sprintComplete(SprintResult result) {
		if (getCardCount(result) > 0) {
			cycles++;
		}
	}

	private int getCardCount(SprintResult result) {
		return new JsonParser().parse(result.getOriginalSprintData()).getAsJsonObject().get("cards").getAsJsonArray().size();
	}
	
	class SprintRunner {
		private JsonObject sprint;
		private SprintResult sprintResult;
		private Set<Card> notStarted;
		private Set<Card> notDone;
		private Set<Card> done;
		private Set<Card> verified;
		private Set<Defect> defectsCreated;
		private Set<SDeveloper> developers;

		public SprintRunner(JsonObject sprint, Set<SprintResultListener> resultListeners) {
			this.sprint = sprint;
			sprintResult = new SprintResult();
			sprintResult.setOriginalSprintData(sprint.toString());
			notStarted = sprintResult.getNotStarted();
			notDone = sprintResult.getNotDone();
			done = sprintResult.getDone();
			verified = sprintResult.getVerified();
			defectsCreated = sprintResult.getDefectsCreated();
		}

		class SCard extends Card {
			SCard(JsonObject cardJson) {
				super(Card.Size.forInt(cardJson.get("size").getAsInt()));
				this.id = cardJson.get("id").getAsInt();
				this.shouldCreateDefect = cardJson.get("shouldCreateDefect").getAsBoolean();
			}

			@Override
			public String toString() {
				return new StringBuilder("Card ").append(id).append("(").append(size).append(")").toString();
			}
		}

		class SDeveloper extends Developer {
			Card card = null;
			Integer remainingWork = 0;

			SDeveloper(JsonObject developerJson) {
				super();
				id = developerJson.get("id").getAsInt();
				velocity = developerJson.get("velocity").getAsInt();
				lift = developerJson.get("lift").getAsInt();
				drag = developerJson.get("drag").getAsInt();
			}

			void work() {
				remainingWork -= 1;
			}

			void workOn(Card card) {
				this.card = card;
				this.remainingWork = card.size;
			}

			boolean isAvailable() {
				return remainingWork <= 0;
			}

			@Override
			public String toString() {
				return new StringBuilder("Dev ").append(id).append("(").append(remainingWork).append(")").toString();
			}

			public boolean isDone() {
				return remainingWork <= 0;
			}

			public boolean isWorking() {
				return !isAvailable();
			}
		}

		private void runSprint() {
			loadCards();
			loadDevelopers();

			final int days = sprint.get("days").getAsInt();
			for (int day = 0; day < days; day++) {
				log("Day " + day + ": " + sprintResult);
				assignCards();
				doADaysWork();
			}
			for (SprintResultListener listener : resultListeners) {
				listener.sprintComplete(sprintResult);
			}
		}

		private void doADaysWork() {
			for (SDeveloper developer : developers) {
				if (developer.isWorking()) {
					workACard(developer);
				} else {
					recordSlackTime(developer);
				}
			}
		}

		private void workACard(SDeveloper developer) {
			log(developer + " will work on " + developer.card);
			developer.work();
			if (developer.isDone()) {
				moveCardToDone(developer);
				checkCardForDefects(developer);
			}
		}

		private void recordSlackTime(SDeveloper developer) {
			log(developer + " is idle");
			sprintResult.incDeveloperIdleDays();
		}

		private void checkCardForDefects(SDeveloper developer) {
			if (developer.card.shouldCreateDefect) {
				defectsCreated.add(new Defect(developer.card));
				log(developer + " introduced a defect in " + developer.card);
			}
		}

		private void moveCardToDone(SDeveloper developer) {
			notDone.remove(developer.card);
			done.add(developer.card);
			done.remove(developer.card);
			verified.add(developer.card);
			log(developer + " finished " + developer.card);
		}

		private void assignCards() {
			for (SDeveloper developer : developers) {
				if (developer.isAvailable()) {
					assignCardTo(developer);
				} else {
					log(developer + " is not available [" + developer.card + "]");
				}
			}
		}

		private void assignCardTo(SDeveloper developer) {
			try {
				Card card = notStarted.iterator().next();
				notStarted.remove(card);
				notDone.add(card);
				developer.workOn(card);
			} catch (NoSuchElementException nsee) {
				log("out of cards");
			}
		}

		private void loadDevelopers() {
			JsonObject team = sprint.getAsJsonObject("team");
			JsonArray developersJson = team.getAsJsonArray("developers");
			developers = new HashSet<SDeveloper>();
			for (JsonElement developerJson : developersJson) {
				SDeveloper e = new SDeveloper(developerJson.getAsJsonObject());
				developers.add(e);
			}
		}

		private void loadCards() {
			JsonArray cardsJson = sprint.getAsJsonArray("cards");
			for (int cardIdx = 0; cardIdx < cardsJson.size(); cardIdx++) {
				notStarted.add(new SCard(cardsJson.get(cardIdx).getAsJsonObject()));
			}
		}
	}

	private String readEntireInputStream() throws IOException {
		BufferedReader reader = createInputStreamReader();
		StringBuilder sb = new StringBuilder();
		try {
			boolean fini = false;
			while (reader.ready() && !fini) {
				String line = reader.readLine();
				if (null == line) {
					fini = true;
				} else {
					sb.append(line);
				}
			}
		} finally {
			reader.close();
		}
		return sb.toString();
	}

	private BufferedReader createInputStreamReader() {
		return new BufferedReader((null == stream) ? new StringReader("") : new InputStreamReader(stream));
	}

	public Integer getCycles() {
		return cycles;
	}

	public void addResultListener(SprintResultListener sprintResultListener) {
		resultListeners.add(sprintResultListener);
	}

}
