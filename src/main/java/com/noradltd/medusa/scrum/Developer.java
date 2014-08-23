package com.noradltd.medusa.scrum;

public class Developer implements Comparable<Developer> {
	private static int instanceCount = 0;
	Integer id = instanceCount++;
	Integer velocity = 1;
	Integer lift = 0;
	Integer drag = 0;

	@Override
	public int compareTo(Developer o) {
		return id.compareTo(o.id);
	}

}
