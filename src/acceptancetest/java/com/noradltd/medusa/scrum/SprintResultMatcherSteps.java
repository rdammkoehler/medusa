package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintResultMatcher.sprintResults;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SprintResultMatcherSteps {

	// TODO duplicated from unit test, consider fixture
	private static final Card CARD = new Card(Card.Size.MEDIUM);
	private static final String DATA = "data";

	private SprintResult sprintResult;
	private SprintResultMatcher matcher;

	@Given("^sprint results$")
	public void sprint_results() throws Throwable {
		sprintResult = new SprintResult();
		sprintResult.setOriginalSprintData(DATA);
		sprintResult.addVerified(CARD);
		sprintResult.incDeveloperIdleDays();
		sprintResult.incDeveloperIdleDays();
	}

	@When("^exact match criteria are provided$")
	public void exact_match_criteria_are_provided() throws Throwable {
		matcher = sprintResults().withOriginalSprintData(DATA).whereVerifiedCardsContains(CARD)
				.whereDeveloperIdleDaysAre(2);
	}

	@Then("^the matcher successfully matches the sprint results$")
	public void the_matcher_successfully_matches_the_sprint_results() throws Throwable {
		assertThat(sprintResult, matcher);
	}

	@When("^mis-matched criteria are provided$")
	public void mis_matched_criteria_are_provided() throws Throwable {
		matcher = sprintResults().whereDeveloperIdleDaysAre(500);
	}

	@Then("^the matcher fails to match the sprint results$")
	public void the_matcher_fails_to_match_the_sprint_results() throws Throwable {
		assertThat(sprintResult, not(matcher));
	}

	@When("^vague criteria are provided$")
	public void vague_criteria_are_provided() throws Throwable {
		matcher = sprintResults().whereDeveloperIdleDaysAreBetween(1, 5);
	}

	@When("^vague criteria do not match$")
	public void vague_criteria_do_not_match() throws Throwable {
		matcher = sprintResults().whereDeveloperIdleDaysAreBetween(5, 8);
	}

	@When("^exclusionary criteria are provided$")
	public void exclusionary_criteria_are_provided() throws Throwable {
		matcher = sprintResults().whereDoneCardsDoesNotContain(CARD);
	}
}
