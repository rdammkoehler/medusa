package com.noradltd.medusa.scrum;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.CombinableMatcher;

public final class SprintResultMatcher extends TypeSafeMatcher<SprintResult> {

	private CombinableMatcher<?> combinableMatcher = null;

	@Factory
	public static SprintResultMatcher fuzzyMatchesSprintResults() {
		return new SprintResultMatcher();
	}

	@Factory
	public static SprintResultMatcher isValid() {
		return new SprintResultMatcher().hasOriginalSprintData().hasCards();
	}

	@Override
	public void describeTo(Description description) {
		description.appendDescriptionOf(combinableMatcher);
	}

	@Override
	protected boolean matchesSafely(SprintResult sprintResult) {
		return combinableMatcher.matches(sprintResult);
	}

	private CombinableMatcher<?> addToCombinableMatcher(Matcher<?> matcher) {
		if (null == combinableMatcher) {
			combinableMatcher = new CombinableMatcher(matcher);
		}
		return combinableMatcher;
	}

	public SprintResultMatcher withOriginalSprintData(String sprintData) {
		addToCombinableMatcher(originalSprintDataIs(sprintData));
		return this;
	}

	public SprintResultMatcher whereVerifiedCardsContains(Card... card) {
		addToCombinableMatcher(verifiedContains(card));
		return this;
	}

	public SprintResultMatcher whereVerifiedCardsDoesNotContain(Card... card) {
		addToCombinableMatcher(verifiedDoesNotContain(card));
		return this;
	}

	public SprintResultMatcher whereDeveloperIdleDaysAre(Integer days) {
		addToCombinableMatcher(developerIdleDaysAre(days));
		return this;
	}

	public SprintResultMatcher whereDeveloperIdleDaysAreBetween(Integer low, Integer high) {
		addToCombinableMatcher(developerIdleDaysBetween(low, high));
		return this;
	}

	class DeveloperIdleDays extends FeatureMatcher<SprintResult, Integer> {

		public DeveloperIdleDays(Matcher<? super Integer> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Integer featureValueOf(SprintResult actual) {
			return actual.getDeveloperIdleDays();
		}

	}

	private Matcher<?> between(Integer low, Integer high) {
		return both(greaterThan(low)).and(lessThan(high));
	}

	private Matcher<?> developerIdleDaysBetween(Integer low, Integer high) {
		return new DeveloperIdleDays((Matcher<? super Integer>) between(low, high), "SprintResult.DeveloperIdleDays",
				"DeveloperIdleDays");
	}

	private Matcher<?> developerIdleDaysAre(Integer days) {
		return new DeveloperIdleDays(is(days), "SprintResult.DeveloperIdleDays", "DeveloperIdleDays");
	}

	class Verified extends FeatureMatcher<SprintResult, Set<Card>> {

		public Verified(Matcher<? super Set<Card>> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Set<Card> featureValueOf(SprintResult actual) {
			return actual.getVerified();
		}

	}

	private Matcher<?> verifiedDoesNotContain(Card... card) {
		return new Verified(not(hasItems(card)), "SprintResult.Verified", "Verified");
	}

	private Matcher<?> verifiedContains(Card... card) {
		return new Verified(hasItems(card), "SprintResult.Verified", "Verified");
	}

	class OriginalSprintData extends FeatureMatcher<SprintResult, String> {

		public OriginalSprintData(Matcher<? super String> matcher, String featureDescription, String featureName) {
			super(matcher, featureDescription, featureName);
		}

		@Override
		protected String featureValueOf(SprintResult actual) {
			return actual.getOriginalSprintData();
		}
	}

	private Matcher<?> originalSprintDataIs(String string) {
		return new OriginalSprintData((Matcher<? super String>) is(string), "SprintResult.OriginalSprintData",
				"OriginalSprintData");
	}

	private SprintResultMatcher hasOriginalSprintData() {
		addToCombinableMatcher(new OriginalSprintData(notNullValue(), "SprintResult.originalSprintData",
				"originalSprintData"));
		return this;
	}

	class CardCount extends FeatureMatcher<SprintResult, Integer> {

		public CardCount(Matcher<? super Integer> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Integer featureValueOf(SprintResult actual) {
			return actual.getDone().size() + actual.getNotDone().size() + actual.getNotStarted().size()
					+ actual.getVerified().size();
		}

	}

	class VerifiedCount extends FeatureMatcher<SprintResult, Integer> {

		public VerifiedCount(Matcher<? super Integer> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Integer featureValueOf(SprintResult actual) {
			return actual.getVerified().size();
		}

	}

	private SprintResultMatcher hasCards() {
		addToCombinableMatcher(new CardCount(not(is(0)), "SprintResult...size()", "...size()"));
		return this;
	}

	public SprintResultMatcher hasVerifiedCards() {
		addToCombinableMatcher(new VerifiedCount(not(is(0)), "SprintResult.Verified.size()", "Verified.size()"));
		return this;
	}
}
