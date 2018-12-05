package edu.wpi.cs.algol.lambda.createmeeting;

public class CreateMeetingRequest {
	String requester;
	String scheduleID;
	String date;
	String time;
	
	public CreateMeetingRequest (String r, String sid, String d, String t) {
		this.requester = r;
		this.scheduleID = sid;
		this.date = d;
		this.time = t;
	}
	
	public String toString() {
		return "CreateScheduleRequest [requester:" + requester + "schedule ID" + scheduleID + 
				"date:" + date + "time:" + time + "]";
	}
}
