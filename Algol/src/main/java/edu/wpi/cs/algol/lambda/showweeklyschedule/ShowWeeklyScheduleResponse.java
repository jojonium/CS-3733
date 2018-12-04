package edu.wpi.cs.algol.lambda.showweeklyschedule;

import java.util.ArrayList;

import edu.wpi.cs.algol.model.TimeSlot;

public class ShowWeeklyScheduleResponse {
	public String response;
	public ArrayList<TimeSlot> ts;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public ShowWeeklyScheduleResponse(String response, int code) {
		this.response = response;
		//this.ts = new ArrayList<TimeSlot>();
		this.httpCode = code;
	}

	/* used for successful responses */
	public ShowWeeklyScheduleResponse(String response, ArrayList<TimeSlot> ts) {
		this.response = response;
		this.ts = ts;
		this.httpCode = 200;
	}

	public String toString(){
		
			return ("Showing schedule was successful, response:" + ts.toString()+ " \n");
		
	}
	
	
}
