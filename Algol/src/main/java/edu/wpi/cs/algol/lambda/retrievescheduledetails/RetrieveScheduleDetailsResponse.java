package edu.wpi.cs.algol.lambda.retrievescheduledetails;

import java.time.LocalDate;
import java.time.LocalTime;

public class RetrieveScheduleDetailsResponse {
	public String response;
	public String scheduleID;
	public LocalDate startDate;
	public LocalDate endDate;
	public LocalTime startTime;
	public LocalTime endTime;
	public int duration;
	public int httpCode;
	/**
	 * @param scheduleID
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param duration
	 * @param httpCode
	 */
	public RetrieveScheduleDetailsResponse(String scheduleID, LocalDate startDate, LocalDate endDate,
			LocalTime startTime, LocalTime endTime, int duration) {
		super();
		this.scheduleID = scheduleID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.httpCode = 200;
	}
	
	/**
	 * @param scheduleID
	 * @param httpCode
	 */
	public RetrieveScheduleDetailsResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}


}
