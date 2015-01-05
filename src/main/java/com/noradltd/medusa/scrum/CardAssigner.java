package com.noradltd.medusa.scrum;

import java.util.Iterator;
import java.util.Set;

public class CardAssigner {

	private final Set<SDeveloper> developers;
	private final SprintResult sprintResult;
	
	public CardAssigner(SprintResult sprintResult, Set<SDeveloper> developers) {
		this.developers = developers;
		this.sprintResult = sprintResult;
	}

	public void assignCards() {
		for (SDeveloper developer : developers) {
			if (developer.isAvailable()) {
				assignCardTo(developer);
			} 
		}
	}

	private void assignCardTo(SDeveloper developer) {
		if (sprintResult.hasUnstartedCards()) {
			Iterator<Card> notStartedItr = sprintResult.getNotStartedIterator();
			Card card = notStartedItr.next();
			sprintResult.removeNotStarted(card);
			sprintResult.addNotDone(card);
			developer.workOn(card);
		}
	}

}
