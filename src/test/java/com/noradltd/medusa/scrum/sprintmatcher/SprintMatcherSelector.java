package com.noradltd.medusa.scrum.sprintmatcher;

class SprintMatcherSelector {

	private static final InverseFuzzySprintMatcher INVERSE_FUZZY_SPRINT_MATCHER = new InverseFuzzySprintMatcher();
	private static final FuzzySprintMatcher FUZZY_SPRINT_MATCHER = new FuzzySprintMatcher();
	private static final ExactSprintMatcher EXACT_SPRINT_MATCHER = new ExactSprintMatcher();

	private boolean isFuzzyMatchRequest(MatchData data) {
		return data.isDontFindFlagsEmpty();
	}

	private boolean isExactMatchRequest(MatchData data) {
		return data.isFuzzyMatchEmpty() && data.isFuzzySearchFlagsEmpty() && data.isDontFindFlagsEmpty();
	}

	public SprintMatcher selectMatcherFor(MatchData data) {
		SprintMatcher matcher = null;
		if (isExactMatchRequest(data)) {
			matcher = EXACT_SPRINT_MATCHER;
		} else if (isFuzzyMatchRequest(data)) {
			matcher = FUZZY_SPRINT_MATCHER;
		} else {
			matcher = INVERSE_FUZZY_SPRINT_MATCHER;
		}
		return matcher;
	}
}