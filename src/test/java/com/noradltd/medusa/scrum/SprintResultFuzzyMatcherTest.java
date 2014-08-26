package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintResultFuzzyMatcher.isLike;
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
		FuzzySprintResult fsr = FuzzySprintResult.Builder().withOriginalSprintData(DATA)
				.whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAre(2).build();
		org.junit.Assert.assertThat(sprintResult, isLike(fsr));
	}

	@Test
	public void itsNotExact() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA + DATA);
		sprintResult.getNotDone().add(CARD);
		FuzzySprintResult fsr = FuzzySprintResult.Builder().withOriginalSprintData(DATA)
				.whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAre(2).build();
		org.junit.Assert.assertThat(sprintResult, not(isLike(fsr)));
	}

	@Test
	public void itsFuzzy() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.getVerified().add(CARD);
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		FuzzySprintResult fsr = FuzzySprintResult.Builder().withOriginalSprintData(DATA)
				.whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAreBetween(1, 5).build();
		org.junit.Assert.assertThat(sprintResult, isLike(fsr));
	}
	
	@Test
	public void isntFuzzy() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		FuzzySprintResult fsr = FuzzySprintResult.Builder().withOriginalSprintData(DATA)
				.whereVerifiedCardsDoesNotContain(CARD).build();
		org.junit.Assert.assertThat(sprintResult, isLike(fsr));
	}
}
