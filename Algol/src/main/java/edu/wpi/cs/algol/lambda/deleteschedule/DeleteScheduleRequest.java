package edu.wpi.cs.algol.lambda.deleteschedule;

public class DeleteScheduleRequest {
	String secretCode;
	

	public DeleteScheduleRequest(String sid) {
		this.secretCode = sid;
	}


	@Override
	public String toString() {
		return "DeleteScheduleRequest [Secret Code=" + secretCode + "]";
	}
}
