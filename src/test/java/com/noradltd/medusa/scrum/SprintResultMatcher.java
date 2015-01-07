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

@SuppressWarnings({ "PMD.GodClass", "PMD.TooManyMethods" })
public final class SprintResultMatcher extends TypeSafeMatcher<SprintResult> {

	private CombinableMatcher<?> combinableMatcher = null;

	@Factory
	public static SprintResultMatcher sprintResults() {
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

	public SprintResultMatcher whereDoneCardsContains(Card... card) {
		addToCombinableMatcher(doneContains(card));
		return this;
	}

	public SprintResultMatcher whereDoneCardsDoesNotContain(Card... card) {
		addToCombinableMatcher(doneDoesNotContain(card));
		return this;
	}

	public SprintResultMatcher whereNotDoneCardsContains(Card... card) {
		addToCombinableMatcher(notDoneContains(card));
		return this;
	}

	public SprintResultMatcher whereNotDoneCardsDoesNotContain(Card... card) {
		addToCombinableMatcher(notDoneDoesNotContain(card));
		return this;
	}	

	public SprintResultMatcher whereNotStartedCardsContains(Card... card) {
		addToCombinableMatcher(notStartedContains(card));
		return this;
	}

	public SprintResultMatcher whereNotStartedCardsDoesNotContain(Card... card) {
		addToCombinableMatcher(notStartedDoesNotContain(card));
		return this;
	}

	public SprintResultMatcher whereDefectsCreatedContains(Defect... defect) {
		addToCombinableMatcher(defectsCreatedContains(defect));
		return this;
	}

	public SprintResultMatcher whereDefectsCreatedDoesNotContain(Defect... defect) {
		addToCombinableMatcher(defectsCreatedDoesNotContain(defect));
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

	private Matcher<?> developerIdleDaysBetween(Integer low, Integer high) {
		return new DeveloperIdleDays(both(greaterThan(low)).and(lessThan(high)), "SprintResult.DeveloperIdleDays",
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

	class Done extends FeatureMatcher<SprintResult, Set<Card>> {

		public Done(Matcher<? super Set<Card>> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Set<Card> featureValueOf(SprintResult actual) {
			return actual.getDone();
		}
	}

	class NotDone extends FeatureMatcher<SprintResult, Set<Card>> {

		public NotDone(Matcher<? super Set<Card>> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Set<Card> featureValueOf(SprintResult actual) {
			return actual.getNotDone();
		}
	}

	class NotStarted extends FeatureMatcher<SprintResult, Set<Card>> {

		public NotStarted(Matcher<? super Set<Card>> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Set<Card> featureValueOf(SprintResult actual) {
			return actual.getNotStarted();
		}
	}

	class DefectsCreated extends FeatureMatcher<SprintResult, Set<Defect>> {

		public DefectsCreated(Matcher<? super Set<Defect>> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Set<Defect> featureValueOf(SprintResult actual) {
			return actual.getDefectsCreated();
		}
	}

	private Matcher<?> verifiedDoesNotContain(Card... card) {
		return new Verified(not(hasItems(card)), "SprintResult.Verified", "Verified");
	}

	private Matcher<?> verifiedContains(Card... card) {
		return new Verified(hasItems(card), "SprintResult.Verified", "Verified");
	}

	private Matcher<?> doneDoesNotContain(Card... card) {
		return new Done(not(hasItems(card)), "SprintResult.Done", "Done");
	}

	private Matcher<?> doneContains(Card... card) {
		return new Done(hasItems(card), "SprintResult.Done", "Done");
	}

	private Matcher<?> notDoneDoesNotContain(Card... card) {
		return new NotDone(not(hasItems(card)), "SprintResult.NotDone", "NotDone");
	}

	private Matcher<?> notDoneContains(Card... card) {
		return new NotDone(hasItems(card), "SprintResult.NotDone", "NotDone");
	}

	private Matcher<?> notStartedDoesNotContain(Card... card) {
		return new NotStarted(not(hasItems(card)), "SprintResult.NotStarted", "NotStarted");
	}

	private Matcher<?> notStartedContains(Card... card) {
		return new NotStarted(hasItems(card), "SprintResult.NotStarted", "NotStarted");
	}	
	
	private Matcher<?> defectsCreatedDoesNotContain(Defect... defect) {
		return new DefectsCreated(not(hasItems(defect)), "SprintResult.DefectsCreated", "DefectsCreated");
	}

	private Matcher<?> defectsCreatedContains(Defect... defect) {
		return new DefectsCreated(hasItems(defect), "SprintResult.DefectsCreated", "DefectsCreated");
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

	private SprintResultMatcher hasCards() {
		addToCombinableMatcher(new CardCount(not(is(0)), "SprintResult...size()", "...size()"));
		return this;
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

	class DoneCount extends FeatureMatcher<SprintResult, Integer> {

		public DoneCount(Matcher<? super Integer> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Integer featureValueOf(SprintResult actual) {
			return actual.getDone().size();
		}

	}

	class NotDoneCount extends FeatureMatcher<SprintResult, Integer> {

		public NotDoneCount(Matcher<? super Integer> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Integer featureValueOf(SprintResult actual) {
			return actual.getNotDone().size();
		}

	}

	class NotStartedCount extends FeatureMatcher<SprintResult, Integer> {

		public NotStartedCount(Matcher<? super Integer> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Integer featureValueOf(SprintResult actual) {
			return actual.getNotStarted().size();
		}

	}

	class DefectsCreatedCount extends FeatureMatcher<SprintResult, Integer> {

		public DefectsCreatedCount(Matcher<? super Integer> subMatcher, String featureDescription, String featureName) {
			super(subMatcher, featureDescription, featureName);
		}

		@Override
		protected Integer featureValueOf(SprintResult actual) {
			return actual.getDefectsCreated().size();
		}

	}

	public SprintResultMatcher thatHaveVerifiedCards() {
		addToCombinableMatcher(new VerifiedCount(not(is(0)), "SprintResult.Verified.size()", "Verified.size()"));
		return this;
	}

	public SprintResultMatcher thatHaveDoneCards() {
		addToCombinableMatcher(new DoneCount(not(is(0)), "SprintResult.Done.size()", "Done.size()"));
		return this;
	}

	public SprintResultMatcher thatHaveNotDoneCards() {
		addToCombinableMatcher(new NotDoneCount(not(is(0)), "SprintResult.NotDone.size()", "NotDone.size()"));
		return this;
	}

	public SprintResultMatcher thatHaveNotStartedCards() {
		addToCombinableMatcher(new NotStartedCount(not(is(0)), "SprintResult.NotStarted.size()", "NotStarted.size()"));
		return this;
	}

	public SprintResultMatcher thatHaveDefectsCreated() {
		addToCombinableMatcher(new DefectsCreatedCount(not(is(0)), "SprintResult.DefectsCreated.size()",
				"DefectsCreated.size()"));
		return this;
	}
}
