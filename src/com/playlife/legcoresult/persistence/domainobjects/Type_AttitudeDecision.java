package com.playlife.legcoresult.persistence.domainobjects;

public enum Type_AttitudeDecision {
	ABSENT(0), ATTEND(1), EXILED(2), AGAINST(3), APPROVE(4), FORFEIT(5), UNKNOWN(
		-1);
	private final int nValue;

	private Type_AttitudeDecision(int value) {
		nValue = value;
	}

	public Type_AttitudeDecision getByValue(int value) {
		switch (value) {
			case 0:
				return ABSENT;
			case 1:
				return ATTEND;
			case 2:
				return EXILED;
			case 3:
				return AGAINST;
			case 4:
				return APPROVE;
			case 5:
				return FORFEIT;
			default:
				return UNKNOWN;
		}
	}

	public int getValue() {
		return nValue;
	}
}
