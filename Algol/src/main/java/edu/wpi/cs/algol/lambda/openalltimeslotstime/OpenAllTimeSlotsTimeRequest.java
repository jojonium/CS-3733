package edu.wpi.cs.algol.lambda.openalltimeslotstime;

public class OpenAllTimeSlotsTimeRequest {
	String scheduleID;
	String secretCode;
	String time;
	/**
	 * @param scheduleID is Schedule's ID 
	 * @param secretCode is Schedule's secret code
	 * @param date is date of time slot to open
	 * @param time is start time of time slot to open
	 */
	public OpenAllTimeSlotsTimeRequest(String scheduleID, String secretCode, String time) {

		this.scheduleID = scheduleID;
		this.secretCode = secretCode;
		this.time = time;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpenAllTimeSlotDayRequest [scheduleID=" + scheduleID + ", secretCode=" + secretCode + ", time=" + time
				+ "]";
	}
	

	
}
