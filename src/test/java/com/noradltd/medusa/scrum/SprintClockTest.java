package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.SprintStreamifier.sprintToStream;
import static com.noradltd.medusa.scrum.SprintBuilder.defaultSprint;
import static com.noradltd.medusa.scrum.SprintBuilder.emptySprint;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
public class SprintClockTest {
	private final SprintClock clock = new SprintClock();;

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

}
