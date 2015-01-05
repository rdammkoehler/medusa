package com.noradltd.medusa.scrum.sprintmatcher;

import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import com.noradltd.medusa.scrum.Card;
import com.noradltd.medusa.scrum.SprintResult;

public final class SprintResultFuzzyMatcher extends TypeSafeMatcher<SprintResult> {

	final MatchData matchData = new MatchData();

	@Factory
	public static SprintResultFuzzyMatcher fuzzyMatchesSprintResults() {
		return new SprintResultFuzzyMatcher();
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("The given Sprint result does not match the expectation!");
	}

	@Override
	protected boolean matchesSafely(SprintResult sprintResult) {
		return new SprintMatcherSelector().selectMatcherFor(matchData).match(matchData, sprintResult);
	}

	public SprintResultFuzzyMatcher withOriginalSprintData(String originalSprintData) {
		matchData.originalSprintData = originalSprintData;
		return this;
	}

	public SprintResultFuzzyMatcher withVerifiedCardsMatch(Set<Card> verified) {
		if (matchData.preventVerifiedListAblation) {
			System.err.println("You can only modify the Verified Card Set matcher once");
		} else {
			matchData.addAllVerified(verified);
		}
		return this;
	}

	public SprintResultFuzzyMatcher whereVerifiedCardsContains(Card verifiedCard) {
		if (matchData.preventVerifiedListAblation) {
			System.err.println("You can only modify the Verified Card Set matcher once");
		} else {
			matchData.addFuzzySearchFlags(matchData.verified);
			matchData.addVerified(verifiedCard);
		}
		return this;
	}

	public SprintResultFuzzyMatcher whereVerifiedCardsContains(Set<Card> verifiedCards) {
		if (matchData.preventVerifiedListAblation) {
			System.err.println("You can only modify the Verified Card Set matcher once");
		} else {
			matchData.addFuzzySearchFlags(matchData.verified);
			matchData.addAllVerified(verifiedCards);
		}
		return this;
	}

	public SprintResultFuzzyMatcher whereVerifiedCardsDoesNotContain(Card card) {
		if (matchData.preventVerifiedListAblation) {
			System.err.println("You can only modify the Verified Card Set matcher once");
		} else {
			matchData.addFuzzySearchFlags(matchData.verified);
			matchData.addDontFindFlags(matchData.verified);
			matchData.addVerified(card);
		}
		return this;
	}

	public SprintResultFuzzyMatcher whereDeveloperIdleDaysAre(Integer idleDays) {
		matchData.developerIdleDays = idleDays;
		return this;
	}

	public SprintResultFuzzyMatcher whereDeveloperIdleDaysAreBetween(Integer lowerLimit, Integer upperLimit) {
		matchData.addFuzzyMatch(FieldFlags.DEVELOPER_IDLE_DAYS_FIELD);
		matchData.minimumDeveloperIdleDays = lowerLimit;
		matchData.maximumDeveloperIdleDays = upperLimit;
		return this;
	}

}
