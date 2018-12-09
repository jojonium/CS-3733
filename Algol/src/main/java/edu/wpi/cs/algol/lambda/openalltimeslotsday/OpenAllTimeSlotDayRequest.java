package edu.wpi.cs.algol.lambda.openalltimeslotsday;

public class OpenAllTimeSlotDayRequest {
	String scheduleID;
	String secretCode;
	String date;
	/**
	 * @param scheduleID is Schedule's ID 
	 * @param secretCode is Schedule's secret code
	 * @param date is date of time slot to open
	 * @param time is start time of time slot to open
	 */
	public OpenAllTimeSlotDayRequest(String scheduleID, String secretCode, String date) {

		this.scheduleID = scheduleID;
		this.secretCode = secretCode;
		this.date = date;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpenAllTimeSlotDayRequest [scheduleID=" + scheduleID + ", secretCode=" + secretCode + ", date=" + date
				+ "]";
	}
	

	
}
