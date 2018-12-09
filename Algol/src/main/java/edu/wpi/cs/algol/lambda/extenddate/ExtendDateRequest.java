package edu.wpi.cs.algol.lambda.extenddate;

public class ExtendDateRequest {
	String scheduleID;
	String secretCode;
	String startDate;
	String endDate;
	
	public ExtendDateRequest (String sid, String secretCode, String sd, String ed) {
		this.scheduleID = sid;
		this.secretCode = secretCode;
		this.startDate = sd;
		this.endDate = ed;
	}
	
	public String toString() {
		return "ExtendDateRequest [schedule ID" + scheduleID + "secret code:" + secretCode +
				"start date: " + startDate + "end date: " + endDate + "]";
	}
}
