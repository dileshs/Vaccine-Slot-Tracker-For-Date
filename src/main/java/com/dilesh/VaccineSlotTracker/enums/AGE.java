package com.dilesh.VaccineSlotTracker.enums;

public enum AGE {
	FORTY_FIVE_ABOVE("45"), EIGHTEEN_ABOVE("18");

	private final String age;

	AGE(String age) {
		this.age = age;
	}

	public Integer getAge() {
		return Integer.parseInt(this.age);
	}
}
