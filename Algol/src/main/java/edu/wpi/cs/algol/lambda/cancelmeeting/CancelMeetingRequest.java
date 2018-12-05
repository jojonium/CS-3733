package edu.wpi.cs.algol.lambda.cancelmeeting;

public class CancelMeetingRequest {
	String scheduleID;
	String date;
	String time;
	String secretCode;
	
	public CancelMeetingRequest (String r, String sid, String d, String t, String sc) {
		this.scheduleID = sid;
		this.date = d;
		this.time = t;
		this.secretCode = sc;
	}
	
	public String toString() {
		return "schedule ID" + scheduleID + 
				"date:" + date + "time:" + time + "secret code:" + secretCode + "]";
	}
}
