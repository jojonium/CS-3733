package edu.wpi.cs.algol.lambda.deletescheduleold;

public class DeleteScheduleOldRequest {
	String adminPass;
	int daysOld;

	public DeleteScheduleOldRequest(String ap, int dso) {
		this.adminPass = ap;
		this.daysOld = dso;
	}


	@Override
	public String toString() {
		return "DeleteScheduleOldRequest [Admin Password=" + adminPass + ", Days Old =" + daysOld + "]";
	}
}
