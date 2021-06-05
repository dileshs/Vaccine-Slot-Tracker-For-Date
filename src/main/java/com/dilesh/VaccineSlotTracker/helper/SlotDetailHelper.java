package com.dilesh.VaccineSlotTracker.helper;

public class SlotDetailHelper {

	private int capacity_dose_1;
	private int capacity_dose_2;
	private String date;
	private int age;
	private String vaccineType;

	public int getCapacity_dose_1() {
		return capacity_dose_1;
	}

	public void setCapacity_dose_1(int capacity_dose_1) {
		this.capacity_dose_1 = capacity_dose_1;
	}

	public int getCapacity_dose_2() {
		return capacity_dose_2;
	}

	public void setCapacity_dose_2(int capacity_dose_2) {
		this.capacity_dose_2 = capacity_dose_2;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getVaccineType() {
		return vaccineType;
	}

	public void setVaccineType(String vaccineType) {
		this.vaccineType = vaccineType;
	}

	@Override
	public String toString() {
		return "SlotDetailHelper [capacity_dose_1=" + capacity_dose_1 + ", capacity_dose_2=" + capacity_dose_2
				+ ", date=" + date + ", age=" + age + ", vaccineType=" + vaccineType + "]";
	}

}
