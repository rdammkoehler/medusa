package com.noradltd.medusa.scrum;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

public final class SprintResultFuzzyMatcher extends TypeSafeMatcher<SprintResult> {

	private final MatchData matchData = new MatchData();

	class MatchData {
		boolean preventVerifiedListAblation = false;
		final Set<FieldFlags> fuzzyMatch = new HashSet<FieldFlags>();
		final Set<Set<Card>> fuzzySearchFlags = new HashSet<Set<Card>>();
		final Set<Set<Card>> dontFindFlags = new HashSet<Set<Card>>();
		String originalSprintData;
		final Set<Card> verified = new HashSet<Card>();
		final Set<Card> done = new HashSet<Card>();
		final Set<Card> notDone = new HashSet<Card>();
		final Set<Card> notStarted = new HashSet<Card>();
		final Set<Defect> defectsCreated = new HashSet<Defect>();
		Integer developerIdleDays, minimumDeveloperIdleDays, maximumDeveloperIdleDays;

		private boolean isFuzzyMatchRequest() {
			return dontFindFlags.isEmpty();
		}

		private boolean isExactMatchRequest() {
			return fuzzyMatch.isEmpty() && fuzzySearchFlags.isEmpty() && dontFindFlags.isEmpty();
		}

		public boolean match(SprintResult sprintResult) {
			boolean match = true;
			match &= initializedByTheSameSprintData(sprintResult);
			if (isExactMatchRequest()) {
				match &= exactMatch(sprintResult);
			} else if (isFuzzyMatchRequest()) {
				match &= fuzzyMatch(sprintResult);
			} else {
				match &= fuzzyInverseMatch(sprintResult);
			}
			return match;
		}

		private boolean initializedByTheSameSprintData(SprintResult sprintResult) {
			return originalSprintData == null || originalSprintData.equals(sprintResult.getOriginalSprintData());
		}

		public void addFuzzySearchFlags(Set<Card> flag) {
			fuzzySearchFlags.add(flag);
		}

		public void addFuzzyMatch(FieldFlags fieldFlag) {
			fuzzyMatch.add(fieldFlag);
		}

		public void addDontFindFlags(Set<Card> flag) {
			dontFindFlags.add(flag);
		}
	}

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
		return matchData.match(sprintResult);
	}

	private enum FieldFlags {
		DEVELOPER_IDLE_DAYS_FIELD;
	}

	public SprintResultFuzzyMatcher withOriginalSprintData(String originalSprintData) {
		matchData.originalSprintData = originalSprintData;
		return this;
	}

	public SprintResultFuzzyMatcher withVerifiedCardsMatch(Set<Card> verified) {
		if (matchData.preventVerifiedListAblation) {
			System.err.println("You can only modify the Verified Card Set matcher once");
		} else {
			matchData.verified.addAll(verified);
		}
		return this;
	}

	public SprintResultFuzzyMatcher whereVerifiedCardsContains(Card verifiedCard) {
		if (matchData.preventVerifiedListAblation) {
			System.err.println("You can only modify the Verified Card Set matcher once");
		} else {
			matchData.addFuzzySearchFlags(matchData.verified);
			matchData.verified.add(verifiedCard);
		}
		return this;
	}

	public SprintResultFuzzyMatcher whereVerifiedCardsContains(Set<Card> verifiedCards) {
		if (matchData.preventVerifiedListAblation) {
			System.err.println("You can only modify the Verified Card Set matcher once");
		} else {
			matchData.addFuzzySearchFlags(matchData.verified);
			matchData.verified.addAll(verifiedCards);
		}
		return this;
	}

	public SprintResultFuzzyMatcher whereVerifiedCardsDoesNotContain(Card card) {
		if (matchData.preventVerifiedListAblation) {
			System.err.println("You can only modify the Verified Card Set matcher once");
		} else {
			matchData.addFuzzySearchFlags(matchData.verified);
			matchData.addDontFindFlags(matchData.verified);
			matchData.verified.add(card);
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

	private boolean exactMatch(SprintResult sprintResult) {
		boolean match = true;
		match &= matchData.verified == null || containsAll(matchData.verified, sprintResult.getVerified());
		match &= matchData.done == null || containsAll(matchData.done, sprintResult.getDone());
		match &= matchData.notDone == null || containsAll(matchData.notDone, sprintResult.getNotDone());
		match &= matchData.notStarted == null || containsAll(matchData.notStarted, sprintResult.getNotStarted());
		match &= matchData.defectsCreated == null
				|| containsAll(matchData.notStarted, sprintResult.getDefectsCreated());
		match &= matchData.developerIdleDays == null
				|| matchData.developerIdleDays.equals(sprintResult.getDeveloperIdleDays());
		return match;
	}

	private boolean fuzzyMatch(SprintResult sprintResult) {
		boolean match = true;
		match &= matchData.verified == null || containsAll(sprintResult.getVerified(), matchData.verified);
		match &= matchData.done == null || containsAll(sprintResult.getDone(), matchData.done);
		match &= matchData.notDone == null || containsAll(sprintResult.getNotDone(), matchData.notDone);
		match &= matchData.notStarted == null || containsAll(sprintResult.getNotStarted(), matchData.notStarted);
		match &= matchData.defectsCreated == null
				|| containsAll(sprintResult.getDefectsCreated(), matchData.defectsCreated);
		if (matchData.fuzzyMatch.contains(FieldFlags.DEVELOPER_IDLE_DAYS_FIELD)) {
			match &= matchData.developerIdleDays == null
					|| (sprintResult.getDeveloperIdleDays() >= matchData.minimumDeveloperIdleDays && sprintResult
							.getDeveloperIdleDays() <= matchData.maximumDeveloperIdleDays);
		}
		return match;
	}

	private boolean fuzzyInverseMatch(SprintResult sprintResult) {
		boolean match = true;
		// ugg, linear search
		for (Set<Card> set : matchData.fuzzySearchFlags) {
			if (matchData.verified == set) {
				match &= doesNotContain(sprintResult.getVerified(), set);
			}
			if (matchData.done == set) {
				match &= doesNotContain(sprintResult.getDone(), set);
			}
			if (matchData.notDone == set) {
				match &= doesNotContain(sprintResult.getNotDone(), set);
			}
			if (matchData.notStarted == set) {
				match &= doesNotContain(sprintResult.getNotStarted(), set);
			}
			// if ( defectsCreated == set) {
			// match &= doesNotContain(sprintResult.getDefectsCreated(),
			// set);
			// }
		}
		match &= matchData.developerIdleDays == null
				|| !matchData.developerIdleDays.equals(sprintResult.getDeveloperIdleDays());
		return match;
	}

	private boolean containsAll(Collection<?> a, Collection<?> b) {
		return a.containsAll(b);
	}

	private boolean doesNotContain(Set<Card> resultSet, Set<Card> set) {
		boolean match = true;
		for (Card card : set) {
			match &= !resultSet.contains(card);
		}
		return match;
	}

}
