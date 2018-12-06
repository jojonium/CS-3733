package edu.wpi.cs.algol.lambda.openalltimeslotstime;

public class OpenAllTimeSlotTimeResponse {
	
	public String response;
	public String secretCode;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public OpenAllTimeSlotTimeResponse(String response, int code) {
		this.response = response;
		this.httpCode= code;
	}
	
	/* used for successful responses */
	public OpenAllTimeSlotTimeResponse(String scheduleID) {
		
		this.scheduleID = scheduleID;
		this.httpCode = 202;
		
	}
	
	public String toString() {
		if (scheduleID != null) {
			return ("Open timeslots was successful, respone: " + response + "\n");
		}
		else {
			return ("Open timeslots error: " + httpCode + "\n");
		}
			
	}

}
