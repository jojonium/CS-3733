package edu.wpi.cs.algol.lambda.closetimeslot;

public class CloseTimeSlotRequest {
	String scheduleID;
	String secretCode;
	String date;
	String time;
/**
 * @param scd The secret code of a schedule
 * @param d The date of the meeting
 * @param t The time of the meeting
 */
	public CloseTimeSlotRequest(String sid, String scd, String d, String t) {
		this.scheduleID = sid;
		this.secretCode = scd;
		this.date = d;
		this.time = t;
	}


	@Override
	public String toString() {
		return "CloseTimeSlotRequest [Secret Code=" + secretCode + ", Date=" + date + ", Time=" + time + "]";
	}
}
