package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintBuilder.addDefectsTo;
import static com.noradltd.medusa.scrum.SprintBuilder.defaultSprint;
import static com.noradltd.medusa.scrum.SprintBuilder.overcommittedSprint;
import static com.noradltd.medusa.scrum.SprintBuilder.undercommittedSprint;
import static com.noradltd.medusa.scrum.SprintResultMatcher.isValid;
import static com.noradltd.medusa.scrum.SprintStreamifier.sprintToStream;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RunSprintSteps {

	private static final int EXPECTED_IDLE_DAYS = 20;
	private static final int DEFECTS_TO_CREATE = 5;
	private SprintResult sprintResult = new SprintResult();
	private final SprintClock sprintClock = new SprintClock();

	public RunSprintSteps() {
		sprintClock.addResultListener(new SprintResultListener() {
			@Override
			public void sprintComplete(SprintResult result) {
				sprintResult = result;
			}
		});
	}

	@Given("^over-committed sprint data$")
	public void over_committed_sprint_data() throws Throwable {
		sprintClock.setStream(sprintToStream(overcommittedSprint()));
	}

	@Given("^sprint data$")
	public void sprint_data() throws Throwable {
		sprintClock.setStream(sprintToStream(defaultSprint()));
	}

	@Given("^under-committed sprint data$")
	public void under_committed_sprint_data() throws Throwable {
		sprintClock.setStream(sprintToStream(undercommittedSprint()));
	}

	@Given("^there are defects created$")
	public void there_are_defects_created() throws Throwable {
		sprintClock.setStream(sprintToStream(addDefectsTo(defaultSprint(), DEFECTS_TO_CREATE)));
	}

	@When("^I run the sprint$")
	public void i_run_the_sprint() throws Throwable {
		sprintClock.run();
	}

	@Then("^I get sprint artifacts$")
	public void i_get_sprint_artifacts() throws Throwable {
		assertThat(sprintResult, isValid());
	}

	@Then("^I not all cards are verified$")
	public void i_not_all_cards_are_verified() throws Throwable {
		assertThat(sprintResult.getVerified().size(), lessThan(defaultSprint().cards.size()));
	}

	@Then("^I see many idle days for developers$")
	public void i_see_many_idle_days_for_developers() throws Throwable {
		assertThat(sprintResult.getDeveloperIdleDays(), is(EXPECTED_IDLE_DAYS));
	}

	@Then("^I get defect creation notifications$")
	public void i_get_defect_creation_notifications() throws Throwable {
		assertThat(sprintResult.getDefectsCreated().size(), is(DEFECTS_TO_CREATE));
	}
}
