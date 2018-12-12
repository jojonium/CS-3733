package edu.wpi.cs.algol.lambda.reportactivity;

public class ReportActivityRequest {
	String adminPass;
	int pastHour;
	public ReportActivityRequest(String ap, int ph) {
		this.adminPass = ap;
		this.pastHour = ph;
	}


	@Override
	public String toString() {
		return "ReportActivityRequest [Admin Password=" + adminPass + ", Hours back =" + pastHour + "]";
	}
}
