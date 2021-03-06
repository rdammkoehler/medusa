package com.noradltd.medusa.scrum;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SprintResult {
	private String originalSprintData;
	private final Set<Card> verified = new HashSet<Card>();
	private final Set<Card> done = new HashSet<Card>();
	private final Set<Card> notDone = new HashSet<Card>();
	private final Set<Card> notStarted = new HashSet<Card>();
	private final Set<Defect> defectsCreated = new HashSet<Defect>();
	private Integer developerIdleDays = 0;

	public Integer getDeveloperIdleDays() {
		return developerIdleDays;
	}

	public void incDeveloperIdleDays() {
		this.developerIdleDays++;
	}

	public void setOriginalSprintData(String sprint) {
		originalSprintData = sprint;
	}

	public String getOriginalSprintData() {
		return originalSprintData;
	}

	public Set<Card> getVerified() {
		return verified;
	}

	public Set<Card> getDone() {
		return done;
	}

	public Set<Card> getNotDone() {
		return notDone;
	}

	public Set<Card> getNotStarted() {
		return notStarted;
	}

	public Iterator<Card> getNotStartedIterator() {
		return notStarted.iterator();
	}

	public boolean hasUnstartedCards() {
		return !notStarted.isEmpty();
	}

	public Set<Defect> getDefectsCreated() {
		return defectsCreated;
	}

	public void addVerified(Card card) {
		verified.add(card);
	}

	public void addDone(Card card) {
		done.add(card);
	}

	public boolean removeDone(Card card) {
		return done.remove(card);
	}

	public void addNotDone(Card card) {
		notDone.add(card);
	}

	public boolean removeNotDone(Card card) {
		return notDone.remove(card);
	}

	public void addNotStarted(Card card) {
		notStarted.add(card);
	}

	public boolean removeNotStarted(Card card) {
		return notStarted.remove(card);
	}

	public void addDefect(Defect defect) {
		defectsCreated.add(defect);
	}

	public void addVerified(Collection<Card> cards) {
		for (Card card : cards) {
			addVerified(card);
		}
	}

	public void addDone(Collection<Card> cards) {
		for (Card card : cards) {
			addDone(card);
		}
	}

	public void addNotDone(Collection<Card> cards) {
		for (Card card : cards) {
			addNotDone(card);
		}
	}

	public void addNotStarted(Collection<Card> cards) {
		for (Card card : cards) {
			addNotStarted(card);
		}
	}

	public void addDefects(Collection<Defect> defects) {
		for (Defect defect : defects) {
			addDefect(defect);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Results, verified ").append(verified);
		sb.append(", done ").append(done);
		sb.append(", notDone ").append(notDone);
		sb.append(", notStarted ").append(notStarted);
		sb.append(", defectsCreated ").append(defectsCreated);
		sb.append(", developerIdleDays ").append(developerIdleDays);
		sb.append(", originalSprintData ").append(originalSprintData);
		return sb.toString();
	}
}