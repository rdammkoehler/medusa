package com.noradltd.medusa.scrum;

import java.util.HashSet;
import java.util.Set;

class SprintResult {
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

	public Set<Defect> getDefectsCreated() {
		return defectsCreated;
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