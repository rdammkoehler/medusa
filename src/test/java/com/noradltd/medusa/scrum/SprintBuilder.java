package com.noradltd.medusa.scrum;

public class SprintBuilder {

	public static final Sprint emptySprint() {
		return new Sprint();
	}

	public static final Sprint overcommittedSprint() {
		Sprint sprint = emptySprint();

		sprint.days = 5;
		
		sprint.cards.add(new Card(Card.Size.SMALL));
		sprint.cards.add(new Card(Card.Size.SMALL));
		sprint.cards.add(new Card(Card.Size.SMALL));
		sprint.cards.add(new Card(Card.Size.SMALL));
		sprint.cards.add(new Card(Card.Size.SMALL));
		sprint.cards.add(new Card(Card.Size.MEDIUM));
		sprint.cards.add(new Card(Card.Size.MEDIUM));
		sprint.cards.add(new Card(Card.Size.MEDIUM));
		sprint.cards.add(new Card(Card.Size.LARGE));
		sprint.cards.add(new Card(Card.Size.LARGE));

		sprint.team = new Team();
		sprint.team.developers.add(new Developer());
		sprint.team.developers.add(new Developer());
		sprint.team.developers.add(new Developer());
		sprint.team.developers.add(new Developer());
		sprint.team.developers.add(new Developer());

		return sprint;
	}

	public static final Sprint defaultSprint() {
		Sprint sprint = overcommittedSprint();
		sprint.days = 10;
		
		return sprint;
	}

}
