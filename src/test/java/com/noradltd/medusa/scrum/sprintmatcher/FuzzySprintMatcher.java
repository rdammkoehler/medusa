package com.noradltd.medusa.scrum.sprintmatcher;

import com.noradltd.medusa.scrum.SprintResult;

class FuzzySprintMatcher extends SprintMatcher {

	public boolean match0(MatchData matchData, SprintResult sprintResult) {
		boolean match = true;
		boolean match1 = true;
		match1 &= matchData.verified == null || containsAll(sprintResult.getVerified(), matchData.verified);
		match1 &= matchData.done == null || containsAll(sprintResult.getDone(), matchData.done);
		match1 &= matchData.notDone == null || containsAll(sprintResult.getNotDone(), matchData.notDone);
		match1 &= matchData.notStarted == null || containsAll(sprintResult.getNotStarted(), matchData.notStarted);
		match1 &= matchData.defectsCreated == null
				|| containsAll(sprintResult.getDefectsCreated(), matchData.defectsCreated);
		if (matchData.fuzzyMatch.contains(FieldFlags.DEVELOPER_IDLE_DAYS_FIELD)) {
			match1 &= matchData.developerIdleDays == null
					|| (sprintResult.getDeveloperIdleDays() >= matchData.minimumDeveloperIdleDays && sprintResult
							.getDeveloperIdleDays() <= matchData.maximumDeveloperIdleDays);
		}
		match &= match1;
		return match;
	}
}