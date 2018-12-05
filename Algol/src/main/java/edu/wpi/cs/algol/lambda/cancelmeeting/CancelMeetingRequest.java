package edu.wpi.cs.algol.lambda.cancelmeeting;

public class CancelMeetingRequest {
	String requester;
	String scheduleID;
	String date;
	String time;
	String secretCode;
	
	public CancelMeetingRequest (String r, String sid, String d, String t, String sc) {
		this.requester = r;
		this.scheduleID = sid;
		this.date = d;
		this.time = t;
		this.secretCode = sc;
	}
	
	public String toString() {
		return "CancelMeetingRequest [requester:" + requester + "schedule ID" + scheduleID + 
				"date:" + date + "time:" + time + "secret code:" + secretCode + "]";
	}
}
