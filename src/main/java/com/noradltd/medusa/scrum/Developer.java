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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = true;
		if (this == obj)
			equals = true;
		if (obj == null)
			equals = false;
		if (getClass() != obj.getClass())
			equals = false;
		Developer other = (Developer) obj;
		if (id == null) {
			if (other.id != null)
				equals = false;
		} else if (!id.equals(other.id))
			equals = false;
		return equals;
	}
}
