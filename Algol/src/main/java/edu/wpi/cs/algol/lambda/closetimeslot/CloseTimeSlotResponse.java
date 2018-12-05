package edu.wpi.cs.algol.lambda.closetimeslot;

public class CloseTimeSlotResponse {
	public String response;
	public String secretCode;
	public String scheduleID;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public CloseTimeSloteResponse(String response, int code) {
		this.response = response;
		this.httpCode = code;
	}

	/* used for successful responses */
	public CloseTimeSlotResponse(String sid) {
		this.scheduleID = sid;
		this.httpCode = 202;
	}

	public String toString(){
		if (secretCode != null )
			return ("Close Time Slot was successful, response: " + response + "\n");
		else
			return ("Delete schedule null error \n");
	}
}
