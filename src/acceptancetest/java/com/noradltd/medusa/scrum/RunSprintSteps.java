package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintStreamifier.sprintToStream;
import static com.noradltd.medusa.scrum.SprintBuilder.defaultSprint;
import static com.noradltd.medusa.scrum.SprintBuilder.overcommittedSprint;
import static com.noradltd.medusa.scrum.SprintResultMatcher.isValid;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RunSprintSteps {

	private SprintResult sprintResult = new SprintResult();
	private SprintClock sprintClock = new SprintClock();;
	{
		sprintClock.addResultListener(new SprintResultListener() {
			public void sprintComplete(SprintResult result) {
				sprintResult = result;
			}
		});
	}

	@Given("^over-committed sprint data$")
	public void over_committed_sprint_data() throws Throwable {
		sprintClock.setStream(sprintToStream(overcommittedSprint())); //make obvious that its too big
	}

	@Given("^sprint data$")
	public void sprint_data() throws Throwable {
		sprintClock.setStream(sprintToStream(defaultSprint()));
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
}
