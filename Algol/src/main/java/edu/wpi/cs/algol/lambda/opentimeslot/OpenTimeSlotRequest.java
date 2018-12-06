package edu.wpi.cs.algol.lambda.opentimeslot;

public class OpenTimeSlotRequest {
	String scheduleID;
	String secretCode;
	String date;
	String time;
	/**
	 * @param scheduleID is Schedule's ID 
	 * @param secretCode is Schedule's secret code
	 * @param date is date of time slot to open
	 * @param time is start time of time slot to open
	 */
	public OpenTimeSlotRequest(String scheduleID, String secretCode, String date, String time) {

		this.scheduleID = scheduleID;
		this.secretCode = secretCode;
		this.date = date;
		this.time = time;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpenTimeSlotRequest [scheduleID=" + scheduleID + ", secretCode=" + secretCode + ", date=" + date
				+ ", time=" + time + "]";
	}
	

	
}
