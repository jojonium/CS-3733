package edu.wpi.cs.algol.lambda.showweeklyschedule;

public class ShowWeeklyScheduleRequest {
	String scheduleID;
	String secretCode;
	String dateTime;
	
	/* normal mode constructor */
	/*public ShowWeeklyScheduleRequest(String sid, String dt) {
		this.scheduleID = sid;
		this.dateTime = dt;
	}*
	
	/* organizer mode constructor */
	/*public ShowWeeklyScheduleRequest(String sid, String sc, String dt) {
		this.scheduleID = sid;
		this.secretCode = sc;
		this.dateTime = dt;
	}*/
	
	public ShowWeeklyScheduleRequest(String sid) {
		this.scheduleID = sid;
	}
	
	@Override
	public String toString() {
		return "ShowWeeklyScheduleRequest [scheduleId=" + scheduleID + "]";
	}
}
