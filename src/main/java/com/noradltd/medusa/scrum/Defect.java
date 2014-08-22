package com.noradltd.medusa.scrum;

public class Defect extends Card {

	Card cause = null;
	
	public Defect() {
		super(Card.Size.MEDIUM);
	}

	public Defect(Size cardSize) {
		super(cardSize);
	}

	public Defect(Card card) {
		super(Card.Size.MEDIUM);
		cause = card;
	}

}
