package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintResultFuzzyMatcher.fuzzyMatchesSprintResults;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.noradltd.medusa.scrum.Card;
import com.noradltd.medusa.scrum.SprintResult;

public class SprintResultFuzzyMatcherTest {

	private static final Card CARD = new Card(Card.Size.MEDIUM);
	private static final List<Card> CARDS = Arrays.asList(new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM),
			new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM));
	private static final String DATA = "data";

	@Test
	public void originalSprintDataMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		assertThat(sprintResult, fuzzyMatchesSprintResults().withOriginalSprintData(DATA));
	}

	@Test
	public void originalSprintDataDoesntMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData("not" + DATA);
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().withOriginalSprintData(DATA)));
	}

	@Test
	public void verifiedCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARD);
		assertThat(sprintResult, fuzzyMatchesSprintResults().whereVerifiedCardsContains(CARD));
	}

	@Test
	public void verifiedCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().whereVerifiedCardsContains(CARD)));
	}

	@Test
	public void verifiedCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		assertThat(sprintResult, fuzzyMatchesSprintResults().whereVerifiedCardsDoesNotContain(CARD));
	}

	@Test
	public void verifiedCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARD);
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().whereVerifiedCardsDoesNotContain(CARD)));
	}

	@Test
	public void listOfVerifiedCardMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, fuzzyMatchesSprintResults().whereVerifiedCardsContains(CARDS.get(0)));
	}

	@Test
	public void listOfVerifiedCardNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().whereVerifiedCardsContains(CARD)));
	}

	@Test
	public void listOfVerifiedCardNotMatch2() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, fuzzyMatchesSprintResults().whereVerifiedCardsDoesNotContain(CARD));
	}

	@Test
	public void listOfVerifiedCardMatch3() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().whereVerifiedCardsDoesNotContain(CARDS.get(0))));
	}

	@Test
	public void listOfVerifiedCardsMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(CARDS);
		assertThat(sprintResult, fuzzyMatchesSprintResults().whereVerifiedCardsContains(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void listOfVerifiedCardsDoesNotMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.addVerified(Arrays.asList(new Card(Card.Size.MEDIUM), new Card(Card.Size.MEDIUM), new Card(
				Card.Size.MEDIUM), new Card(Card.Size.MEDIUM)));
		assertThat(sprintResult,
				fuzzyMatchesSprintResults().whereVerifiedCardsDoesNotContain(CARDS.toArray(new Card[] {})));
	}

	@Test
	public void developerIdleDaysMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, fuzzyMatchesSprintResults().whereDeveloperIdleDaysAre(2));
	}

	@Test
	public void developerIdleDaysDontMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().whereDeveloperIdleDaysAre(0)));
	}

	@Test
	public void developerIdleDaysBetweenMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, fuzzyMatchesSprintResults().whereDeveloperIdleDaysAreBetween(1, 3));
	}

	@Test
	public void developerIdleDaysBetweenDontMatch() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().whereDeveloperIdleDaysAreBetween(3, 6)));
	}

	@Test
	public void itsExact() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.addVerified(CARD);
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult,
				fuzzyMatchesSprintResults().withOriginalSprintData(DATA).whereVerifiedCardsContains(CARD)
						.whereDeveloperIdleDaysAre(2));
	}

	@Test
	public void itsNotExact() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA + DATA);
		sprintResult.addNotDone(CARD);
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
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
				fuzzyMatchesSprintResults().withOriginalSprintData(DATA).whereVerifiedCardsContains(CARD)
						.whereDeveloperIdleDaysAreBetween(1, 5));
	}

	@Test
	public void isntFuzzy() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.addVerified(CARD);
		assertThat(sprintResult, fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsDoesNotContain(CARD));
	}
}
