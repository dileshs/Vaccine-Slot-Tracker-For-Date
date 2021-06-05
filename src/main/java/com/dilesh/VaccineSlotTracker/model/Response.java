package com.dilesh.VaccineSlotTracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	@JsonProperty
	List<Sessions> sessions;

	public List<Sessions> getSessions() {
		return sessions;
	}

	public void setSessions(List<Sessions> sessions) {
		this.sessions = sessions;
	}

	@Override
	public String toString() {
		return "Response [sessions=" + sessions + "]";
	}

}
