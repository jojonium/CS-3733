package edu.wpi.cs.algol.lambda.closetimeslot;

public class CloseTimeSlotRequest {
	String secretCode;
	String date;
	String time;
/**
 * @param scd The secret code of a schedule
 * @param d The date of the meeting
 * @param t The time of the meeting
 */
	public CloseTimeSlotRequest(String scd, String d, String t) {
		this.secretCode = scd;
		this.date = d;
		this.time = t;
	}


	@Override
	public String toString() {
		return "CloseTimeSlotRequest [Secret Code=" + secretCode + ", Date=" + date + ", Time=" + time + "]";
	}
}
