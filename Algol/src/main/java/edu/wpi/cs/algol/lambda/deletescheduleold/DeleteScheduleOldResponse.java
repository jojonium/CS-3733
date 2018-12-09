package edu.wpi.cs.algol.lambda.deletescheduleold;

public class DeleteScheduleOldResponse {
	public String response;
	public int daysOld;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public DeleteScheduleOldResponse(String response, int code) {
		this.response = response;
		this.httpCode = code;
	}

	/* used for successful responses */
	public DeleteScheduleOldResponse(int dso) {
		this.daysOld = dso;
		this.httpCode = 202;
	}

	public String toString(){
		if (daysOld <0 )
			return ("Delete old schedules was successful, response: " + response + "\n");
		else
			return ("Delete old schedules error: days is invalid \n");
	}
}
