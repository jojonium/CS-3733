package edu.wpi.cs.algol.lambda.opentimeslot;

public class OpenTimeSlotResponse {
	
	public String response;
	public String secretCode;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public OpenTimeSlotResponse(String response, int code) {
		this.response = response;
		this.httpCode= code;
	}
	
	/* used for successful responses */
	public OpenTimeSlotResponse(String scheduleID) {
		
		this.scheduleID = scheduleID;
		this.httpCode = 200;
		
	}
	
	public String toString() {
		if (scheduleID != null) {
			return ("Open time slot was successful, respone: " + response + "\n");
		}
		else {
			return ("Open timeslot error: " + httpCode + "\n");
		}
			
	}

}
