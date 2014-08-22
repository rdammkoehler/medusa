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

}
