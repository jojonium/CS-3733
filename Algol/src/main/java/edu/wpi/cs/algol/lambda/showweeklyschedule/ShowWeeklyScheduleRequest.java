package edu.wpi.cs.algol.lambda.showweeklyschedule;

public class ShowWeeklyScheduleRequest {
	String scheduleID;
	//String secretCode;
	String dateTime;
	
	/* normal mode constructor */
	public ShowWeeklyScheduleRequest(String scheduleID, String dateTime) {
		this.scheduleID = scheduleID;
		this.dateTime = dateTime;
	}
	
	/* organizer mode constructor */
	/*public ShowWeeklyScheduleRequest(String sid, String sc, String dt) {
		this.scheduleID = sid;
		this.secretCode = sc;
		this.dateTime = dt;
	}*/
	
	public ShowWeeklyScheduleRequest(String scheduleID) {
		this.scheduleID = scheduleID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShowWeeklyScheduleRequest [scheduleID=" + scheduleID + ", dateTime="
				+ dateTime + "]";
	}
	

}
