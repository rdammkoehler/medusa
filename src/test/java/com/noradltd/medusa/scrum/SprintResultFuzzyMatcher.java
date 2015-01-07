package com.noradltd.medusa.scrum;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;

import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.CombinableMatcher;

import com.noradltd.medusa.scrum.Card;
import com.noradltd.medusa.scrum.SprintResult;

public final class SprintResultFuzzyMatcher extends TypeSafeMatcher<SprintResult> {

	private CombinableMatcher<?> combinableMatcher = null;

	@Factory
	public static SprintResultFuzzyMatcher fuzzyMatchesSprintResults() {
		// seems like we build up a bunch of matchers in the with-statements and
		// add them to a combinable matcher that came from here
		// except that combinable matcher needs at least one matcher to get
		// started, this matcher must be this matcher with no internal matchers
		return new SprintResultFuzzyMatcher();
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

	public SprintResultFuzzyMatcher withOriginalSprintData(String sprintData) {
		addToCombinableMatcher(originalSprintDataIs(sprintData));
		return this;
	}

	public SprintResultFuzzyMatcher whereVerifiedCardsContains(Card... card) {
		addToCombinableMatcher(verifiedContains(card));
		return this;
	}
	
	public SprintResultFuzzyMatcher whereVerifiedCardsDoesNotContain(Card... card) {
		addToCombinableMatcher(verifiedDoesNotContain(card));
		return this;
	}

	public SprintResultFuzzyMatcher whereDeveloperIdleDaysAre(Integer days) {
		addToCombinableMatcher(developerIdleDaysAre(days));
		return this;
	}

	public SprintResultFuzzyMatcher whereDeveloperIdleDaysAreBetween(Integer low, Integer high) {
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
		return new DeveloperIdleDays((Matcher<? super Integer>) between(low, high), "SprintResult.DeveloperIdleDays", "DeveloperIdleDays");
	}

	private Matcher<?> developerIdleDaysAre(Integer days) {
		return new DeveloperIdleDays(is(days), "SprintResult.DeveloperIdleDays", "DeveloperIdleDays");
	}

	class Verified extends FeatureMatcher<SprintResult, Set<Card>> {

		public Verified(Matcher<? super Set<Card>> subMatcher, String featureDescription,
				String featureName) {
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
		return new OriginalSprintData((Matcher<? super String>) is(string), "SprintResult.OriginalSprintData", "OriginalSprintData");
	}
}
