package com.noradltd.medusa.scrum;

import java.util.ArrayList;
import java.util.List;

class Sprint {
	Team team = new Team();
	List<Card> cards = new ArrayList<Card>();
	List<Defect> defects = new ArrayList<Defect>();
	Integer days = 5;
}