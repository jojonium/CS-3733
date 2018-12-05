package edu.wpi.cs.algol.lamda.showweeklyscheduleadmin;

public class ShowWeeklyScheduleOrganizerRequest {
	String scheduleID;
	String secretCode;
	String date;
	
	/* normal mode constructor */
	public ShowWeeklyScheduleOrganizerRequest(String scheduleID, String code, String date) {
		this.scheduleID = scheduleID;
		this.secretCode = code;
		this.date= date;
	}
	
	/* organizer mode constructor */
	/*public ShowWeeklyScheduleRequest(String sid, String sc, String dt) {
		this.scheduleID = sid;
		this.secretCode = sc;
		this.dateTime = dt;
	}*/
	
	public ShowWeeklyScheduleOrganizerRequest(String scheduleID, String code) {
		this.scheduleID = scheduleID;
		this.secretCode = code;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShowWeeklyScheduleOrganizerRequest [scheduleID=" + scheduleID + ", secret code=" + secretCode + ", date="
				+ date+ "]";
	}
	

}
