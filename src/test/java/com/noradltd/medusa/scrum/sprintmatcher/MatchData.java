package com.noradltd.medusa.scrum.sprintmatcher;

import java.util.HashSet;
import java.util.Set;

import com.noradltd.medusa.scrum.Card;
import com.noradltd.medusa.scrum.Defect;

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

	public void addFuzzySearchFlags(Set<Card> flag) {
		fuzzySearchFlags.add(flag);
	}

	public void addFuzzyMatch(FieldFlags fieldFlag) {
		fuzzyMatch.add(fieldFlag);
	}

	public void addDontFindFlags(Set<Card> flag) {
		dontFindFlags.add(flag);
	}

	public void addVerified(Card card) {
		verified.add(card);
	}

	public void addAllVerified(Set<Card> verifiedCards) {
		verified.addAll(verifiedCards);
	}

	public boolean isDontFindFlagsEmpty() {
		return dontFindFlags.isEmpty();
	}

	public boolean isFuzzyMatchEmpty() {
		return fuzzyMatch.isEmpty();
	}

	public boolean isFuzzySearchFlagsEmpty() {
		return fuzzySearchFlags.isEmpty();
	}
}