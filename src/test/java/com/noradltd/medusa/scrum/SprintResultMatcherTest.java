package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintResultMatcher.assertThat;

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
		assertThat(sprintResult).hasOriginalSprintData(DATA);
	}

	@Test
	public void originalSprintDataDoesntMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData("not" + DATA);
		// so, how in AssertJ Do you do negation?
		// assertThat(sprintResult),
		// not(sprintResults().withOriginalSprintData(DATA)));
	}

	@Test
	public void verifiedCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARD);
		assertThat(sprintResult).verifiedCardsContains(CARD);
	}

	@Test
	public void verifiedCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult).verifiedCardsDoesNotContain(CARD);
	}

	@Test
	public void listOfVerifiedCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult).verifiedCardsContains(CARDS.get(0));
	}

	@Test
	public void listOfVerifiedCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult).verifiedCardsDoesNotContain(CARD);
	}

	@Test
	public void hasVerifiedCards() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARD);
		assertThat(sprintResult).hasVerifiedCards();
	}

	@Test
	public void hasNoVerifiedCards() {
		SprintResult sprintResult = new SprintResult();
		// again, how do I negate?
		// assertThat(sprintResult).thatHaveVerifiedCards();
	}

	@Test
	public void hasNotStartedCards() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addNotStarted(CARD);
		assertThat(sprintResult).hasNotStartedCards();
	}

	@Test
	public void hasNoNotStartedCards() {
		SprintResult sprintResult = new SprintResult();
		// how to negate?
		// assertThat(sprintResult,
		// not(sprintResults().thatHaveNotStartedCards()));
	}

	@Test
	public void defectsCreatedDefectMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefect(DEFECT);
		assertThat(sprintResult).defectsCreatedContains(DEFECT);
	}

	@Test
	public void defectsCreatedDefectNotMatch() {
		SprintResult sprintResult = new SprintResult();
		// how to negate?
		// assertThat(sprintResult,
		// not(sprintResults().whereDefectsCreatedContains(DEFECT)));
	}

	@Test
	public void defectsCreatedDefectNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult).defectsCreatedDoesNotContain(DEFECT);
	}

	@Test
	public void defectsCreatedDefectMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefect(DEFECT);
		// negate?
		// assertThat(sprintResult,
		// not(sprintResults().whereDefectsCreatedDoesNotContain(DEFECT)));
	}

	@Test
	public void listOfDefectsCreatedDefectMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		assertThat(sprintResult).defectsCreatedContains(DEFECTS.get(0));
	}

	@Test
	public void listOfDefectsCreatedDefectNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		// negate?
		// assertThat(sprintResult,
		// not(sprintResults().whereDefectsCreatedContains(DEFECT)));
	}

	@Test
	public void listOfDefectsCreatedDefectNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		assertThat(sprintResult).defectsCreatedDoesNotContain(DEFECT);
	}

	@Test
	public void listOfDefectsCreatedDefectMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		// negate?
		// assertThat(sprintResult,
		// not(sprintResults().whereDefectsCreatedDoesNotContain(DEFECTS.get(0))));
	}

	@Test
	public void listOfDefectsCreatedDefectsMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(DEFECTS);
		assertThat(sprintResult).defectsCreatedContains(DEFECTS.toArray(new Defect[] {}));
	}

	@Test
	public void listOfDefectsCreatedDefectsDoesNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefects(Arrays.asList(new Defect(new Card(Card.Size.MEDIUM)), new Defect(new Card(
				Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM)), new Defect(new Card(Card.Size.MEDIUM))));
		assertThat(sprintResult).defectsCreatedDoesNotContain(DEFECTS.toArray(new Defect[] {}));
	}

	@Test
	public void hasDefectsCreatedDefects() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addDefect(DEFECT);
		assertThat(sprintResult).hasDefectsCreated();
	}

	@Test
	public void hasNoDefectsCreatedDefects() {
		SprintResult sprintResult = new SprintResult();
		// negate?
		// assertThat(sprintResult,
		// not(sprintResults().thatHaveDefectsCreated()));
	}

	@Test
	public void developerIdleDaysMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult).hasDeveloperIdleDays(2);
	}

	@Test
	public void developerIdleDaysDontMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		// negate?
		// assertThat(sprintResult,
		// not(sprintResults().whereDeveloperIdleDaysAre(0)));
	}

	@Test
	public void developerIdleDaysBetweenMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult).hasDeveloperIdleDaysBetween(1, 3);
	}

	@Test
	public void developerIdleDaysBetweenDontMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		// negate?
		// assertThat(sprintResult,
		// not(sprintResults().whereDeveloperIdleDaysAreBetween(3, 6)));
	}

}
