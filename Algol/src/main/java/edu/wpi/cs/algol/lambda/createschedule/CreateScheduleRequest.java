package edu.wpi.cs.algol.lambda.createschedule;
public class CreateScheduleRequest {
	String name;
	String startDate;
	String endDate;
	String startTime;
	String endTime;
	String duration;
	

	public CreateScheduleRequest(String name, String startDate, String endDate, String startTime, String endTime,
			String duration) {

		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		
	}


	@Override
	public String toString() {
		return "CreateScheduleRequest [name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", duration=" + duration + "]";
	}
	
}
