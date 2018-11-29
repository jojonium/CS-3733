package edu.wpi.cs.algol.lambda;
public class CreateScheduleRequest {
	String name;
	String dateStart;
	String dateEnd;
	String timeStart;
	String timeEnd;
	String duration;
	
	public CreateScheduleRequest (String name, String dateStart, String dateEnd, String timeStart, String timeEnd, String duration) {
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.duration = duration;
	}
	
	public String toString() {
		return "Create("+ name + "," + dateStart + "," + dateEnd + "," + timeStart + "," + timeEnd + "," + duration +  ")";
	}
}
