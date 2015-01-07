package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintResultMatcher.sprintResults;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SprintResultMatcherTest {

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
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		assertThat(sprintResult, sprintResults().withOriginalSprintData(DATA));
	}

	@Test
	public void originalSprintDataDoesntMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData("not" + DATA);
		assertThat(sprintResult, not(sprintResults().withOriginalSprintData(DATA)));
	}

	@Test
	public void verifiedCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARD);
		assertThat(sprintResult, sprintResults().whereVerifiedCardsContains(CARD));
	}

	@Test
	public void verifiedCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().whereVerifiedCardsContains(CARD)));
	}

	@Test
	public void verifiedCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, sprintResults().whereVerifiedCardsDoesNotContain(CARD));
	}

	@Test
	public void verifiedCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARD);
		assertThat(sprintResult, not(sprintResults().whereVerifiedCardsDoesNotContain(CARD)));
	}

	@Test
	public void listOfVerifiedCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, sprintResults().whereVerifiedCardsContains(CARDS.get(0)));
	}

	@Test
	public void listOfVerifiedCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, not(sprintResults().whereVerifiedCardsContains(CARD)));
	}

	@Test
	public void listOfVerifiedCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, sprintResults().whereVerifiedCardsDoesNotContain(CARD));
	}

	@Test
	public void listOfVerifiedCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, not(sprintResults().whereVerifiedCardsDoesNotContain(CARDS.get(0))));
	}

	@Test
	public void listOfVerifiedCardsMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, sprintResults().whereVerifiedCardsContains(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void listOfVerifiedCardsDoesNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(Arrays.asList(new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM), new Card(
				Card.Size.MEDIUM), new Card(Card.Size.MEDIUM)));
		assertThat(sprintResult, sprintResults().whereVerifiedCardsDoesNotContain(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void hasVerifiedCards() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARD);
		assertThat(sprintResult, sprintResults().thatHaveVerifiedCards());
	}

	@Test
	public void hasNoVerifiedCards() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().thatHaveVerifiedCards()));
	}

	@Test
	public void doneCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(CARD);
		assertThat(sprintResult, sprintResults().whereDoneCardsContains(CARD));
	}

	@Test
	public void doneCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().whereDoneCardsContains(CARD)));
	}

	@Test
	public void doneCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, sprintResults().whereDoneCardsDoesNotContain(CARD));
	}

	@Test
	public void doneCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(CARD);
		assertThat(sprintResult, not(sprintResults().whereDoneCardsDoesNotContain(CARD)));
	}

	@Test
	public void listOfDoneCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(CARDS);
		assertThat(sprintResult, sprintResults().whereDoneCardsContains(CARDS.get(0)));
	}

	@Test
	public void listOfDoneCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(CARDS);
		assertThat(sprintResult, not(sprintResults().whereDoneCardsContains(CARD)));
	}

	@Test
	public void listOfDoneCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(CARDS);
		assertThat(sprintResult, sprintResults().whereDoneCardsDoesNotContain(CARD));
	}

	@Test
	public void listOfDoneCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(CARDS);
		assertThat(sprintResult, not(sprintResults().whereDoneCardsDoesNotContain(CARDS.get(0))));
	}

	@Test
	public void listOfDoneCardsMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(CARDS);
		assertThat(sprintResult, sprintResults().whereDoneCardsContains(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void listOfDoneCardsDoesNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(Arrays.asList(new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM), new Card(
				Card.Size.MEDIUM), new Card(Card.Size.MEDIUM)));
		assertThat(sprintResult, sprintResults().whereDoneCardsDoesNotContain(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void hasDoneCards() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDone(CARD);
		assertThat(sprintResult, sprintResults().thatHaveDoneCards());
	}

	@Test
	public void hasNoDoneCards() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().thatHaveDoneCards()));
	}

	@Test
	public void notDoneCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(CARD);
		assertThat(sprintResult, sprintResults().whereNotDoneCardsContains(CARD));
	}

	@Test
	public void notDoneCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().whereNotDoneCardsContains(CARD)));
	}

	@Test
	public void notDoneCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, sprintResults().whereNotDoneCardsDoesNotContain(CARD));
	}

	@Test
	public void notDoneCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(CARD);
		assertThat(sprintResult, not(sprintResults().whereNotDoneCardsDoesNotContain(CARD)));
	}

	@Test
	public void listOfNotDoneCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(CARDS);
		assertThat(sprintResult, sprintResults().whereNotDoneCardsContains(CARDS.get(0)));
	}

	@Test
	public void listOfNotDoneCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(CARDS);
		assertThat(sprintResult, not(sprintResults().whereNotDoneCardsContains(CARD)));
	}

	@Test
	public void listOfNotDoneCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(CARDS);
		assertThat(sprintResult, sprintResults().whereNotDoneCardsDoesNotContain(CARD));
	}

	@Test
	public void listOfNotDoneCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(CARDS);
		assertThat(sprintResult, not(sprintResults().whereNotDoneCardsDoesNotContain(CARDS.get(0))));
	}

	@Test
	public void listOfNotDoneCardsMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(CARDS);
		assertThat(sprintResult, sprintResults().whereNotDoneCardsContains(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void listOfNotDoneCardsDoesNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(Arrays.asList(new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM), new Card(
				Card.Size.MEDIUM), new Card(Card.Size.MEDIUM)));
		assertThat(sprintResult, sprintResults().whereNotDoneCardsDoesNotContain(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void hasNotDoneCards() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotDone(CARD);
		assertThat(sprintResult, sprintResults().thatHaveNotDoneCards());
	}

	@Test
	public void hasNoNotDoneCards() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().thatHaveNotDoneCards()));
	}

	@Test
	public void notStartedCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARD);
		assertThat(sprintResult, sprintResults().whereNotStartedCardsContains(CARD));
	}

	@Test
	public void notStartedCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().whereNotStartedCardsContains(CARD)));
	}

	@Test
	public void notStartedCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, sprintResults().whereNotStartedCardsDoesNotContain(CARD));
	}

	@Test
	public void notStartedCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARD);
		assertThat(sprintResult, not(sprintResults().whereNotStartedCardsDoesNotContain(CARD)));
	}

	@Test
	public void listOfNotStartedCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARDS);
		assertThat(sprintResult, sprintResults().whereNotStartedCardsContains(CARDS.get(0)));
	}

	@Test
	public void listOfNotStartedCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARDS);
		assertThat(sprintResult, not(sprintResults().whereNotStartedCardsContains(CARD)));
	}

	@Test
	public void listOfNotStartedCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARDS);
		assertThat(sprintResult, sprintResults().whereNotStartedCardsDoesNotContain(CARD));
	}

	@Test
	public void listOfNotStartedCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARDS);
		assertThat(sprintResult, not(sprintResults().whereNotStartedCardsDoesNotContain(CARDS.get(0))));
	}

	@Test
	public void listOfNotStartedCardsMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARDS);
		assertThat(sprintResult, sprintResults().whereNotStartedCardsContains(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void listOfNotStartedCardsDoesNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(Arrays.asList(new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM), new Card(
				Card.Size.MEDIUM), new Card(Card.Size.MEDIUM)));
		assertThat(sprintResult, sprintResults().whereNotStartedCardsDoesNotContain(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void hasNotStartedCards() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARD);
		assertThat(sprintResult, sprintResults().thatHaveNotStartedCards());
	}

	@Test
	public void hasNoNotStartedCards() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().thatHaveNotStartedCards()));
	}

	@Test
	public void defectsCreatedDefectMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefect(DEFECT);
		assertThat(sprintResult, sprintResults().whereDefectsCreatedContains(DEFECT));
	}

	@Test
	public void defectsCreatedDefectNotMatch() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().whereDefectsCreatedContains(DEFECT)));
	}

	@Test
	public void defectsCreatedDefectNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, sprintResults().whereDefectsCreatedDoesNotContain(DEFECT));
	}

	@Test
	public void defectsCreatedDefectMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefect(DEFECT);
		assertThat(sprintResult, not(sprintResults().whereDefectsCreatedDoesNotContain(DEFECT)));
	}

	@Test
	public void listOfDefectsCreatedDefectMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		assertThat(sprintResult, sprintResults().whereDefectsCreatedContains(DEFECTS.get(0)));
	}

	@Test
	public void listOfDefectsCreatedDefectNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		assertThat(sprintResult, not(sprintResults().whereDefectsCreatedContains(DEFECT)));
	}

	@Test
	public void listOfDefectsCreatedDefectNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		assertThat(sprintResult, sprintResults().whereDefectsCreatedDoesNotContain(DEFECT));
	}

	@Test
	public void listOfDefectsCreatedDefectMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		assertThat(sprintResult, not(sprintResults().whereDefectsCreatedDoesNotContain(DEFECTS.get(0))));
	}

	@Test
	public void listOfDefectsCreatedDefectsMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		assertThat(sprintResult, sprintResults().whereDefectsCreatedContains(DEFECTS.toArray(new Defect[] {})));
	}

	@Test
	public void listOfDefectsCreatedDefectsDoesNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(Arrays.asList(new Defect(new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM)),
				new Defect(new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM))));
		assertThat(sprintResult,
				sprintResults().whereDefectsCreatedDoesNotContain(DEFECTS.toArray(new Defect[] {})));
	}

	@Test
	public void hasDefectsCreatedDefects() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefect(DEFECT);
		assertThat(sprintResult, sprintResults().thatHaveDefectsCreated());
	}

	@Test
	public void hasNoDefectsCreatedDefects() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(sprintResults().thatHaveDefectsCreated()));
	}

	@Test
	public void developerIdleDaysMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, sprintResults().whereDeveloperIdleDaysAre(2));
	}

	@Test
	public void developerIdleDaysDontMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, not(sprintResults().whereDeveloperIdleDaysAre(0)));
	}

	@Test
	public void developerIdleDaysBetweenMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, sprintResults().whereDeveloperIdleDaysAreBetween(1, 3));
	}

	@Test
	public void developerIdleDaysBetweenDontMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, not(sprintResults().whereDeveloperIdleDaysAreBetween(3, 6)));
	}

}
