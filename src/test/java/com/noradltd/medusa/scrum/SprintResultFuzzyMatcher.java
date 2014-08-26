package com.noradltd.medusa.scrum;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public final class SprintResultFuzzyMatcher extends TypeSafeMatcher<SprintResult> {

	private final FuzzySprintResult fuzzyResult;
	
	@Factory
	public static <T> Matcher<SprintResult> isLike(FuzzySprintResult fuzzyResult) {
		return new SprintResultFuzzyMatcher(fuzzyResult);
	}

	
	private SprintResultFuzzyMatcher(FuzzySprintResult fuzzyResult) {
		this.fuzzyResult = fuzzyResult;
	}
	
	@Override
	public void describeTo(Description description) {
		//TODO make me much much better
		description.appendText("The given Sprint result does not match the expectation!");
	}

	@Override
	protected boolean matchesSafely(SprintResult item) {
		return fuzzyResult.match(item);
	}

}
