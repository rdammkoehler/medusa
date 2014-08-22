package com.noradltd.medusa.scrum;

public class Card implements Comparable<Card> {

	enum Size {
		SMALL(1), MEDIUM(3), LARGE(5);
		private final Integer scalarSize;

		Size(Integer sSize) {
			scalarSize = sSize;
		}

		public Integer getScalarSize() {
			return scalarSize;
		}

		public static Size forInt(Integer scalarSize) {
			switch (scalarSize) {
			case 1:
				return SMALL;
			case 3:
				return MEDIUM;
			case 5:
				return LARGE;
			default:
				throw new RuntimeException("Unknown Size Scalar: " + scalarSize);
			}
		}
	}

	private static int instanceCount = 0;

	Integer id = instanceCount++;
	Integer size = 0;
	Integer startedOnDay = 0;
	Integer endedOnDay = 0;
	Integer duration = 0;
	boolean shouldGetBlocked = false;
	float blockDurationInDays = 0.0f;
	boolean shouldCreateDefect = false;
	float defectImpactRelativeToOriginalSize = 1.0f;

	public Card(Size cardSize) {
		size = cardSize.getScalarSize();
	}

	@Override
	public int compareTo(Card o) {
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
