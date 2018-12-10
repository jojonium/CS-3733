package edu.wpi.cs.algol.lambda.reportactivity;


public class ReportActivityResponse {
	public String response;
	public String scheduleID;
	public String secretCode;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public ReportActivityResponse(String r, int code) {
		this.response = r;
		this.httpCode = code;
	}

	/* used for successful responses */
	public ReportActivityResponse(String sid, String scd) {
		this.scheduleID = sid;
		this.secretCode = scd;
		this.httpCode = 200;
	}

	public String toString(){
		if (scheduleID!=null && secretCode!=null) {
			return "scheduleID: " + scheduleID + "secretCode: " + secretCode;
		}
		
		else
			return ("Schedule ID or Secret Code null error \n");
	}
}
