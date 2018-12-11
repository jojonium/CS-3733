package edu.wpi.cs.algol.lambda.retrievedetails;

import edu.wpi.cs.algol.model.TimeSlot;

public class RetrieveDetailsResponse {
	public String message;
	TimeSlot timeslot;
	public int httpCode;
	
	public RetrieveDetailsResponse (String m, int code) {
		this.message = m;
		this.httpCode = code;
	}
	
	// 200 means success
	public RetrieveDetailsResponse (String m, TimeSlot t) {
		this.message = m;
		this.timeslot = t;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "message: " + message + "timeslot:" + timeslot;
	}
}
