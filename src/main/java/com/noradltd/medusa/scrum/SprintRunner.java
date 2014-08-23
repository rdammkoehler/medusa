package com.noradltd.medusa.scrum;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class SprintRunner {
	private final JsonObject sprint;
	private final SprintResult sprintResult;
	private final Set<Card> notStarted;
	private final Set<Card> notDone;
	private final Set<Card> done;
	private final Set<Card> verified;
	private final Set<Defect> defectsCreated;
	private final Set<SDeveloper> developers;
	private final Set<SprintResultListener> resultListeners = new HashSet<SprintResultListener>();

	@Deprecated
	private void log(String message) {
		// diag method while I'm messing around
		System.out.println(message);
	}

	public SprintRunner(JsonObject sprint, Set<SprintResultListener> resultListeners) {
		this.sprint = sprint;
		this.resultListeners.addAll(resultListeners);
		sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(sprint.toString());
		notStarted = sprintResult.getNotStarted();
		notDone = sprintResult.getNotDone();
		done = sprintResult.getDone();
		verified = sprintResult.getVerified();
		defectsCreated = sprintResult.getDefectsCreated();
		developers = new HashSet<SDeveloper>();
	}

	public void runSprint() {
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
		log(developer + " will work on " + developer.getAssignedCard());
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
		if (developer.getAssignedCard().shouldCreateDefect) {
			defectsCreated.add(new Defect(developer.getAssignedCard()));
			log(developer + " introduced a defect in " + developer.getAssignedCard());
		}
	}

	private void moveCardToDone(SDeveloper developer) {
		notDone.remove(developer.getAssignedCard());
		done.add(developer.getAssignedCard());
		done.remove(developer.getAssignedCard());
		verified.add(developer.getAssignedCard());
		log(developer + " finished " + developer.getAssignedCard());
	}

	private void assignCards() {
		for (SDeveloper developer : developers) {
			if (developer.isAvailable()) {
				assignCardTo(developer);
			} else {
				log(developer + " is not available [" + developer.getAssignedCard() + "]");
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
		developers.clear();
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

class SCard extends Card {
	public SCard(JsonObject cardJson) {
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

	private Card assignedCard = null;
	private Integer remainingWork = 0;

	public SDeveloper(JsonObject developerJson) {
		super();
		id = developerJson.get("id").getAsInt();
		velocity = developerJson.get("velocity").getAsInt();
		lift = developerJson.get("lift").getAsInt();
		drag = developerJson.get("drag").getAsInt();
	}

	public Card getAssignedCard() {
		return assignedCard;
	}

	public Integer getRemainingWork() {
		return remainingWork;
	}

	public void work() {
		remainingWork -= 1;
	}

	public void workOn(Card card) {
		this.assignedCard = card;
		this.remainingWork = card.size;
	}

	public boolean isAvailable() {
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

