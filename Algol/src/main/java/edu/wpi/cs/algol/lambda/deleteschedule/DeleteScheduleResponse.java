package edu.wpi.cs.algol.lambda.deleteschedule;

public class DeleteScheduleResponse {
	public String response;
	public String secretCode;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public DeleteScheduleResponse(String response, int code) {
		this.response = response;
		this.httpCode = code;
	}

	/* used for successful responses */
	public DeleteScheduleResponse(String sid) {
		this.scheduleID = sid;
		this.httpCode = 202;
	}

	public String toString(){
		if (secretCode != null )
			return ("Delete schedule was successful, response: " + response + "\n");
		else
			return ("Delete schedule null error \n");
	}
}
