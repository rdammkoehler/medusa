package com.noradltd.medusa.scrum;

import static org.junit.Assert.assertThat;
import static com.noradltd.medusa.scrum.SprintResultFuzzyMatcher.fuzzyMatchesSprintResults;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

public class SprintResultFuzzyMatcherTest {

	private static final Card CARD = new Card(Card.Size.MEDIUM);
	private static final String DATA = "data";

	@Test
	public void itsExact() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.getVerified().add(CARD);
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAre(2));
	}

	@Test
	public void itsNotExact() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA + DATA);
		sprintResult.getNotDone().add(CARD);
		assertThat(sprintResult, not(fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
				 .whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAre(2)));
	}

	@Test
	public void itsFuzzy() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.getVerified().add(CARD);
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAreBetween(1, 5));
	}

	@Test
	public void isntFuzzy() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		assertThat(sprintResult, fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsDoesNotContain(CARD));
	}
}
