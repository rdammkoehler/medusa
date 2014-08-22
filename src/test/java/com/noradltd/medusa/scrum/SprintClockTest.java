package com.noradltd.medusa.scrum;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.GsonBuilder;

public class SprintClockTest {
	private SprintClock clock = new SprintClock();;

	class Day {

	}

	class Sprint {
		List<Day> days = new ArrayList<Day>();
	}

	private void run(InputStream stream) {
		clock.setStream(stream);
		try {
			clock.run();
		} catch (IOException e) {
			throw new RuntimeException("run failed", e);
		}
	}

	@Test
	public void reportZeroSprintsWhenGivenNoData() {
		run(null);
		assertThat(clock.getCycles(), is(0));
	}

	@Test
	public void reportZeroSprintsWhenSprintContainsNoDays() {
		run(sprintToStream(emptySprint()));
		assertThat(clock.getCycles(), is(0));
	}

	@Test
	public void failIfInputStreamDoesNotContainJson() {
		run(new ByteArrayInputStream("This is not json".getBytes()));
	}

	@Test
	public void reportOneSprintWhenGivenASprintsWorthOfData() {
		run(sprintToStream(defaultSprint()));
		assertThat(clock.getCycles(), is(1));
	}

	@Test
	public void reportTwoSprintsWhenGivenTwoSprintsWorthOfData() {
		run(sprintToStream(defaultSprint(), defaultSprint()));
		assertThat(clock.getCycles(), is(2));
	}

	private Sprint emptySprint() {
		return new Sprint();
	}

	private Sprint defaultSprint() {
		Sprint sprint = emptySprint();
		sprint.days.add(new Day());
		sprint.days.add(new Day());
		sprint.days.add(new Day());
		sprint.days.add(new Day());
		sprint.days.add(new Day());
		return sprint;
	}

	private InputStream sprintToStream(Sprint... sprints) {
		return new ByteArrayInputStream(new GsonBuilder().create().toJson(sprints).getBytes());
	}
}
