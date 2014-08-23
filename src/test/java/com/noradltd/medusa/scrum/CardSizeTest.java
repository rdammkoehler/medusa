package com.noradltd.medusa.scrum;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CardSizeTest {

	@Test
	public void smallIsOnePoint() {
		assertThat(Card.Size.forInt(1), is(Card.Size.SMALL));
	}

	@Test
	public void mediumIsThreePoints() {
		assertThat(Card.Size.forInt(3), is(Card.Size.MEDIUM));
	}

	@Test
	public void largeIsFivePoints() {
		assertThat(Card.Size.forInt(5), is(Card.Size.LARGE));
	}

	@Test(expected = RuntimeException.class)
	public void zeroIsntASize() {
		Card.Size.forInt(0);
	}

	@Test(expected = RuntimeException.class)
	public void twoIsntASize() {
		Card.Size.forInt(2);
	}
	
}

