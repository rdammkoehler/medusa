package com.noradltd.medusa.scrum;

import java.util.Iterator;
import java.util.NoSuchElementException;
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
		try {
			Set<Card> notStarted = sprintResult.getNotStarted();
			Iterator<Card> notStartedItr = notStarted.iterator();
			Card card = notStartedItr.next();
			notStarted.remove(card);
			Set<Card> notDone = sprintResult.getNotDone();
			notDone.add(card);
			developer.workOn(card);
		} catch (NoSuchElementException nsee) {
			//no-op
		}
	}

}
