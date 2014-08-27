package com.noradltd.medusa.scrum;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.noradltd.medusa.scrum.Card.Size;

public class CardSizeTest {

	@Test
	public void smallIsOnePoint() {
		assertThat(Size.forInt(1), is(Size.SMALL));
	}

	@Test
	public void mediumIsThreePoints() {
		assertThat(Size.forInt(3), is(Size.MEDIUM));
	}

	@Test
	public void largeIsFivePoints() {
		assertThat(Size.forInt(5), is(Size.LARGE));
	}

	@Test(expected = RuntimeException.class)
	public void zeroIsntASize() {
		Size.forInt(0);
	}

	@Test(expected = RuntimeException.class)
	public void twoIsntASize() {
		Size.forInt(2);
	}
	
}

