package edu.wpi.cs.algol.lambda.retrievedetails;

public class retrieveDetailsRequest {
	String scheduleID;
	String secretCode;

	
	public retrieveDetailsRequest (String sid, String secretCode) {
		this.scheduleID = sid;
		this.secretCode = secretCode;
	}
	
	public String toString() {
		return "schedule ID" + scheduleID + ", secret code:" + secretCode + "]";
	}
}
