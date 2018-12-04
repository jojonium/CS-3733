package edu.wpi.cs.algol.lambda.deleteschedule;

import java.util.ArrayList;

import edu.wpi.cs.algol.model.TimeSlot;

public class DeleteScheduleResponse {
	public String response;
	public ArrayList<TimeSlot> ts;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public DeleteScheduleResponse(String response, int code) {
		this.response = response;
		this.ts = new ArrayList<TimeSlot>();
		this.httpCode = code;
	}

	/* used for successful responses */
	public DeleteScheduleResponse(String response, ArrayList<TimeSlot> ts) {
		this.response = response;
		this.ts = ts;
		this.httpCode = 200;
	}

	public String toString(){
		if (ts != null)
			return ("Delete schedule was successful, response: " + ts.size() + "\n");
		else
			return ("Delete schedule null error \n");
	}
}
