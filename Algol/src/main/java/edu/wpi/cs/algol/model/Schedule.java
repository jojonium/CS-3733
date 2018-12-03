package edu.wpi.cs.algol.model;

//import java.awt.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.NoSuchElementException;
import java.util.Random;


import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class Schedule {
	private String secretCode;
	private String id;
	public final String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	public final int duration;
	private ArrayList<TimeSlot> timeSlots;

	public LambdaLogger logger = null;
	// month/day/year 
	public Schedule (String name, String startDate, String endDate,
			String startTime, String endTime, int duration ) { 
		// date formatted "(\\d){1,2}([/]){1} (\\d){1,2} ([/]){1}(\\d){4}"
		// time formatted "(\\d){1,2}([:]){1}(\\d){2}"
		/*StringTokenizer stSDate = new StringTokenizer(startDate,"/");
		StringTokenizer stEDate = new StringTokenizer(endDate,"/");
		StringTokenizer stSTime = new StringTokenizer(startTime,":");
		StringTokenizer stETime = new StringTokenizer(endTime,":");
		
		
		int sMonth = Integer.parseInt(stSDate.nextToken("/"));
		logger.log("Parse sMonth: " + sMonth + "\n");
		int sDay = Integer.parseInt(stSDate.nextToken("/"));
		logger.log("Parse sDay: " + sDay + "\n");
		int sYear = Integer.parseInt(stSDate.nextToken());
		logger.log("Parse sYear: " + sYear + "\n");

		int eMonth = Integer.parseInt(stEDate.nextToken("/"));
		logger.log("Parse eMonth: " + eMonth + "\n");
		int eDay = Integer.parseInt(stEDate.nextToken("/"));
		logger.log("Parse eDay: " + eDay + "\n");
		int eYear = Integer.parseInt(stEDate.nextToken());
		logger.log("Parse eYear: " + eYear + "\n");
		
		int sHour = Integer.parseInt(stSTime.nextToken(":"));
		logger.log("Parse sHour: " + sHour + "\n");
		int sMin = Integer.parseInt(stSTime.nextToken());
		logger.log("Parse sMin: " + sMin + "\n");

		int eHour = Integer.parseInt(stETime.nextToken(":"));
		logger.log("Parse eHour: " + eHour + "\n");
		int eMin = Integer.parseInt(stETime.nextToken());
		logger.log("Parse eMin: " + eMin + "\n");
		 */
		int startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
		String[] dateStartArray = startDate.split("/");
		startMonth = Integer.parseInt(dateStartArray[0]);
		startDay = Integer.parseInt(dateStartArray[1]);
		startYear = Integer.parseInt(dateStartArray[2]);

		String[] dateEndArray = endDate.split("/");
		endMonth = Integer.parseInt(dateEndArray[0]);
		endDay = Integer.parseInt(dateEndArray[1]);
		endYear = Integer.parseInt(dateEndArray[2]);
	


		String[] timeStartArray = startTime.split(":");
		startHour = Integer.parseInt(timeStartArray[0]);
		startMinute = Integer.parseInt(timeStartArray[1]);

		String[] timeEndArray = endTime.split(":");
		endHour = Integer.parseInt(timeEndArray[0]);
		endMinute = Integer.parseInt(timeEndArray[1]);

		this.name = name;
		this.startDate = LocalDate.of(startYear, startMonth, startDay);
		this.endDate = LocalDate.of(endYear, endMonth, endDay);
		this.startTime = LocalTime.of(startHour, startMinute);
		this.endTime = LocalTime.of(endHour, endMinute);
		this.duration = duration;

		// timeslot generation
		for (LocalDate date = this.startDate; date.isBefore(this.endDate); date = date.plusDays(1)) {

			for(LocalTime time = this.startTime; time.isBefore(this.endTime); time = time.plusMinutes(duration)) {

				//timeSlots.add(new TimeSlot(,))

			}

		}

		// unique value generations
		this.secretCode = generateCode();
		this.id = generateCode();

	}
	
	public Schedule(String secretCode, String id, String name, String startDate, String endDate,
	String startTime, String endTime, int duration) {
		int startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
		String[] dateStartArray = startDate.split("/");
		startMonth = Integer.parseInt(dateStartArray[0]);
		startDay = Integer.parseInt(dateStartArray[1]);
		startYear = Integer.parseInt(dateStartArray[2]);

		String[] dateEndArray = endDate.split("/");
		endMonth = Integer.parseInt(dateEndArray[0]);
		endDay = Integer.parseInt(dateEndArray[1]);
		endYear = Integer.parseInt(dateEndArray[2]);
	


		String[] timeStartArray = startTime.split(":");
		startHour = Integer.parseInt(timeStartArray[0]);
		startMinute = Integer.parseInt(timeStartArray[1]);

		String[] timeEndArray = endTime.split(":");
		endHour = Integer.parseInt(timeEndArray[0]);
		endMinute = Integer.parseInt(timeEndArray[1]);

		this.name = name;
		this.startDate = LocalDate.of(startYear, startMonth, startDay);
		this.endDate = LocalDate.of(endYear, endMonth, endDay);
		this.startTime = LocalTime.of(startHour, startMinute);
		this.endTime = LocalTime.of(endHour, endMinute);
		this.duration = duration;
		
		this.secretCode = secretCode;
		this.id = id;
	}

	private static String generateCode(){

		String code = "";
		Random r = new Random();
		//48-57, 65-90, 97-122
		for (int i = 0; i < 6; i++) {
			
			if ((r.nextInt(3)+1) == 1) {
				code += Character.toString((char) (r.nextInt(58-48) + 48));
			}
			else if ((r.nextInt(3)+1) == 1) {
				code += Character.toString((char) (r.nextInt(91-65) + 65));
			}
			else {
				code += Character.toString((char) (r.nextInt(123-97) + 97));
			}

		}

		return code;

	}

	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	/*private class TimeSlotIterator implements Iterator<TimeSlot> {
		private int pointer;
		private int lastSlot;
		
		
		public TimeSlotIterator(int start, int lastSlot) {
			this.pointer = start;
			this.lastSlot = lastSlot;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return this.pointer != lastSlot;
		}

		@Override
		public TimeSlot next() {
			// TODO Auto-generated method stub
			if(this.hasNext()) {
				int current = pointer;
				pointer++;
				return timeSlots.get(current);
			}
			throw new NoSuchElementException();
			
		}
	}
	public Iterator<TimeSlot> iterator() {
		return new TimeSlotIterator(0,this.timeSlots.size());
	}*/

	@Override
	public String toString() {
		return "Schedule [secretCode=" + secretCode + ", id=" + id + ", name=" + name + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime + ", duration="
				+ duration + "]";
	}


}
