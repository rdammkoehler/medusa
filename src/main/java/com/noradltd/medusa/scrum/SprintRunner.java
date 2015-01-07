package com.noradltd.medusa.scrum;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class SprintRunner {
	private final JsonObject sprint;
	private final SprintResult sprintResult;
	private final Set<SDeveloper> developers;
	private final Set<SprintResultListener> resultListeners = new HashSet<SprintResultListener>();

	public SprintRunner(JsonObject sprint, Set<SprintResultListener> resultListeners) {
		this.sprint = sprint;
		this.resultListeners.addAll(resultListeners);
		sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(sprint.toString());
		developers = new HashSet<SDeveloper>();
	}

	public void runSprint() {
		loadCards();
		loadDevelopers();

		System.out.println("SPRINT---");
		final int days = sprint.get("days").getAsInt();
		for (int day = 0; day < days; day++) {
			new CardAssigner(sprintResult, developers).assignCards();
			doADaysWork();
		}
		for (SprintResultListener listener : resultListeners) {
			listener.sprintComplete(sprintResult);
		}
	}

	private void doADaysWork() {
		System.out.println("---DAY");
		for (SDeveloper developer : developers) {
			if (developer.isWorking()) {
				workACard(developer);
			} else {
				System.out.println(developer + " is a slacker!");
				recordSlackTime();
			}
		}
	}

	private void workACard(SDeveloper developer) {
		developer.work();
		if (developer.isDone()) {
			new CardCompleter(sprintResult, developer.getAssignedCard()).moveCardToDone();
		}
	}

	private void recordSlackTime() {
		sprintResult.incDeveloperIdleDays();
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
		int size = cardsJson.size();
		for (int cardIdx = 0; cardIdx < size; cardIdx++) {
			sprintResult.addNotStarted(new SCard(cardsJson.get(cardIdx).getAsJsonObject()));
		}
	}
}

class SCard extends Card {
	public SCard(JsonObject cardJson) {
		super(Card.Size.forInt(cardJson.get("size").getAsInt()));
		this.id = cardJson.get("id").getAsInt();
		this.shouldCreateDefect = cardJson.get("shouldCreateDefect").getAsBoolean();
	}

//	@Override
//	public String toString() {
//		return new StringBuilder("Card ").append(id).append("(").append(size).append(")").toString();
//	}
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

//	public Integer getRemainingWork() {
//		return remainingWork;
//	}

	public void work() {
		System.out.println(this + " is working on " + assignedCard);
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

