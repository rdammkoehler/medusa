package com.noradltd.medusa.scrum;

import static com.noradltd.medusa.scrum.CardBuilder.createCardsLike;
import static com.noradltd.medusa.scrum.TeamBuilder.createTeamOf;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.noradltd.medusa.scrum.Card.Size;

public abstract class SprintBuilder {

	private SprintBuilder() {
	}

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
		sprint.addCards(createCardsLike(Size.SMALL, 5));
		sprint.team = createTeamOf(5);

		return sprint;
	}

	public static final Sprint overcommittedSprint() {
		Sprint sprint = emptySprint();

		sprint.days = 5;
		sprint.addCards(createCardsLike(Size.SMALL, 5));
		sprint.addCards(createCardsLike(Size.MEDIUM, 5));
		sprint.addCards(createCardsLike(Size.LARGE, 5));
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

}
