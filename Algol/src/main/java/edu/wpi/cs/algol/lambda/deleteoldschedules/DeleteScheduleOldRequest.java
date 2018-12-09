package edu.wpi.cs.algol.lambda.deleteoldschedules;

public class DeleteScheduleOldRequest {
	String secretCode;
	String scheduleID;

	public DeleteScheduleOldRequest(String sid, String scd) {
		this.secretCode = scd;
		this.scheduleID = sid;
	}


	@Override
	public String toString() {
		return "DeleteScheduleRequest [Schedule ID=" + scheduleID + ", Secret Code =" + secretCode + "]";
	}
}
