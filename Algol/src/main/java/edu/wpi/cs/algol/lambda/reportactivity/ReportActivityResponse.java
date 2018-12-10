package edu.wpi.cs.algol.lambda.reportactivity;

import java.util.ArrayList;

import edu.wpi.cs.algol.model.Schedule;

public class ReportActivityResponse {
	public String response;
	public ArrayList<Schedule> schedules;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public ReportActivityResponse(String response, int code) {
		this.response = response;
		this.httpCode = code;
	}

	/* used for successful responses */
	public ReportActivityResponse(String response, ArrayList<Schedule> s) {
		this.response = response;
		this.schedules = s;
		this.httpCode = 200;
	}

	public String toString(){
		if (scheduleID != null )
			return ("Delete schedule was successful, response: " + response + "\n");
		else
			return ("Delete schedule null error \n");
	}
}
