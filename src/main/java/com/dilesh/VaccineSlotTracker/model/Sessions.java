package com.dilesh.VaccineSlotTracker.model;

public class Sessions {

	private Long center_id;
	private String name;
	private String address;
	private String state_name;
	private String district_name;
	private String block_name;
	private String pincode;
	private String from;
	private String to;
	private String fee_type;
	private String date;
	private int available_capacity;
	private int available_capacity_dose1;
	private int available_capacity_dose2;
	private int fee;
	private int min_age_limit;
	private String vaccine;

	public Long getCenter_id() {
		return center_id;
	}

	public void setCenter_id(Long center_id) {
		this.center_id = center_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public String getBlock_name() {
		return block_name;
	}

	public void setBlock_name(String block_name) {
		this.block_name = block_name;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAvailable_capacity() {
		return available_capacity;
	}

	public void setAvailable_capacity(int available_capacity) {
		this.available_capacity = available_capacity;
	}

	public int getAvailable_capacity_dose1() {
		return available_capacity_dose1;
	}

	public void setAvailable_capacity_dose1(int available_capacity_dose1) {
		this.available_capacity_dose1 = available_capacity_dose1;
	}

	public int getAvailable_capacity_dose2() {
		return available_capacity_dose2;
	}

	public void setAvailable_capacity_dose2(int available_capacity_dose2) {
		this.available_capacity_dose2 = available_capacity_dose2;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getMin_age_limit() {
		return min_age_limit;
	}

	public void setMin_age_limit(int min_age_limit) {
		this.min_age_limit = min_age_limit;
	}

	public String getVaccine() {
		return vaccine;
	}

	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

	@Override
	public String toString() {
		return "Sessions [center_id=" + center_id + ", name=" + name + ", address=" + address + ", state_name="
				+ state_name + ", district_name=" + district_name + ", block_name=" + block_name + ", pincode="
				+ pincode + ", from=" + from + ", to=" + to + ", fee_type=" + fee_type + ", date=" + date
				+ ", available_capacity=" + available_capacity + ", available_capacity_dose1="
				+ available_capacity_dose1 + ", available_capacity_dose2=" + available_capacity_dose2 + ", fee=" + fee
				+ ", min_age_limit=" + min_age_limit + ", vaccine=" + vaccine + "]";
	}

}
