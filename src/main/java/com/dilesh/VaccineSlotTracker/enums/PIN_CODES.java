package com.dilesh.VaccineSlotTracker.enums;

public enum PIN_CODES {
	/*
	 * if needed add pin codes from below website :
	 * 
	 * https://mumbai7.com/postal-codes-in-mumbai/
	*/

	MALAD_EAST("400097"), MALAD_WEST("400064"), GOREGAON_EAST("400063"), GOREGAON_WEST("400062"),
	JOGESHWARI_EAST("400060"), JOGESHWARI_WEST("400102"), KANDIVALI_EAST("400101"), KANDIVALI_WEST("400067"),
	BORIVALI_EAST("400066"), BORIVALI_WEST("400092"), DAHISAR("400068");

	private final String pin;

	PIN_CODES(String pin) {
		this.pin = pin;
	}

	public String getPinCode() {
		return this.pin;
	}
}
