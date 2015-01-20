package com.noradltd.medusa.scrum;

import java.util.Set;

import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({ "PMD.GodClass", "PMD.TooManyMethods" })
public final class SprintResultMatcher extends AbstractAssert<SprintResultMatcher, SprintResult> {

	protected SprintResultMatcher(SprintResult actual) {
		super(actual, SprintResultMatcher.class);
	}

	public static SprintResultMatcher assertThat(SprintResult sprintResult) {
		return new SprintResultMatcher(sprintResult);
	}

	public SprintResultMatcher isValid() {
		if (actual.getOriginalSprintData().isEmpty()) {
			failWithMessage("Sprint Result has no original Sprint Data", actual.getOriginalSprintData());
		}
		if ( actual.getDone().size() + actual.getNotDone().size() + actual.getNotStarted().size()
				+ actual.getVerified().size() <= 0) {
			failWithMessage("Sprint Result has no cards", actual);
		}
		return this;
	}
	
	public SprintResultMatcher hasOriginalSprintData(String sprintData) {
		if (!actual.getOriginalSprintData().equals(sprintData)) {
			failWithMessage("Original Sprint Data does not match", sprintData, actual.getOriginalSprintData());
		}
		return this;
	}

	private void cardsContain(String listname, Set<? extends Card> list, Card... cards) {
		for (Card card : cards) {
			if (!list.contains(card)) {
				failWithMessage(listname + " Cards does not contain expected card(s)", cards, actual.getVerified());
			}
		}
	}

	private void cardsDoNotContain(String listname, Set<? extends Card> list, Card... cards) {
		for (Card card : cards) {
			if (list.contains(card)) {
				failWithMessage(listname + " Cards contain unexpected card(s)", cards, actual.getVerified());
			}
		}
	}

	public SprintResultMatcher verifiedCardsContains(Card... cards) {
		cardsContain("Verified", actual.getVerified(), cards);
		return this;
	}

	public SprintResultMatcher verifiedCardsDoesNotContain(Card... cards) {
		cardsDoNotContain("Verified", actual.getVerified(), cards);
		return this;
	}

	public SprintResultMatcher whereDoneCardsContains(Card... cards) {
		cardsContain("Done", actual.getDone(), cards);
		return this;
	}

	public SprintResultMatcher whereDoneCardsDoesNotContain(Card... cards) {
		cardsDoNotContain("Done", actual.getDone(), cards);
		return this;
	}

	public SprintResultMatcher whereNotDoneCardsContains(Card... cards) {
		cardsContain("Not Done", actual.getNotDone(), cards);
		return this;
	}

	public SprintResultMatcher whereNotDoneCardsDoesNotContain(Card... cards) {
		cardsDoNotContain("Not Done", actual.getNotDone(), cards);
		return this;
	}

	public SprintResultMatcher whereNotStartedCardsContains(Card... cards) {
		cardsContain("Not Started", actual.getNotStarted(), cards);
		return this;
	}

	public SprintResultMatcher whereNotStartedCardsDoesNotContain(Card... cards) {
		cardsDoNotContain("Not Started", actual.getNotStarted(), cards);
		return this;
	}

	public SprintResultMatcher defectsCreatedContains(Defect... defects) {
		cardsContain("Defects", actual.getDefectsCreated(), defects);
		return this;
	}

	public SprintResultMatcher defectsCreatedDoesNotContain(Defect... defects) {
		cardsDoNotContain("Defects", actual.getDefectsCreated(), defects);
		return this;
	}

	public SprintResultMatcher hasDeveloperIdleDays(Integer days) {
		if (!actual.getDeveloperIdleDays().equals(days)) {
			failWithMessage("Developer Idle Days not equal to expected", days, actual.getDeveloperIdleDays());
		}
		return this;
	}

	public SprintResultMatcher hasDeveloperIdleDaysBetween(Integer low, Integer high) {
		if (!(actual.getDeveloperIdleDays() >= low && actual.getDeveloperIdleDays() <= high)) {
			failWithMessage("Developer Idle Days not in expected range", low, high, actual.getDeveloperIdleDays());
		}
		return this;
	}

	public SprintResultMatcher hasVerifiedCards() {
		if (actual.getVerified().isEmpty()) {
			failWithMessage("Expected some verified cards", actual.getVerified());
		}
		return this;
	}

	public SprintResultMatcher thatHaveDoneCards() {
		if (actual.getDone().isEmpty()) {
			failWithMessage("Expected some done cards", actual.getDone());
		}
		return this;
	}

	public SprintResultMatcher thatHaveNotDoneCards() {
		if (actual.getNotDone().isEmpty()) {
			failWithMessage("Expected some not done cards", actual.getNotDone());
		}
		return this;
	}

	public SprintResultMatcher hasNotStartedCards() {
		if (actual.getNotStarted().isEmpty()) {
			failWithMessage("Expected some not started cards", actual.getNotStarted());
		}
		return this;
	}

	public SprintResultMatcher hasDefectsCreated() {
		if (actual.getDefectsCreated().isEmpty()) {
			failWithMessage("Expected some defects created cards", actual.getDefectsCreated());
		}
		return this;
	}
}
