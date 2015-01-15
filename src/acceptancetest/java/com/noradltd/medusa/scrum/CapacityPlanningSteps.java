package com.noradltd.medusa.scrum;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CapacityPlanningSteps {

	private Developer developer;
	private Sprint sprint;
	private Calendar today;

	@Given("^a developer with (\\d+)% capacity$")
	public void a_developer_with_capacity(int percentageAvailable) throws Throwable {
		throw new PendingException();
	}

	@When("^work assignments occur$")
	public void work_assignments_occur() throws Throwable {
		throw new PendingException();
	}

	@Then("^the developer is assigned work$")
	public void the_developer_is_assigned_work() throws Throwable {
		assertThat(developer, DeveloperMatcher.isWorkingIn(sprint, today));
	}

	@Given("^a developer with unavailable time$")
	public void a_developer_with_unavailable_time() throws Throwable {
		throw new PendingException();
	}

	@Then("^the developer is not assigned work$")
	public void the_developer_is_not_assigned_work() throws Throwable {
		assertThat(developer, not(DeveloperMatcher.isWorkingIn(sprint, today)));
	}

}

class DeveloperMatcher extends TypeSafeMatcher<Developer> {

	@Factory
	public static DeveloperMatcher isWorkingIn(Sprint sprint, Calendar date) {
		return null;
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean matchesSafely(Developer item) {
		// TODO Auto-generated method stub
		return false;
	}

}
