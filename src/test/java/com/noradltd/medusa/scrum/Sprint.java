package com.noradltd.medusa.scrum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Sprint {
	Team team = new Team();
	List<Card> cards = new ArrayList<Card>();
	List<Defect> defects = new ArrayList<Defect>();
	Integer days = 5;
	
	public boolean addCard(Card card) {
		return cards.add(card);
	}
	
	public boolean addCards(Collection<Card> cards) {
		return this.cards.addAll(cards);
	}
	
	public boolean addDefect(Defect defect) {
		return defects.add(defect);
	}
	
	public boolean addDefects(Collection<Defect> defects) {
		return this.defects.addAll(defects);
	}
}