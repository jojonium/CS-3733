package edu.wpi.cs.algol.lambda.closealltimeslotsday;

public class CloseAllTimeSlotsDayResponse {
	
	public String response;
	public String secretCode;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public CloseAllTimeSlotsDayResponse(String response, int code) {
		this.response = response;
		this.httpCode= code;
	}
	
	/* used for successful responses */
	public CloseAllTimeSlotsDayResponse(String scheduleID) {
		
		this.scheduleID = scheduleID;
		this.httpCode = 202;
		
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
