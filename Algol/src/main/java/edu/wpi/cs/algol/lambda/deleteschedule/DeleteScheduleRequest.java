package edu.wpi.cs.algol.lambda.deleteschedule;

public class DeleteScheduleRequest {
	String secretCode;
	String scheduleID;

	public DeleteScheduleRequest(String sid, String scd) {
		this.secretCode = scd;
		this.scheduleID = sid;
	}


	@Override
	public String toString() {
		return "DeleteScheduleRequest [Schedule ID=" + scheduleID + ", Secret Code =" + secretCode + "]";
	}
}
