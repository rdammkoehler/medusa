package com.noradltd.medusa.scrum;


public class CardCompleter {
	
	private final SprintResult sprintResult;
	private final Card assignedCard;
	
	public CardCompleter(SprintResult sprintResult, Card assignedCard) {
		this.sprintResult = sprintResult;
		this.assignedCard = assignedCard;
	}

	public void moveCardToDone() {
		sprintResult.removeNotDone(assignedCard);
		sprintResult.addDone(assignedCard);
		sprintResult.removeDone(assignedCard);
		sprintResult.addVerified(assignedCard); //TODO add verifier and move this there
		checkCardForDefects();
	}
	
	private void checkCardForDefects() {
		if (assignedCard.shouldCreateDefect) {
			sprintResult.addDefect(new Defect(assignedCard));
		}
	}
}
