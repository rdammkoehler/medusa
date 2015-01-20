package com.noradltd.medusa.scrum;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SprintResultMatcherSteps {


	@Given("^sprint results$")
	public void sprint_results() throws Throwable {
	}

	@When("^exact match criteria are provided$")
	public void exact_match_criteria_are_provided() throws Throwable {
	}

	@Then("^the matcher successfully matches the sprint results$")
	public void the_matcher_successfully_matches_the_sprint_results() throws Throwable {
	}

	@When("^mis-matched criteria are provided$")
	public void mis_matched_criteria_are_provided() throws Throwable {
	}

	@Then("^the matcher fails to match the sprint results$")
	public void the_matcher_fails_to_match_the_sprint_results() throws Throwable {
	}

	@When("^vague criteria are provided$")
	public void vague_criteria_are_provided() throws Throwable {
	}

	@When("^vague criteria do not match$")
	public void vague_criteria_do_not_match() throws Throwable {
	}

	@When("^exclusionary criteria are provided$")
	public void exclusionary_criteria_are_provided() throws Throwable {
	}
}
