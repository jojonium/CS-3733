package edu.wpi.cs.algol.lambda.extendtime;

public class ExtendTimeRequest {
	String scheduleID;
	String secretCode;
	String startTime;
	String endTime;
	
	public ExtendTimeRequest (String sid, String secretCode, String st, String et) {
		this.scheduleID = sid;
		this.secretCode = secretCode;
		this.startTime = st;
		this.endTime = et;
	}
	
	public String toString() {
		return "ExtendDateRequest [schedule ID" + scheduleID + "secret code:" + secretCode +
				"start date: " + startTime + "end date: " + endTime + "]";
	}
}
