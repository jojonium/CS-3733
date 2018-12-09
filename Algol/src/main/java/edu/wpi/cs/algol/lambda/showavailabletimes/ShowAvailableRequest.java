package edu.wpi.cs.algol.lambda.showavailabletimes;

public class ShowAvailableRequest {
	String scheduleID;
	String startDate;
	String endDate;
	String startTime;
	String endTime;
	
	public ShowAvailableRequest (String sid, String sd, String ed, String st, String et) {
		this.scheduleID = sid;
		this.startDate = sd;
		this.endDate = ed;
		this.startTime = st;
		this.endTime = et;
	}
	
	public String toString() {
		return "ShowavailableRequest [schedule ID" + scheduleID + "start date: " + startDate + 
				"end date: " + endDate + "start time: " + startTime + "end date: " + endDate + "]";
	}
}
