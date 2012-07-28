package com.playlife.legcoresult.persistence.domainobjects;

public enum Type_TopicStatus {

	WAITING, PASSED, FAILED, UNKNOW;

	public static Type_TopicStatus fromInt(int value) {
		switch (value) {
			case 0:
				return WAITING;
			case 1:
				return PASSED;
			case 2:
				return FAILED;
			default:
				return UNKNOW;
		}
	}
}
