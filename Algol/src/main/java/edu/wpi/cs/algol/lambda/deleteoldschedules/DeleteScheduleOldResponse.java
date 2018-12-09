package edu.wpi.cs.algol.lambda.deleteoldschedules;

public class DeleteScheduleOldResponse {
	public String response;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public DeleteScheduleOldResponse(String response, int code) {
		this.response = response;
		this.httpCode = code;
	}

	/* used for successful responses */
	public DeleteScheduleOldResponse(String sid) {
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
