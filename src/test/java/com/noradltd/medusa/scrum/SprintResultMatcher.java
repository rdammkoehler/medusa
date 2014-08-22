package com.noradltd.medusa.scrum;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class SprintResultMatcher extends TypeSafeMatcher<SprintResult> {

	@Factory
	public static <T> Matcher<SprintResult> isValid() {
		return new SprintResultMatcher();
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Sprint Result is invalid because it is missing essential data");
	}

	@Override
	protected boolean matchesSafely(SprintResult sprintResult) {
		boolean valid = sprintResult != null;
		valid &= sprintResult.getOriginalSprintData() != null;
		valid &= sprintResult.getVerified().size() + sprintResult.getDone().size() + sprintResult.getNotDone().size() + sprintResult.getNotStarted().size() > 0;
		return valid;
	}

}
