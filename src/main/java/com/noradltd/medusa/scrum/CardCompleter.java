package com.noradltd.medusa.scrum;


public class CardCompleter {
	
	private final SprintResult sprintResult;
	private final Card assignedCard;
	
	public CardCompleter(SprintResult sprintResult, Card assignedCard) {
		this.sprintResult = sprintResult;
		this.assignedCard = assignedCard;
	}

	public void moveCardToDone() {
		sprintResult.getNotDone().remove(assignedCard);
		sprintResult.getDone().add(assignedCard);
		sprintResult.getDone().remove(assignedCard);
		sprintResult.getVerified().add(assignedCard);
		checkCardForDefects();
	}
	
	private void checkCardForDefects() {
		if (assignedCard.shouldCreateDefect) {
			sprintResult.getDefectsCreated().add(new Defect(assignedCard));
		}
	}
}
