package com.noradltd.medusa.scrum.sprintmatcher;

import com.noradltd.medusa.scrum.SprintResult;

class ExactSprintMatcher extends SprintMatcher {

	public boolean match0(MatchData matchData, SprintResult sprintResult) {
		boolean match = true;
		boolean match1 = true;
		match1 &= matchData.verified == null || containsAll(matchData.verified, sprintResult.getVerified());
		match1 &= matchData.done == null || containsAll(matchData.done, sprintResult.getDone());
		match1 &= matchData.notDone == null || containsAll(matchData.notDone, sprintResult.getNotDone());
		match1 &= matchData.notStarted == null || containsAll(matchData.notStarted, sprintResult.getNotStarted());
		match1 &= matchData.defectsCreated == null
				|| containsAll(matchData.notStarted, sprintResult.getDefectsCreated());
		match1 &= matchData.developerIdleDays == null
				|| matchData.developerIdleDays.equals(sprintResult.getDeveloperIdleDays());
		match &= match1;
		return match;
	}
}