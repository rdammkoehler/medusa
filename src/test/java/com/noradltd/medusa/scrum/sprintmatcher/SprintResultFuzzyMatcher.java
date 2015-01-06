package com.noradltd.medusa.scrum.sprintmatcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import com.noradltd.medusa.scrum.SprintResult;

public final class SprintResultFuzzyMatcher extends TypeSafeMatcher<SprintResult> {


	@Factory
	public static SprintResultFuzzyMatcher fuzzyMatchesSprintResults() {
		return null; 
	}

	@Override
	public void describeTo(Description description) {
	}

	@Override
	protected boolean matchesSafely(SprintResult sprintResult) {
		return false; 
	}

}
