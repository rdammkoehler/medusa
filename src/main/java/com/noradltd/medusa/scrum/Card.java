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
			Size size = MEDIUM;
			switch (scalarSize) {
			case 1:
				size = SMALL;
				break;
			case 3:
				size = MEDIUM;
				break;
			case 5:
				size = LARGE;
				break;
			default:
				throw new RuntimeException("Unknown Size Scalar: " + scalarSize);
			}
			return size;
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
		boolean equals = true;
		if (this == obj) {
			equals = true;
		} else if (obj == null) {
			equals = false;
		} else if (getClass() != obj.getClass()) {
			equals = false;
		} else {
			Card other = (Card) obj;
			if (id == null) {
				if (other.id != null) {
					equals = false;
				}
			} else if (!id.equals(other.id)) {
				equals = false;
			}
		}
		return equals;
	}

}
