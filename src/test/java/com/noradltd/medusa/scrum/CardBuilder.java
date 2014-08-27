package com.noradltd.medusa.scrum;

import java.util.ArrayList;
import java.util.List;

public class CardBuilder {
	public static List<Card> createCardsLike(Card.Size size, Integer cardCount) {
		List<Card> cards = new ArrayList<Card>();
		for (int ct = 0; ct < cardCount; ct++) {
			cards.add(new Card(size));
		}
		return cards;
	}

}
