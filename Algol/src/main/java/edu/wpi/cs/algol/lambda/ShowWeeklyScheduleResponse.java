package edu.wpi.cs.algol.lambda;

import java.util.ArrayList;

import edu.wpi.cs.algol.model.TimeSlot;

public class ShowWeeklyScheduleResponse {
	String response;
	String id;
	String name;
	String startDate;
	String endDate;
	String startTime;
	String endTime;
	String duration;
	ArrayList<TimeSlot> timeSlots;
	int httpCode;
	
	/* used for errors or other responses that require a message */
	public ShowWeeklyScheduleResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}

	/* used for successful responses */
	public ShowWeeklyScheduleResponse(String id, String name, String startDate, String endDate, String startTime,
			String endTime, String duration, ArrayList<TimeSlot> timeSlots) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.timeSlots = timeSlots;
		this.httpCode = 200;
	}

	public String toString(){
		if (response != null)
			return ("Showing schedule, response: " + response);
		else
			return ("Showing schedule " + name);
	}
}
