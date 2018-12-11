package edu.wpi.cs.algol.lambda.showweeklyscheduleorganizer;

public class ShowWeeklyScheduleOrganizerRequest {
	String scheduleID;
	String secretCode;
	String date;
	
	/* normal mode constructor */
	public ShowWeeklyScheduleOrganizerRequest(String scheduleID, String secretCode, String date) {
		this.scheduleID = scheduleID;
		this.secretCode = secretCode;
		this.date= date;
	}
	
	/* organizer mode constructor */
	/*public ShowWeeklyScheduleRequest(String sid, String sc, String dt) {
		this.scheduleID = sid;
		this.secretCode = sc;
		this.dateTime = dt;
	}*/
	// not necessary
	public ShowWeeklyScheduleOrganizerRequest(String scheduleID, String secretCode) {
		this.scheduleID = scheduleID;
		this.secretCode = secretCode;
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
