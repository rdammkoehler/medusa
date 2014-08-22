package com.noradltd.medusa.scrum;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "html:target/cucumber-html-report" }, monochrome = true)
public class RunCukeTest {

}