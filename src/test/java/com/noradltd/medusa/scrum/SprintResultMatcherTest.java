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
		assertThat(sprintResult,
				sprintResults().whereVerifiedCardsDoesNotContain(CARDS.toArray(new Card[] {})));
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

	/** LEGACY **/
	@Test
	public void itsExact() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.addVerified(CARD);
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult,
				sprintResults().withOriginalSprintData(DATA).whereVerifiedCardsContains(CARD)
						.whereDeveloperIdleDaysAre(2));
	}

	@Test
	public void itsNotExact() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA + DATA);
		sprintResult.addNotDone(CARD);
		assertThat(sprintResult, not(sprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAre(2)));
	}

	@Test
	public void itsFuzzy() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.addVerified(CARD);
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult,
				sprintResults().withOriginalSprintData(DATA).whereVerifiedCardsContains(CARD)
						.whereDeveloperIdleDaysAreBetween(1, 5));
	}

	@Test
	public void isntFuzzy() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.addVerified(CARD);
		assertThat(sprintResult, sprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsDoesNotContain(CARD));
	}
}
