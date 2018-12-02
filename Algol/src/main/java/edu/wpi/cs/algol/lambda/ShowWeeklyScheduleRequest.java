package edu.wpi.cs.algol.lambda;

public class ShowWeeklyScheduleRequest {
	String scheduleID;
	String secretCode;
	
	/* normal mode constructor */
	public ShowWeeklyScheduleRequest(String sid) {
		this.scheduleID = sid;
	}
	
	/* organizer mode constructor */
	public ShowWeeklyScheduleRequest(String sid, String sc) {
		this.scheduleID = sid;
		this.secretCode = sc;
	}
	
	@Override
	public String toString() {
		return "ShowWeeklyScheduleRequest [scheduleId=" + scheduleID + ", secretCode=" + secretCode + "]";
	}
}
