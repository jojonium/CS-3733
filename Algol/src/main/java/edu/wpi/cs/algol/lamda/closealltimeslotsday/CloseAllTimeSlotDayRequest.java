package edu.wpi.cs.algol.lamda.closealltimeslotsday;

public class CloseAllTimeSlotDayRequest {
	String scheduleID;
	String secretCode;
	String date;
	/**
	 * @param scheduleID is Schedule's ID 
	 * @param secretCode is Schedule's secret code
	 * @param date is date of time slot to open
	 * @param time is start time of time slot to open
	 */
	public CloseAllTimeSlotDayRequest(String scheduleID, String secretCode, String date) {

		this.scheduleID = scheduleID;
		this.secretCode = secretCode;
		this.date = date;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CloseAllTimeSlotDayRequest [scheduleID=" + scheduleID + ", secretCode=" + secretCode + ", date=" + date
				+ "]";
	}
	

	
}
