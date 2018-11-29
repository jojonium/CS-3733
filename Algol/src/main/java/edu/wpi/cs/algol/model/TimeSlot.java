package edu.wpi.cs.algol.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.StringTokenizer;


public class TimeSlot {
	
	
	private String secretCode; 
	private LocalDateTime beginDateTime;
	private boolean isOpen;
	private String requester;
	private String scheduleID;
// 11/28/2018 12:12
	public TimeSlot(String beginDateTime, String scheduleID) {
		
		StringTokenizer stDateTime = new StringTokenizer(beginDateTime);

		int month = Integer.parseInt(stDateTime.nextToken("/"));	// parsing might not work correctly
		int day = Integer.parseInt(stDateTime.nextToken("/"));
		int year = Integer.parseInt(stDateTime.nextToken());
		
		int hour = Integer.parseInt(stDateTime.nextToken(":"));
		int min = Integer.parseInt(stDateTime.nextToken());
		
		this.beginDateTime = LocalDateTime.of(LocalDate.of(year, month, day), 
				LocalTime.of(hour, min));
		this.scheduleID = scheduleID;

		
		// randomly generated code
		this.secretCode = generateCode();

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


		
}
