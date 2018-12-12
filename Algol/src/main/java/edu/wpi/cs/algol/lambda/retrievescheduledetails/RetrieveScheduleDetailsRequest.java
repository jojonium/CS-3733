package edu.wpi.cs.algol.lambda.retrievescheduledetails;

public class RetrieveScheduleDetailsRequest {
	String scheduleID;

	public RetrieveScheduleDetailsRequest(String scheduleID) {
		this.scheduleID = scheduleID;
	}
	
	public String toString() {
		return "scheduleID=" + scheduleID;
	}
}
