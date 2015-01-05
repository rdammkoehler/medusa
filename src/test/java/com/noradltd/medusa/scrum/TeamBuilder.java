package com.noradltd.medusa.scrum;

public class TeamBuilder {
	
	public static Team createTeamOf(Integer memberCount) {
		Team team = new Team();
		for (int ct = 0; ct < memberCount; ct++) {
			team.developers.add(new Developer());
		}
		return team;
	}
}
