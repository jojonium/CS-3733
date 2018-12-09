package edu.wpi.cs.algol.lamda.showweeklyscheduleorganizer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import edu.wpi.cs.algol.model.TimeSlot;

public class ShowWeeklyScheduleOrganizerResponse {
	public String response;

	public String name;
	public LocalDate startDate; 
	public LocalDate endDate;	// last available date in showweeklyschedule
	public LocalTime startTime; 
	public LocalTime endTime;
	public int duration;
	public ArrayList<TimeSlot> timeSlots;
	public String hasPreviousWeek;
	public String hasNextWeek;
	public int httpCode;

	/* used for errors or other responses that require a message */
	public ShowWeeklyScheduleOrganizerResponse(String response, int code) {
		this.response = response;
		//this.ts = new ArrayList<TimeSlot>();
		this.httpCode = code;
	}

	/* used for successful responses */
	public ShowWeeklyScheduleOrganizerResponse(String name, LocalDate startDate, LocalDate endDate, LocalTime startTime,
			LocalTime endTime, int duration, ArrayList<TimeSlot> timeSlots, String hasPreviousWeek, String hasNextWeek) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.timeSlots = timeSlots;
		this.hasPreviousWeek = hasPreviousWeek;
		this.hasNextWeek = hasNextWeek;
		this.httpCode = 200;
	}

	@Override
	public String toString() {

		if (timeSlots != null) 
			return "ShowWeeklyScheduleOrganizerResponse [name=" + name + ", startDate=" + startDate
					+ ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime + ", duration="
					+ duration + ", timeSlot=" + timeSlots + ", hasPreviousWeek=" + hasPreviousWeek + 
					", hasNextWeek=" + hasNextWeek + ", httpCode=" + httpCode + "]";
		else
			return "response=\" " + response + ", httpCode=" + httpCode + "]";
	
	}

	/*public String toString(){
		if (ts != null) 
			return (name + startDate + endDate + startTime + );
		else
			return (response + " " + httpCode + "\n");
	}*/



}
