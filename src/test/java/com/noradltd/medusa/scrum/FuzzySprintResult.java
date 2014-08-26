package com.noradltd.medusa.scrum;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class FuzzySprintResult {

	private enum FieldFlags {
		DEVELOPER_IDLE_DAYS_FIELD;
	}

	static class Builder {

		private FuzzySprintResult fuzzyResult = new FuzzySprintResult();

		public Builder withOriginalSprintData(String originalSprintData) {
			fuzzyResult.setOriginalSprintData(originalSprintData);
			return this;
		}

		private boolean preventVerifiedListAblation = false;

		public Builder withVerifiedCards(Set<Card> verified) {
			if (!preventVerifiedListAblation) {
				fuzzyResult.getVerified().addAll(verified);
			} else {
				System.err.println("You can only modify the Verified Card Set matcher once");
			}
			return this;
		}

		public Builder whereVerifiedCardsContains(Card verifiedCard) {
			if (!preventVerifiedListAblation) {
				fuzzyResult.setFuzzySearch(fuzzyResult.getVerified());
				fuzzyResult.getVerified().add(verifiedCard);
			} else {
				System.err.println("You can only modify the Verified Card Set matcher once");
			}
			return this;
		}

		public Builder whereVerifiedCardsContains(Set<Card> verifiedCards) {
			if (!preventVerifiedListAblation) {
				fuzzyResult.setFuzzySearch(fuzzyResult.getVerified());
				fuzzyResult.getVerified().addAll(verifiedCards);
			} else {
				System.err.println("You can only modify the Verified Card Set matcher once");
			}
			return this;
		}

		public Builder whereVerifiedCardsDoesNotContain(Card card) {
			if (!preventVerifiedListAblation) {
				fuzzyResult.setFuzzySearch(fuzzyResult.getVerified());
				fuzzyResult.setDontFind(fuzzyResult.getVerified());
				fuzzyResult.getVerified().add(card);
			} else {
				System.err.println("You can only modify the Verified Card Set matcher once");
			}
			return this;
		}

		public Builder whereDeveloperIdleDaysAre(Integer idleDays) {
			fuzzyResult.setDeveloperIdleDays(idleDays);
			return this;
		}

		public Builder whereDeveloperIdleDaysAreBetween(Integer lowerLimit, Integer upperLimit) {
			fuzzyResult.addFuzzyMatch(FieldFlags.DEVELOPER_IDLE_DAYS_FIELD);
			fuzzyResult.setMinimumDeveloperIdleDays(lowerLimit);
			fuzzyResult.setMaximumDeveloperIdleDays(upperLimit);
			return this;
		}

		public FuzzySprintResult build() {
			return fuzzyResult;
		}
	}

	public static final Builder Builder() {
		return new Builder();
	}

	private Set<FieldFlags> fuzzyMatch = new HashSet<FieldFlags>();
	private Set<Set<Card>> fuzzySearchFlags = new HashSet<Set<Card>>();
	private Set<Set<Card>> dontFindFlags = new HashSet<Set<Card>>();
	private String originalSprintData;
	private final Set<Card> verified = new HashSet<Card>();
	private final Set<Card> done = new HashSet<Card>();
	private final Set<Card> notDone = new HashSet<Card>();
	private final Set<Card> notStarted = new HashSet<Card>();
	private final Set<Defect> defectsCreated = new HashSet<Defect>();
	private Integer developerIdleDays, minimumDeveloperIdleDays, maximumDeveloperIdleDays;

	private FuzzySprintResult() {
	}

	public boolean match(SprintResult sprintResult) {
		boolean match = true;
		match &= originalSprintData == null || originalSprintData.equals(sprintResult.getOriginalSprintData());
		if (fuzzyMatch.isEmpty() && fuzzySearchFlags.isEmpty() && dontFindFlags.isEmpty()) {
			match &= verified == null || verified.containsAll(sprintResult.getVerified());
			match &= done == null || done.containsAll(sprintResult.getDone());
			match &= notDone == null || notDone.containsAll(sprintResult.getNotDone());
			match &= notStarted == null || notStarted.containsAll(sprintResult.getNotStarted());
			match &= defectsCreated == null || notStarted.containsAll(sprintResult.getDefectsCreated());
			match &= developerIdleDays == null || developerIdleDays.equals(sprintResult.getDeveloperIdleDays());
		} else {
			if (dontFindFlags.isEmpty()) {
				match &= verified == null || sprintResult.getVerified().containsAll(verified);
				match &= done == null || sprintResult.getDone().containsAll(done);
				match &= notDone == null || sprintResult.getNotDone().containsAll(notDone);
				match &= notStarted == null || sprintResult.getNotStarted().containsAll(notStarted);
				match &= defectsCreated == null || sprintResult.getDefectsCreated().containsAll(notStarted);
				if (fuzzyMatch.contains(FieldFlags.DEVELOPER_IDLE_DAYS_FIELD)) {
					match &= developerIdleDays == null
							|| (sprintResult.getDeveloperIdleDays() >= minimumDeveloperIdleDays && sprintResult
									.getDeveloperIdleDays() <= maximumDeveloperIdleDays);
				}
			} else {
				// ugg, linear search
				for (Set<Card> set : fuzzySearchFlags) {
					if (verified == set) {
						match &= doesNotContain(sprintResult.getVerified(), set);
					}
					if (done == set ) {
						match &= doesNotContain(sprintResult.getDone(), set);
					}
					if ( notDone == set) {
						match &= doesNotContain(sprintResult.getNotDone(), set);
					}
					if ( notStarted == set ) {
						match &= doesNotContain(sprintResult.getNotStarted(),set);
					}
//					if ( defectsCreated == set) {
//						match &= doesNotContain(sprintResult.getDefectsCreated(), set);
//					}
				}
				match &= developerIdleDays == null || !developerIdleDays.equals(sprintResult.getDeveloperIdleDays());
			}
		}
		return match;
	}

	private boolean doesNotContain(Set<Card> resultSet, Set<Card> set) {
		boolean match = true;
		for (Card card : set) {
			match &= !resultSet.contains(card);
		}
		return match;
	}

	public void setFuzzySearch(Set<Card> verified) {
		fuzzySearchFlags.add(verified);
	}

	public void addFuzzyMatch(FieldFlags fieldFlag) {
		fuzzyMatch.add(fieldFlag);
	}

	public void setDontFind(Set<Card> verified) {
		dontFindFlags.add(verified);
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

	public Integer getDeveloperIdleDays() {
		return developerIdleDays;
	}

	protected void setOriginalSprintData(String originalSprintData) {
		this.originalSprintData = originalSprintData;
	}

	protected void setDeveloperIdleDays(Integer developerIdleDays) {
		this.developerIdleDays = developerIdleDays;
	}

	protected Integer getMinimumDeveloperIdleDays() {
		return minimumDeveloperIdleDays;
	}

	protected void setMinimumDeveloperIdleDays(Integer minimumDeveloperIdleDays) {
		this.minimumDeveloperIdleDays = minimumDeveloperIdleDays;
	}

	protected Integer getMaximumDeveloperIdleDays() {
		return maximumDeveloperIdleDays;
	}

	protected void setMaximumDeveloperIdleDays(Integer maximumDeveloperIdleDays) {
		this.maximumDeveloperIdleDays = maximumDeveloperIdleDays;
	}

}
