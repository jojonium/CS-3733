package edu.wpi.cs.algol.model;

import java.awt.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

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


	// month/day/year 
	public Schedule (String name, String startDate, String endDate,
			String startTime, String endTime, int duration ) { 
		// date formatted "(\\d){1,2}([/]){1} (\\d){1,2} ([/]){1}(\\d){4}"
		// time formatted "(\\d){1,2}([:]){1}(\\d){2}"
		StringTokenizer stSDate = new StringTokenizer(startDate,"/");
		StringTokenizer stEDate = new StringTokenizer(endDate,"/");
		StringTokenizer stSTime = new StringTokenizer(startTime,":");
		StringTokenizer stETime = new StringTokenizer(endTime,":");

		int sMonth = Integer.parseInt(stSDate.nextToken());
		int sDay = Integer.parseInt(stSDate.nextToken());
		int sYear = Integer.parseInt(stSDate.nextToken());

		int eMonth = Integer.parseInt(stEDate.nextToken());
		int eDay = Integer.parseInt(stEDate.nextToken());
		int eYear = Integer.parseInt(stEDate.nextToken());

		int sHour = Integer.parseInt(stSTime.nextToken());
		int sMin = Integer.parseInt(stSTime.nextToken());

		int eHour = Integer.parseInt(stETime.nextToken());
		int eMin = Integer.parseInt(stETime.nextToken());

		this.name = name;
		this.startDate = LocalDate.of(sYear, sMonth, sDay);
		this.endDate = LocalDate.of(eYear, eMonth, eDay);
		this.startTime = LocalTime.of(sHour, sMin);
		this.endTime = LocalTime.of(eHour, eMin);
		this.duration = duration;

		// timeslot generation
		for (LocalDate date = this.startDate; date.isBefore(this.endDate); date = date.plusDays(1)) {

			for(LocalTime time = this.startTime; time.isBefore(this.endTime); time = time.plusMinutes(duration)) {

				// loop and add timeslots

			}

		}

		// unique value generations
		this.secretCode = generateCode();
		this.id = generateCode();

	}

	private static String generateCode(){

		String code = "";
		Random r = new Random();
		//48-57, 65-90, 97-122
		for (int i = 0; i < 6; i++) {

			if ((r.nextInt(3)+1) == 1) {
				code += Character.toString((char) (r.nextInt(48-58) + 48));
			}
			else if ((r.nextInt(3)+1) == 1) {
				code += Character.toString((char) (r.nextInt(65-91) + 65));
			}
			else {
				code += Character.toString((char) (r.nextInt(97-123) + 97));
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

	private class TimeSlotIterator implements Iterator<TimeSlot> {
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
	}


}
