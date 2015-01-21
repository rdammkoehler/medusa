package com.noradltd.medusa.scrum;

import java.util.Collection;
import java.util.Set;

import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({ "PMD.GodClass", "PMD.TooManyMethods" })
public final class SprintResultAssert extends AbstractAssert<SprintResultAssert, SprintResult> {

	private static final String DEVELOPER_IDLE_DAYS_NOT_IN_EXPECTED_RANGE = "Developer Idle Days not in expected range";
	private static final String DEVELOPER_IDLE_DAYS_NOT_EQUAL_TO_EXPECTED = "Developer Idle Days not equal to expected";
	private static final String DEFECTS_CARD_LIST_NAME = "Defects";
	private static final String NOT_STARTED_CARD_LIST_NAME = "Not Started";
	private static final String NOT_DONE_CARD_LIST_NAME = "Not Done";
	private static final String DONE_CARD_LIST_NAME = "Done";
	private static final String VERIFIED_CARD_LIST_NAME = "Verified";
	private static final String CARDS_CONTAIN_UNEXPECTED_CARD_S = " Cards contain unexpected card(s)";
	private static final String CARDS_DOES_NOT_CONTAIN_EXPECTED_CARD_S = " Cards does not contain expected card(s)";
	private static final String ORIGINAL_SPRINT_DATA_DOES_NOT_MATCH = "Original Sprint Data does not match";
	private static final String SPRINT_RESULT_HAS_NO_CARDS = "Sprint Result has no cards";
	private static final String SPRINT_RESULT_HAS_NO_ORIGINAL_SPRINT_DATA = "Sprint Result has no original Sprint Data";

	protected SprintResultAssert(SprintResult actual) {
		super(actual, SprintResultAssert.class);
	}

	public static SprintResultAssert assertThat(SprintResult sprintResult) {
		return new SprintResultAssert(sprintResult);
	}

	// this could be significantly better
	public SprintResultAssert isValid() {
		if (actual.getOriginalSprintData().isEmpty()) {
			failWithMessage(SPRINT_RESULT_HAS_NO_ORIGINAL_SPRINT_DATA, actual.getOriginalSprintData());
		}
		if (actual.getDone().size() + actual.getNotDone().size() + actual.getNotStarted().size()
				+ actual.getVerified().size() <= 0) {
			failWithMessage(SPRINT_RESULT_HAS_NO_CARDS, actual);
		}
		return this;
	}

	public SprintResultAssert hasOriginalSprintData(String sprintData) {
		if (!actual.getOriginalSprintData().equals(sprintData)) {
			failWithMessage(ORIGINAL_SPRINT_DATA_DOES_NOT_MATCH, sprintData, actual.getOriginalSprintData());
		}
		return this;
	}

	private void cardsContain(String listname, Set<? extends Card> list, Card... cards) {
		for (Card card : cards) {
			if (!list.contains(card)) {
				failWithMessage(listname + CARDS_DOES_NOT_CONTAIN_EXPECTED_CARD_S, cards, actual.getVerified());
			}
		}
	}

	private void cardsDoNotContain(String listname, Set<? extends Card> list, Card... cards) {
		for (Card card : cards) {
			if (list.contains(card)) {
				failWithMessage(listname + CARDS_CONTAIN_UNEXPECTED_CARD_S, cards, actual.getVerified());
			}
		}
	}

	public SprintResultAssert verifiedCardsContains(Card... cards) {
		cardsContain(VERIFIED_CARD_LIST_NAME, actual.getVerified(), cards);
		return this;
	}

	public SprintResultAssert verifiedCardsDoesNotContain(Card... cards) {
		cardsDoNotContain(VERIFIED_CARD_LIST_NAME, actual.getVerified(), cards);
		return this;
	}

	public SprintResultAssert whereDoneCardsContains(Card... cards) {
		cardsContain(DONE_CARD_LIST_NAME, actual.getDone(), cards);
		return this;
	}

	public SprintResultAssert whereDoneCardsDoesNotContain(Card... cards) {
		cardsDoNotContain(DONE_CARD_LIST_NAME, actual.getDone(), cards);
		return this;
	}

	public SprintResultAssert whereNotDoneCardsContains(Card... cards) {
		cardsContain(NOT_DONE_CARD_LIST_NAME, actual.getNotDone(), cards);
		return this;
	}

	public SprintResultAssert whereNotDoneCardsDoesNotContain(Card... cards) {
		cardsDoNotContain(NOT_DONE_CARD_LIST_NAME, actual.getNotDone(), cards);
		return this;
	}

	public SprintResultAssert whereNotStartedCardsContains(Card... cards) {
		cardsContain(NOT_STARTED_CARD_LIST_NAME, actual.getNotStarted(), cards);
		return this;
	}

	public SprintResultAssert whereNotStartedCardsDoesNotContain(Card... cards) {
		cardsDoNotContain(NOT_STARTED_CARD_LIST_NAME, actual.getNotStarted(), cards);
		return this;
	}

	public SprintResultAssert defectsCreatedContains(Defect... defects) {
		cardsContain(DEFECTS_CARD_LIST_NAME, actual.getDefectsCreated(), defects);
		return this;
	}

	public SprintResultAssert defectsCreatedDoesNotContain(Defect... defects) {
		cardsDoNotContain(DEFECTS_CARD_LIST_NAME, actual.getDefectsCreated(), defects);
		return this;
	}

	public SprintResultAssert hasDeveloperIdleDays(Integer days) {
		if (!actual.getDeveloperIdleDays().equals(days)) {
			failWithMessage(DEVELOPER_IDLE_DAYS_NOT_EQUAL_TO_EXPECTED, days, actual.getDeveloperIdleDays());
		}
		return this;
	}

	public SprintResultAssert hasDeveloperIdleDaysBetween(Integer low, Integer high) {
		if (!(actual.getDeveloperIdleDays() >= low && actual.getDeveloperIdleDays() <= high)) {
			failWithMessage(DEVELOPER_IDLE_DAYS_NOT_IN_EXPECTED_RANGE, low, high, actual.getDeveloperIdleDays());
		}
		return this;
	}

	private void expectEmptyCollection(String listName, Collection<? extends Card> collection) {
		if ( !collection.isEmpty() ) {
			failWithMessage("Expected no " + listName + " cards", collection);
		}
	}

	private void expectNotEmptyCollection(String listName, Collection<? extends Card> collection) {
		if (collection.isEmpty()) {
			failWithMessage("Expected some " + listName + " cards", collection);
		}
	}
	
	public SprintResultAssert hasNoVerifiedCards() {
		expectEmptyCollection(VERIFIED_CARD_LIST_NAME, actual.getVerified());
		return this;
	}

	public SprintResultAssert hasVerifiedCards() {
		expectNotEmptyCollection(VERIFIED_CARD_LIST_NAME, actual.getVerified());
		return this;
	}

	public SprintResultAssert hasDoneCards() {
		expectNotEmptyCollection(DONE_CARD_LIST_NAME, actual.getDone());
		return this;
	}

	public SprintResultAssert hasNotDoneCards() {
		expectNotEmptyCollection(NOT_DONE_CARD_LIST_NAME, actual.getNotDone());
		return this;
	}

	public SprintResultAssert hasNotStartedCards() {
		expectNotEmptyCollection(NOT_STARTED_CARD_LIST_NAME, actual.getNotStarted());
		return this;
	}

	public SprintResultAssert hasDefectsCreated() {
		expectNotEmptyCollection(DEFECTS_CARD_LIST_NAME, actual.getDefectsCreated());
		return this;
	}
}
