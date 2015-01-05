package com.noradltd.medusa.scrum.sprintmatcher;

import java.util.Collection;
import java.util.Set;

import com.noradltd.medusa.scrum.Card;
import com.noradltd.medusa.scrum.SprintResult;

abstract class SprintMatcher {

	public boolean match(MatchData data, SprintResult sprintResult) {
		return initializedByTheSameSprintData(data, sprintResult) && match0(data, sprintResult);
	}

	protected abstract boolean match0(MatchData data, SprintResult sprintResult);

	private boolean initializedByTheSameSprintData(MatchData matchData, SprintResult sprintResult) {
		return matchData.originalSprintData == null
				|| matchData.originalSprintData.equals(sprintResult.getOriginalSprintData());
	}

	protected boolean containsAll(Collection<?> a, Collection<?> b) {
		return a.containsAll(b);
	}

	protected boolean doesNotContain(Set<Card> resultSet, Set<Card> set) {
		boolean match = true;
		for (Card card : set) {
			match &= !resultSet.contains(card);
		}
		return match;
	}
}