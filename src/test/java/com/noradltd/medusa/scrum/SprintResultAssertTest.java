package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintResultAssert.CARDS_CONTAIN_UNEXPECTED_CARD_S;
import static com.noradltd.medusa.scrum.SprintResultAssert.CARDS_DOES_NOT_CONTAIN_EXPECTED_CARD_S;
import static com.noradltd.medusa.scrum.SprintResultAssert.CARDS_SUFFIX;
import static com.noradltd.medusa.scrum.SprintResultAssert.DEFECTS_CARD_LIST_NAME;
import static com.noradltd.medusa.scrum.SprintResultAssert.DEVELOPER_IDLE_DAYS_NOT_EQUAL_TO_EXPECTED;
import static com.noradltd.medusa.scrum.SprintResultAssert.DEVELOPER_IDLE_DAYS_NOT_IN_EXPECTED_RANGE;
import static com.noradltd.medusa.scrum.SprintResultAssert.EXPECTED_SOME;
import static com.noradltd.medusa.scrum.SprintResultAssert.NOT_STARTED_CARD_LIST_NAME;
import static com.noradltd.medusa.scrum.SprintResultAssert.ORIGINAL_SPRINT_DATA_DOES_NOT_MATCH;
import static com.noradltd.medusa.scrum.SprintResultAssert.SPRINT_RESULT_HAS_NO_CARDS;
import static com.noradltd.medusa.scrum.SprintResultAssert.SPRINT_RESULT_HAS_NO_ORIGINAL_SPRINT_DATA;
import static com.noradltd.medusa.scrum.SprintResultAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SprintResultAssertTest {

	private static final String EXPECTED_SOME_NOT_STARTED_CARDS = EXPECTED_SOME + NOT_STARTED_CARD_LIST_NAME
			+ CARDS_SUFFIX;
	private static final String DEFECTS_CARDS_DOES_NOT_CONTAIN_EXPECTED_CARD_S = DEFECTS_CARD_LIST_NAME
			+ CARDS_DOES_NOT_CONTAIN_EXPECTED_CARD_S;
	private static final String DEFECTS_CARDS_CONTAIN_UNEXPECTED_CARD_S = DEFECTS_CARD_LIST_NAME
			+ CARDS_CONTAIN_UNEXPECTED_CARD_S;
	private static final String EXPECTED_DEFECT_CARDS = EXPECTED_SOME + DEFECTS_CARD_LIST_NAME + CARDS_SUFFIX;
	private static final Card CARD = new Card(Card.Size.MEDIUM);
	private static final List<Card> CARDS = Arrays.asList(new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM),
			new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM));
	private static final Defect DEFECT = new Defect(new Card(Card.Size.MEDIUM));
	private static final List<Defect> DEFECTS = Arrays
			.asList(new Defect(new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM)), new Defect(
					new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM)));
	private static final String DATA = "data";

	@Test
	public void validIsValid() {
		assertThat(new SprintResultBuilder().withOriginalSprintData(DATA).withVerified(CARDS).build()).isValid();
	}

	@Test
	public void invalidIsInvalid() {
		expectAssertionErrorWithMessageContaining(SPRINT_RESULT_HAS_NO_ORIGINAL_SPRINT_DATA);
		assertThat(new SprintResultBuilder().build()).isValid();
	}

	@Test
	public void invalidIsInvalidToo() {
		expectAssertionErrorWithMessageContaining(SPRINT_RESULT_HAS_NO_CARDS);
		assertThat(new SprintResultBuilder().withOriginalSprintData(DATA).build());
	}

	@Test
	public void originalSprintDataMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withOriginalSprintData(DATA).build();
		assertThat(sprintResult).hasOriginalSprintData(DATA);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private void expectAssertionErrorWithMessageContaining(String message) {
		exception.expect(AssertionError.class);
		exception.expectMessage(containsString(message));
		exception.handleAssertionErrors();
	}

	@Test
	public void originalSprintDataDoesntMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withOriginalSprintData("not" + DATA).build();

		expectAssertionErrorWithMessageContaining(ORIGINAL_SPRINT_DATA_DOES_NOT_MATCH);

		assertThat(sprintResult).hasOriginalSprintData(DATA);
	}

	@Test
	public void verifiedCardMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withVerified(CARD).build();
		assertThat(sprintResult).verifiedCardsContains(CARD);
	}

	@Test
	public void verifiedCardNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().build();
		assertThat(sprintResult).verifiedCardsDoesNotContain(CARD);
	}

	@Test
	public void listOfVerifiedCardMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withVerified(CARDS).build();
		assertThat(sprintResult).verifiedCardsContains(CARDS.get(0));
	}

	@Test
	public void listOfVerifiedCardNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withVerified(CARDS).build();
		assertThat(sprintResult).verifiedCardsDoesNotContain(CARD);
	}

	@Test
	public void hasVerifiedCards() {
		SprintResult sprintResult = new SprintResultBuilder().withVerified(CARD).build();
		assertThat(sprintResult).hasVerifiedCards();
	}

	@Test
	public void hasNoVerifiedCards() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult).hasNoVerifiedCards();
	}

	@Test
	public void notStartedCardMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withNotStarted(CARD).build();
		assertThat(sprintResult).notStartedCardsContains(CARD);
	}

	@Test
	public void notStartedCardNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().build();
		assertThat(sprintResult).notStartedCardsDoesNotContain(CARD);
	}

	@Test
	public void listOfNotStartedCardMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withNotStarted(CARDS).build();
		assertThat(sprintResult).notStartedCardsContains(CARDS.get(0));
	}

	@Test
	public void listOfNotStartedCardNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withNotStarted(CARDS).build();
		assertThat(sprintResult).notStartedCardsDoesNotContain(CARD);
	}

	@Test
	public void hasNotStartedCards() {
		SprintResult sprintResult = new SprintResultBuilder().withNotStarted(CARD).build();
		assertThat(sprintResult).hasNotStartedCards();
	}

	@Test
	public void hasNoNotStartedCards() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult).hasNoNotStartedCards();
	}

	@Test
	public void doneCardMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDone(CARD).build();
		assertThat(sprintResult).doneCardsContains(CARD);
	}

	@Test
	public void doneCardNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().build();
		assertThat(sprintResult).doneCardsDoesNotContain(CARD);
	}

	@Test
	public void listOfDoneCardMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDone(CARDS).build();
		assertThat(sprintResult).doneCardsContains(CARDS.get(0));
	}

	@Test
	public void listOfDoneCardNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDone(CARDS).build();
		assertThat(sprintResult).doneCardsDoesNotContain(CARD);
	}

	@Test
	public void hasDoneCards() {
		SprintResult sprintResult = new SprintResultBuilder().withDone(CARD).build();
		assertThat(sprintResult).hasDoneCards();
	}

	@Test
	public void hasNoDoneCards() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult).hasNoDoneCards();
	}

	@Test
	public void notDoneCardMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withNotDone(CARD).build();
		assertThat(sprintResult).notDoneCardsContains(CARD);
	}

	@Test
	public void notDoneCardNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().build();
		assertThat(sprintResult).notDoneCardsDoesNotContain(CARD);
	}

	@Test
	public void listOfNotDoneCardMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withNotDone(CARDS).build();
		assertThat(sprintResult).notDoneCardsContains(CARDS.get(0));
	}

	@Test
	public void listOfNotDoneCardNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withNotDone(CARDS).build();
		assertThat(sprintResult).notDoneCardsDoesNotContain(CARD);
	}

	@Test
	public void hasNotDoneCards() {
		SprintResult sprintResult = new SprintResultBuilder().withNotDone(CARD).build();
		assertThat(sprintResult).hasNotDoneCards();
	}

	@Test
	public void hasNoNotDoneCards() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult).hasNoNotDoneCards();
	}

	@Test
	public void doesntHaveNotStartedCards() {
		SprintResult sprintResult = new SprintResultBuilder().build();

		expectAssertionErrorWithMessageContaining(EXPECTED_SOME_NOT_STARTED_CARDS);

		assertThat(sprintResult).hasNotStartedCards();
	}

	@Test
	public void defectsCreatedDefectMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(DEFECT).build();
		assertThat(sprintResult).defectsCreatedContains(DEFECT);
	}

	@Test
	public void defectsCreatedDefectNotMatch() {
		SprintResult sprintResult = new SprintResult();

		expectAssertionErrorWithMessageContaining(DEFECTS_CARDS_DOES_NOT_CONTAIN_EXPECTED_CARD_S);

		assertThat(sprintResult).defectsCreatedContains(DEFECT);
	}

	@Test
	public void defectsCreatedDefectNotMatch2() {
		SprintResult sprintResult = new SprintResultBuilder().build();
		assertThat(sprintResult).defectsCreatedDoesNotContain(DEFECT);
	}

	@Test
	public void defectsCreatedDefectMatch3() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(DEFECT).build();

		expectAssertionErrorWithMessageContaining(DEFECTS_CARDS_CONTAIN_UNEXPECTED_CARD_S);

		assertThat(sprintResult).defectsCreatedDoesNotContain(DEFECT);
	}

	@Test
	public void listOfDefectsCreatedDefectMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(DEFECTS).build();
		assertThat(sprintResult).defectsCreatedContains(DEFECTS.get(0));
	}

	@Test
	public void listOfDefectsCreatedDefectNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(DEFECTS).build();

		expectAssertionErrorWithMessageContaining(DEFECTS_CARDS_DOES_NOT_CONTAIN_EXPECTED_CARD_S);

		assertThat(sprintResult).defectsCreatedContains(DEFECT);
	}

	@Test
	public void listOfDefectsCreatedDefectNotMatch2() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(DEFECTS).build();
		assertThat(sprintResult).defectsCreatedDoesNotContain(DEFECT);
	}

	@Test
	public void listOfDefectsCreatedDefectMatch3() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(DEFECTS).build();

		expectAssertionErrorWithMessageContaining(DEFECTS_CARDS_CONTAIN_UNEXPECTED_CARD_S);

		assertThat(sprintResult).defectsCreatedDoesNotContain(DEFECTS.get(0));
	}

	@Test
	public void listOfDefectsCreatedDefectsMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(DEFECTS).build();
		assertThat(sprintResult).defectsCreatedContains(DEFECTS.toArray(new Defect[] {}));
	}

	@Test
	public void listOfDefectsCreatedDefectsDoesNotMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(
				Arrays.asList(new Defect(new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM)),
						new Defect(new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM)))).build();
		assertThat(sprintResult).defectsCreatedDoesNotContain(DEFECTS.toArray(new Defect[] {}));
	}

	@Test
	public void hasDefectsCreatedDefects() {
		SprintResult sprintResult = new SprintResultBuilder().withDefect(DEFECT).build();
		assertThat(sprintResult).hasDefectsCreated();
	}

	@Test
	public void hasNoDefectsCreatedDefects() {
		SprintResult sprintResult = new SprintResultBuilder().build();

		expectAssertionErrorWithMessageContaining(EXPECTED_DEFECT_CARDS);

		assertThat(sprintResult).hasDefectsCreated();
	}

	@Test
	public void developerIdleDaysMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDeveloperIdleDays(2).build();
		assertThat(sprintResult).hasDeveloperIdleDays(2);
	}

	@Test
	public void developerIdleDaysDontMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDeveloperIdleDays(2).build();

		expectAssertionErrorWithMessageContaining(DEVELOPER_IDLE_DAYS_NOT_EQUAL_TO_EXPECTED);

		assertThat(sprintResult).hasDeveloperIdleDays(0);
	}

	@Test
	public void developerIdleDaysBetweenMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDeveloperIdleDays(2).build();
		assertThat(sprintResult).hasDeveloperIdleDaysBetween(1, 3);
	}

	@Test
	public void developerIdleDaysBetweenDontMatch() {
		SprintResult sprintResult = new SprintResultBuilder().withDeveloperIdleDays(2).build();

		expectAssertionErrorWithMessageContaining(DEVELOPER_IDLE_DAYS_NOT_IN_EXPECTED_RANGE);

		assertThat(sprintResult).hasDeveloperIdleDaysBetween(3, 6);
	}

}
