package edu.wpi.cs.algol.lambda.reportactivity;

public class ReportActivityResponse {
	public String response;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public ReportActivityResponse(String response, int code) {
		this.response = response;
		this.httpCode = code;
	}

	/* used for successful responses */
	public ReportActivityResponse(String sid) {
		this.scheduleID = sid;
		this.httpCode = 202;
	}

	public String toString(){
		if (scheduleID != null )
			return ("Delete schedule was successful, response: " + response + "\n");
		else
			return ("Delete schedule null error \n");
	}
}
