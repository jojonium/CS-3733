package edu.wpi.cs.algol.lambda.retrievedetails;

import edu.wpi.cs.algol.model.TimeSlot;

public class retrieveDetailsResponse {
	String message;
	TimeSlot timeslot;
	int httpCode;
	
	public retrieveDetailsResponse (String m, int code) {
		this.message = m;
		this.httpCode = code;
	}
	
	// 200 means success
	public retrieveDetailsResponse (String m, TimeSlot t) {
		this.message = m;
		this.timeslot = t;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "message: " + message + "timeslot:" + timeslot;
	}
}
