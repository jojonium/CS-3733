package edu.wpi.cs.algol.lambda.closealltimeslotstime;

public class CloseAllTimeSlotsTimeResponse {
	
	public String response;
	public String secretCode;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public CloseAllTimeSlotsTimeResponse(String response, int code) {
		this.response = response;
		this.httpCode= code;
	}
	
	/* used for successful responses */
	public CloseAllTimeSlotsTimeResponse(String scheduleID) {
		
		this.scheduleID = scheduleID;
		this.httpCode = 200;
		
	}
	
	public String toString() {
		if (scheduleID != null) {
			return ("Close timeslots was successful, respone: " + response + "\n");
		}
		else {
			return ("Close timeslots error: " + httpCode + "\n");
		}
			
	}

}
