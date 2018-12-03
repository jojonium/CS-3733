package edu.wpi.cs.algol.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;


public class TimeSlot {
	
	
	private String secretCode; 
	private LocalDateTime beginDateTime;
	private boolean isOpen;
	private String requester;
	private String scheduleID;
// 11/28/2018 12:12
	
	/* constructor for new TimeSlots */
	public TimeSlot(String beginDateTime, String scheduleID) {
		
		String[] dateTime = beginDateTime.split("T");
		String[] date = dateTime[0].split("-");
		String[] time = dateTime[1].split(":");
		
		int year = Integer.parseInt(date[0]);	// parsing might not work correctly
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);
		
		int hour = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);
		
		this.beginDateTime = LocalDateTime.of(LocalDate.of(year, month, day), 
				LocalTime.of(hour, min));
		this.scheduleID = scheduleID;
		this.isOpen = true;

		
		// randomly generated code

	}
	
	// for creating a meeting
	public TimeSlot(String beginDateTime, String scheduleID, String requester) {
		
		String[] dateTime = beginDateTime.split("T");
		String[] date = dateTime[0].split("-");
		String[] time = dateTime[1].split(":");
		
		int year = Integer.parseInt(date[0]);	// parsing might not work correctly
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);
		
		int hour = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);
		
		this.beginDateTime = LocalDateTime.of(LocalDate.of(year, month, day), 
				LocalTime.of(hour, min));
		
		this.scheduleID = scheduleID;
		this.requester = requester;
		this.secretCode = generateCode();;
		this.isOpen = false;
		
	}
	/* Construct during schedule generation */
	public TimeSlot(LocalDateTime beginDateTime, String scheduleID) {
		this.beginDateTime = beginDateTime;
		this.scheduleID = scheduleID;
		this.isOpen = false;
	}
	/* constructor for existing TimeSlots, prolly don't need though */
	public TimeSlot(String secretCode, String beginDateTime, String isOpen, String requester, String scheduleID) {
		
		String[] dateTime = beginDateTime.split("T");
		String[] date = dateTime[0].split("-");
		String[] time = dateTime[1].split(":");
		
		int year = Integer.parseInt(date[0]);	// parsing might not work correctly
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);
		
		int hour = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);
		
		this.beginDateTime = LocalDateTime.of(LocalDate.of(year, month, day), 
				LocalTime.of(hour, min));
		
		this.secretCode = secretCode;
		this.isOpen = Boolean.valueOf(isOpen);
		this.requester = requester;
		this.scheduleID = scheduleID;
		
	
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

	public LocalDateTime getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(LocalDateTime beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getScheduleId() {
		return scheduleID;
	}

	public void setScheduleId(String scheduleID) {
		this.scheduleID = scheduleID;
	}

	@Override
	public String toString() {
		return "TimeSlot [secretCode=" + secretCode + ", beginDateTime=" + beginDateTime + ", isOpen=" + isOpen
				+ ", requester=" + requester + ", scheduleID=" + scheduleID + "]";
	}


		
}
