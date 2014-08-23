package com.noradltd.medusa.scrum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class SprintBuilder {

	private SprintBuilder() {}
	
	public static final Sprint emptySprint() {
		return new Sprint();
	}

	public static final Sprint defaultSprint() {
		Sprint sprint = overcommittedSprint();
		sprint.days = 10;

		return sprint;
	}

	public static final Sprint undercommittedSprint() {
		Sprint sprint = emptySprint();
		sprint.days = 5;
		sprint.cards.addAll(createCardsLike(Card.Size.SMALL, 5));
		sprint.team = createTeamOf(5);

		return sprint;
	}

	public static final Sprint overcommittedSprint() {
		Sprint sprint = emptySprint();

		sprint.days = 5;
		sprint.cards.addAll(createCardsLike(Card.Size.SMALL, 5));
		sprint.cards.addAll(createCardsLike(Card.Size.MEDIUM, 5));
		sprint.cards.addAll(createCardsLike(Card.Size.LARGE, 5));
		sprint.team = createTeamOf(5);

		return sprint;
	}

	public static final Sprint addDefectsTo(Sprint sprint, Integer defectCount) {
		Card[] array = sprint.cards.toArray(new Card[] {});
		List<Card> list = Arrays.asList(array);
		Collections.shuffle(list);
		Iterator<Card> cardItr = list.iterator();
		Card currentCard = cardItr.next();
		for (int ct = 0; ct < defectCount; ct++) {
			currentCard.shouldCreateDefect = true;
			currentCard.defectImpactRelativeToOriginalSize = 1.0f;
			currentCard = cardItr.next();
		}
		return sprint;
	}

	// TODO move to TeamBuilder
	private static Team createTeamOf(Integer memberCount) {
		Team team = new Team();
		for (int ct = 0; ct < memberCount; ct++) {
			team.developers.add(new Developer());
		}
		return team;
	}

	// TODO move to CardBuilder
	private static List<Card> createCardsLike(Card.Size size, Integer cardCount) {
		List<Card> cards = new ArrayList<Card>();
		for (int ct = 0; ct < cardCount; ct++) {
			cards.add(new Card(size));
		}
		return cards;
	}

}
