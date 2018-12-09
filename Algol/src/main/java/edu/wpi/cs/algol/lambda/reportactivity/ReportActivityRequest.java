package edu.wpi.cs.algol.lambda.reportactivity;

public class ReportActivityRequest {
	String secretCode;
	String scheduleID;

	public ReportActivityRequest(String sid, String scd) {
		this.secretCode = scd;
		this.scheduleID = sid;
	}


	@Override
	public String toString() {
		return "DeleteScheduleRequest [Schedule ID=" + scheduleID + ", Secret Code =" + secretCode + "]";
	}
}
