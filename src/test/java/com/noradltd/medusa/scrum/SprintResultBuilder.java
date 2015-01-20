package com.noradltd.medusa.scrum;

import java.util.Arrays;
import java.util.Collection;

public class SprintResultBuilder {

	private SprintResult sprintResult = new SprintResult();

	public SprintResultBuilder withOriginalSprintData(String data) {
		sprintResult.setOriginalSprintData(data);
		return this;
	}

	public SprintResultBuilder withVerified(Collection<Card> cards) {
		sprintResult.addVerified(cards);
		return this;
	}

	public SprintResultBuilder withVerified(Card... cards) {
		sprintResult.addVerified(Arrays.asList(cards));
		return this;
	}

	public SprintResultBuilder withNotStarted(Collection<Card> cards) {
		sprintResult.addNotStarted(cards);
		return this;
	}

	public SprintResultBuilder withNotStarted(Card... cards) {
		sprintResult.addNotStarted(Arrays.asList(cards));
		return this;
	}

	public SprintResultBuilder withDefect(Collection<Defect> defects) {
		sprintResult.addDefects(defects);
		return this;
	}

	public SprintResultBuilder withDefect(Defect... defects) {
		sprintResult.addDefects(Arrays.asList(defects));
		return this;
	}

	public SprintResultBuilder withDeveloperIdleDays(Integer developerIdleDays) {
		for (int idx = 0; idx < developerIdleDays; idx++) {
			sprintResult.incDeveloperIdleDays();
		}
		return this;
	}

	public SprintResult build() {
		return sprintResult;
	}

	public SprintResult buildWithDefaults() {
		return build();
	}
}
