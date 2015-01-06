package com.noradltd.medusa.scrum.sprintmatcher;

import static org.junit.Assert.assertThat;
import static com.noradltd.medusa.scrum.sprintmatcher.SprintResultFuzzyMatcher.fuzzyMatchesSprintResults;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

import com.noradltd.medusa.scrum.Card;
import com.noradltd.medusa.scrum.SprintResult;
import com.noradltd.medusa.scrum.Card.Size;

public class SprintResultFuzzyMatcherTest {

	private static final Card CARD = new Card(Card.Size.MEDIUM);
	private static final String DATA = "data";

	@Test
	public void itsExact() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.addVerified(CARD);
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
		assertThat(sprintResult, fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAre(2));
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
		assertThat(sprintResult, fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsContains(CARD).whereDeveloperIdleDaysAreBetween(1, 5));
		//assertThat(sprintResult, new SprintResultBuilder().......match());
		//assertThat(sprintResult, fuzzyMatch(newBuilder().with().with().with()));
	}

	@Test
	public void isntFuzzy() {
		SprintResult sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		assertThat(sprintResult, fuzzyMatchesSprintResults().withOriginalSprintData(DATA)
				.whereVerifiedCardsDoesNotContain(CARD));
	}
}
