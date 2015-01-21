package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintResultAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SprintResultAssertTest {

	private static final String ORIGINAL_SPRINT_DATA_DOES_NOT_MATCH = "Original Sprint Data does not match";
	private static final String EXPECTED_SOME_NOT_STARTED_CARDS = "Expected some not started cards";
	private static final String DEFECTS_CARDS_DOES_NOT_CONTAIN_EXPECTED_CARD_S = "Defects Cards does not contain expected card(s)";
	private static final String DEFECTS_CARDS_CONTAIN_UNEXPECTED_CARD_S = "Defects Cards contain unexpected card(s)";
	private static final String DEVELOPER_IDLE_DAYS_NOT_IN_EXPECTED_RANGE = "Developer Idle Days not in expected range";
	private static final String DEVELOPER_IDLE_DAYS_NOT_EQUAL_TO_EXPECTED = "Developer Idle Days not equal to expected";
	private static final String EXPECTED_DEFECT_CARDS = "Expected some defects created cards";
	private static final Card CARD = new Card(Card.Size.MEDIUM);
	private static final List<Card> CARDS = Arrays.asList(new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM),
			new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM));
	private static final Defect DEFECT = new Defect(new Card(Card.Size.MEDIUM));
	private static final List<Defect> DEFECTS = Arrays
			.asList(new Defect(new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM)), new Defect(
					new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM)));
	private static final String DATA = "data";

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
	public void hasNotStartedCards() {
		SprintResult sprintResult = new SprintResultBuilder().withNotStarted(CARD).build();
		assertThat(sprintResult).hasNotStartedCards();
	}

	@Test
	public void hasNoNotStartedCards() {
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
