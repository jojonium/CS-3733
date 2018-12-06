package edu.wpi.cs.algol.lamda.closealltimeslotsday;

public class CloseAllTimeSlotDayResponse {
	
	public String response;
	public String secretCode;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public CloseAllTimeSlotDayResponse(String response, int code) {
		this.response = response;
		this.httpCode= code;
	}
	
	/* used for successful responses */
	public CloseAllTimeSlotDayResponse(String scheduleID) {
		
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
