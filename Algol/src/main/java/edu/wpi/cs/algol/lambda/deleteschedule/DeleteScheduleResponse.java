package edu.wpi.cs.algol.lambda.deleteschedule;

import java.util.ArrayList;

import edu.wpi.cs.algol.model.TimeSlot;

public class DeleteScheduleResponse {
	public String response;
	public String secretCode;
	public String scheduleID;
	public String name;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public DeleteScheduleResponse(String response, String sid, String scd, String name,  int code) {
		this.response = response;
		this.scheduleID = sid;
		this.secretCode = scd;
		this.name = name;
		this.httpCode = code;
	}

	/* used for successful responses */
	public DeleteScheduleResponse(String response, String sid, String scd, String name) {
		this.response = response;
		this.scheduleID = sid;
		this.secretCode = scd;
		this.name = name;
		this.httpCode = 202;
	}

	public String toString(){
		if (secretCode != null )
			return ("Delete schedule was successful, response: " + response + "\n");
		else
			return ("Delete schedule null error \n");
	}
}
