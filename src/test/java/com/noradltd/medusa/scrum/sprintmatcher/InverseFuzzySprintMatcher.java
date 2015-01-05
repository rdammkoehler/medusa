package com.noradltd.medusa.scrum.sprintmatcher;

import java.util.Set;

import com.noradltd.medusa.scrum.Card;
import com.noradltd.medusa.scrum.SprintResult;

class InverseFuzzySprintMatcher extends SprintMatcher {

	public boolean match0(MatchData matchData, SprintResult sprintResult) {
		boolean match = true;
		boolean match1 = true;
		// ugg, linear search
		for (Set<Card> set : matchData.fuzzySearchFlags) {
			if (matchData.verified == set) {
				match1 &= doesNotContain(sprintResult.getVerified(), set);
			}
			if (matchData.done == set) {
				match1 &= doesNotContain(sprintResult.getDone(), set);
			}
			if (matchData.notDone == set) {
				match1 &= doesNotContain(sprintResult.getNotDone(), set);
			}
			if (matchData.notStarted == set) {
				match1 &= doesNotContain(sprintResult.getNotStarted(), set);
			}
			// if ( defectsCreated == set) {
			// match &= doesNotContain(sprintResult.getDefectsCreated(),
			// set);
			// }
		}
		match1 &= matchData.developerIdleDays == null
				|| !matchData.developerIdleDays.equals(sprintResult.getDeveloperIdleDays());
		match &= match1;
		return match;
	}

}