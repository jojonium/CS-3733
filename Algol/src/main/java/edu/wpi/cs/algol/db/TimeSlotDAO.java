package edu.wpi.cs.algol.db;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.model.TimeSlot;


//beginDateTime, isOpen, requester, secretCode, 

public class TimeSlotDAO {

	java.sql.Connection conn;

	public TimeSlotDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}

	}

	public TimeSlot getTimeSlot(String scheduleID, LocalDateTime beginDateTime ) throws Exception {

		try {

			TimeSlot timeSlot = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE scheduleID =? AND beginDateTime =?;");
			ps.setString(1, scheduleID);
			ps.setString(2, beginDateTime.toString()); //info needs to be be parsed
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				timeSlot = createTimeSlot(resultSet);
			}

			resultSet.close();
			ps.close();

			return timeSlot;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting timeslot: " + e.getMessage());
		}

	}
	public TimeSlot getTimeSlotFromCode(String scheduleID, String secretCode) throws Exception {

		try {

			TimeSlot timeSlot = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE scheduleID =? AND secretCode =?;");
			ps.setString(1, scheduleID);
			ps.setString(2, secretCode); //info needs to be be parsed
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				timeSlot = createTimeSlot(resultSet);
			}

			resultSet.close();
			ps.close();

			return timeSlot;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting timeslot from secretCode: " + e.getMessage());
		}

	}

	public boolean closeTimeSlot(String scheduleID, String secretCode, String date, String time) throws Exception {

		try {

			// configure input strings
			String[] dateString = date.split("/");
			String[] timeString = time.split(":");

			int month = Integer.parseInt(dateString[0]);
			int dayOfMonth = Integer.parseInt(dateString[1]);
			int year = Integer.parseInt(dateString[2]);

			int hour = Integer.parseInt(timeString[0]);
			int minute = Integer.parseInt(timeString[1]);


			LocalDate inputDate = LocalDate.of(year, month, dayOfMonth);
			LocalTime inputTime = LocalTime.of(hour, minute);
			LocalDateTime dateTime = LocalDateTime.of(inputDate, inputTime);
			ScheduleDAO sDao = new ScheduleDAO();
			Schedule s = sDao.getSchedule(scheduleID);
			if (this.getTimeSlot(scheduleID, dateTime).isOpen() == false) { return true; }
			if (this.getTimeSlot(scheduleID, LocalDateTime.of(inputDate, inputTime)).getSecretCode() == null) {
				if (secretCode.equals(s.getSecretCode())) {
					PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isOpen =? WHERE beginDateTime =? AND scheduleID =?;");
					ps.setString(1, "false");
					ps.setString(2, dateTime.toString());
					ps.setString(3, scheduleID);


					int valsAffected = ps.executeUpdate();
					ps.close();

					return (valsAffected ==1);

				}
				return false;
			}
			return false;
		} catch (Exception e) {
			throw new Exception("Failed in closing timeslot: " + e.getMessage());
		}
	}

	public boolean openTimeSlot(String scheduleID, String secretCode, String date, String time) throws Exception {

		try {

			// configure input strings
			String[] dateString = date.split("/");
			String[] timeString = time.split(":");

			int month = Integer.parseInt(dateString[0]);
			int dayOfMonth = Integer.parseInt(dateString[1]);
			int year = Integer.parseInt(dateString[2]);

			int hour = Integer.parseInt(timeString[0]);
			int minute = Integer.parseInt(timeString[1]);


			LocalDate inputDate = LocalDate.of(year, month, dayOfMonth);
			LocalTime inputTime = LocalTime.of(hour, minute);
			LocalDateTime dateTime = LocalDateTime.of(inputDate, inputTime);
			ScheduleDAO sDao = new ScheduleDAO();
			Schedule s = sDao.getSchedule(scheduleID);
			if (this.getTimeSlot(scheduleID, dateTime).isOpen() == true) { return true; }
			if (this.getTimeSlot(scheduleID, LocalDateTime.of(inputDate, inputTime)).getSecretCode() == null) {
				if (secretCode.equals(s.getSecretCode())) {
					PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isOpen =? WHERE beginDateTime =? AND scheduleID =?;");
					ps.setString(1, "true");
					ps.setString(2, dateTime.toString());
					ps.setString(3, scheduleID);


					int valsAffected = ps.executeUpdate();
					ps.close();

					return (valsAffected ==1);

				}
				return false;
			}
			return false;
		} catch (Exception e) {
			throw new Exception("Failed in opening timeslot: " + e.getMessage());
		}
	}

	public boolean updateTimeSlot(TimeSlot timeSlot) throws Exception {

		try { // include a
			String query = "UPDATE TimeSlots SET requester=?, isOpen=?, secretCode =? WHERE beginDateTime=? AND scheduleID =?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, timeSlot.getRequester());
			ps.setString(2, timeSlot.isOpen() ? "true" : "false");
			ps.setString(3, timeSlot.getSecretCode()); 
			ps.setString(4, timeSlot.getBeginDateTime().toString()); 
			ps.setString(5, timeSlot.getScheduleId()); 		// Schedule ID	
			int valsAffected = ps.executeUpdate();
			ps.close();

			return (valsAffected ==1);
		} catch (Exception e) {
			throw new Exception("Failed to update report: " + e.getMessage());
		}

	}


	public boolean addTimeSlot(TimeSlot timeSlot) throws Exception {

		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO TimeSlots (scheduleID, beginDateTime, isOpen) values(?,?,?);");

			ps.setString(1, timeSlot.getScheduleId());
			ps.setString(2,timeSlot.getBeginDateTime().toString());
			ps.setString(3, (timeSlot.isOpen() ? "true" : "false"));
			ps.execute();

			return true;
		} catch (Exception e) {
			throw new Exception("Fail to add new timeslot: " + e.getMessage());
		}

	}

	public boolean addMeeting(TimeSlot timeSlot) throws Exception {

		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET requester=?, isOpen=?, secretCode =? WHERE beginDateTime=? AND scheduleID =?;");
			ps.setString(1, timeSlot.getRequester());
			ps.setString(2, timeSlot.isOpen() ? "true" : "false"); 
			ps.setString(3, timeSlot.getSecretCode());
			ps.setString(4,timeSlot.getBeginDateTime().toString());
			ps.setString(5,timeSlot.getScheduleId());
			int numAffected = ps.executeUpdate();

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Fail to add new meeting in timeslot: " + e.getMessage());
		}

	}

	public boolean deleteAllTimeSlots(String scheduleID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE from TimeSlots WHERE scheduleID =?;");

			ps.setString(1, scheduleID);
			int numAffected = ps.executeUpdate();

			return (numAffected == 1);
		} catch (Exception e) {
			throw new Exception ("Fail to delete all time slots: " + e.getMessage());
		}
	}

	public boolean cancelMeeting(String scheduleID, LocalDateTime beginDateTime ) throws Exception {

		try {

			PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET requester=?, isOpen=?, secretCode =? WHERE beginDateTime=? AND scheduleID =?;");

			ps.setString(1, null);
			ps.setString(2, "true");
			ps.setString(3, null);
			ps.setString(4, beginDateTime.toString());
			ps.setString(5, scheduleID);
			int numAffected = ps.executeUpdate();

			return (numAffected == 1);
		} catch (Exception e) {
			throw new Exception ("Fail to cancel meeting: "+ e.getMessage());
		}



	}

	// something for getting multiple timeslots for various reasons
	public ArrayList<TimeSlot> getAllTimeSlots(String id) throws Exception{

		ArrayList<TimeSlot> allTimeSlots = new ArrayList<TimeSlot>();

		try {

			// selects all timeslots with the given scheduleID
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots WHERE scheduleID = ?");
			ps.setString(1, id);
			ResultSet resultSet = ps.executeQuery();

			while(resultSet.next()) {
				TimeSlot ts = createTimeSlot(resultSet);
				allTimeSlots.add(ts);
			}

			resultSet.close();
			ps.close();

			return allTimeSlots;


		} catch (Exception e) {
			throw new Exception("Failed in getting all timeslots: " + e.getMessage());
		}


	}

	public ArrayList<TimeSlot> getWeeklyTimeSlots(String scheduleID, String dateStart) throws Exception {

		ArrayList<TimeSlot> weekts = new ArrayList<TimeSlot>();
		//allts = daoT.getAllTimeSlots(id);
		ScheduleDAO daoS = new ScheduleDAO();
		TimeSlotDAO daoT = new TimeSlotDAO();

		Schedule s = daoS.getSchedule(scheduleID);
		TimeSlot startts;

		if (!dateStart.isEmpty()) {			// a valid date has been included
			String[] date = dateStart.split("-");
			//String[] time = dateTime.split(":");

			int month = Integer.parseInt(date[0]);	// parsing might not work correctly
			int day = Integer.parseInt(date[1]);
			int year = Integer.parseInt(date[2]);
			LocalDateTime ldt;
			LocalDate checkDate = LocalDate.of(year, month, day);

			// checks if valid input date 
			if (checkDate.isBefore(s.getStartDate())) {
				checkDate = s.getStartDate();
			}
			if (checkDate.isAfter(s.getEndDate())) {
				checkDate = s.getEndDate();
			}

			ldt = LocalDateTime.of(checkDate, s.getStartTime());
			// check which date schedule begins on
			startts = daoT.getTimeSlot(scheduleID, ldt);

			// starts on a monday
			if(startts.getBeginDateTime().getDayOfWeek().equals(DayOfWeek.MONDAY)) {

				for (int i = 0; i < 5; i ++) { // if day starts on monday

					for(LocalTime time = (s.getStartTime().getMinute()% s.getDuration() == 0) ? s.getStartTime() : s.getStartTime().plusMinutes(s.getDuration() - s.getStartTime().getMinute()%s.getDuration()); time.isBefore(s.getEndTime()); time = time.plusMinutes(s.getDuration())) {
						if (LocalDate.of(year, month, day).plusDays(i).isBefore(s.getEndDate().plusDays(1))) {
							weekts.add(daoT.getTimeSlot(scheduleID, LocalDateTime.of(LocalDate.of(year, month, day).plusDays(i), time)));
						}
					}

				}

			}
			// does not start on a monday
			else {

				int i = 0; // 12/21/2018
				while (!startts.getBeginDateTime().getDayOfWeek().equals(DayOfWeek.MONDAY)) {

					startts = daoT.getTimeSlot(scheduleID, LocalDateTime.of(checkDate.minusDays(i), s.getStartTime()));
					if (startts == null) {
						startts = this.getTimeSlot(scheduleID, LocalDateTime.of(checkDate.minusDays(i).plusDays(1), s.getStartTime()));
						break;
					}

					i++;

				}
				year = startts.getBeginDateTime().getYear();
				month = startts.getBeginDateTime().getMonthValue();
				day = startts.getBeginDateTime().getDayOfMonth();
				LocalDate ldate = LocalDate.of(year, month, day);
				int j = 0;
				while (ldate.plusDays(j).isBefore(s.getEndDate().plusDays(1)) && !LocalDateTime.of(ldate.plusDays(j), s.getStartTime()).getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
					if (LocalDate.of(year, month, day).plusDays(j).isBefore(s.getEndDate().plusDays(1))) {
						for(LocalTime time = (s.getStartTime().getMinute()% s.getDuration() == 0) ? s.getStartTime() : s.getStartTime().plusMinutes(s.getDuration() - s.getStartTime().getMinute()%s.getDuration()); time.isBefore(s.getEndTime()); time = time.plusMinutes(s.getDuration())) {
							if (daoT.getTimeSlot(scheduleID, LocalDateTime.of(LocalDate.of(year, month, day).plusDays(j), time)) !=null ) {
								weekts.add(daoT.getTimeSlot(scheduleID, LocalDateTime.of(LocalDate.of(year, month, day).plusDays(j), time)));
							}
						}
					}
					j++;
				}
			}


			return weekts;


		}
		// no date has been indicated to show (shows default
		else {

			startts = daoT.getTimeSlot(scheduleID, LocalDateTime.of(s.getStartDate(),s.getStartTime()));

			int day = startts.getBeginDateTime().getDayOfMonth();
			int month = startts.getBeginDateTime().getMonthValue();
			int year = startts.getBeginDateTime().getYear();
			if (startts.getBeginDateTime().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
				for(LocalTime time = (s.getStartTime().getMinute()% s.getDuration() == 0) ? s.getStartTime() : s.getStartTime().plusMinutes(s.getDuration() - s.getStartTime().getMinute()%s.getDuration()); time.isBefore(s.getEndTime()); time = time.plusMinutes(s.getDuration())) {

					startts = daoT.getTimeSlot(scheduleID, LocalDateTime.of(LocalDate.of(year, month, day), time));
					weekts.add(startts);

				}
				

			}
			else {
				for (int i = 0;LocalDate.of(year, month, day).plusDays(i).plusDays(0).isBefore(s.getEndDate().plusDays(1).plusDays(0)); i ++) {

					if (!startts.getBeginDateTime().getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
						for(LocalTime time = (s.getStartTime().getMinute()% s.getDuration() == 0) ? s.getStartTime() : s.getStartTime().plusMinutes(s.getDuration() - s.getStartTime().getMinute()%s.getDuration()); time.isBefore(s.getEndTime()); time = time.plusMinutes(s.getDuration())) {

							startts = daoT.getTimeSlot(scheduleID, LocalDateTime.of(LocalDate.of(year, month, day).plusDays(i), time));
							weekts.add(startts);

						}
					}
				}
			}


			return weekts;

		}


	}

	/*
	 * Open All slots on given day date as mm/dd/yyyy or mm-dd-yyyy
	 */

	public boolean openTimeSlotsOnDay(String scheduleID, String secretCode, String date) throws Exception {
		try {

			// acquiring time
			ScheduleDAO sDao = new ScheduleDAO();
			Schedule s = sDao.getSchedule(scheduleID);
			LocalTime startTime = s.getStartTime();


			if (secretCode.equals(s.getSecretCode())) {

				boolean status = false;
				LocalTime t = startTime;
				while (t.isBefore(s.getEndTime())) {

					status = this.openTimeSlot(scheduleID, secretCode, date, t.toString());

					t=t.plusMinutes(s.getDuration());
					t=t.plusMinutes(0);
				}
				return status;

			}
			throw new Exception();

		} catch (Exception e) {
			throw new Exception("Failed in opening timeslots on day: " + e.getMessage());
		}

	}

	/*
	 * Open All slots on given time
	 */

	public boolean openTimeSlotsAtTime(String scheduleID, String secretCode, String time) throws Exception {
		try {

			// configure input strings



			ScheduleDAO sDao = new ScheduleDAO();
			Schedule s = sDao.getSchedule(scheduleID);
			LocalDate startDate = s.getStartDate();
			if (secretCode.equals(s.getSecretCode())) {
				boolean status = true;
				LocalDate d = startDate;
				while (d.isBefore(s.getEndDate().plusDays(1))) {
					String[] dateS = d.toString().split("-");
					String date ="";
					date = dateS[1]+ "/" + dateS[2]+ "/" + dateS[0];
					//	if (this.getTimeSlot(scheduleID, LocalDateTime.of(d,LocalTime.of(hour, minute))).getSecretCode() == null) {
					if (!LocalDate.of(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]), Integer.parseInt(dateS[2])).getDayOfWeek().equals(DayOfWeek.SATURDAY)
							&& !LocalDate.of(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]), Integer.parseInt(dateS[2])).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
						status = this.openTimeSlot(scheduleID, secretCode, date, time);
					}
					//	}
					d=d.plusDays(1);
					d=d.plusDays(0);
				}
				return status;
			}
			else
				return false;
			//if (this.getTimeSlot(scheduleID, dateTime).isOpen() == false) { return true; }

			/*if (secretCode.equals(s.getSecretCode())) {
				PreparedStatement ps = conn.prepareStatement("UPDATE TimeSlots SET isOpen =? WHERE beginDateTime =? AND scheduleID =?;");
				ps.setString(1, "false");
				ps.setString(2, dateTime.toString());
				ps.setString(3, scheduleID);


				int valsAffected = ps.executeUpdate();
				ps.close();

				return (valsAffected ==1);

			}
			return false; */

		} catch (Exception e) {
			throw new Exception("Failed in opening timeslots at time: " + e.getMessage() + e);
		}
	}

	/*
	 * Close All slots on given day
	 */

	public boolean closeTimeSlotsOnDay(String scheduleID, String secretCode, String date) throws Exception {
		try {

			// configure input strings

			// acquiring time
			ScheduleDAO sDao = new ScheduleDAO();
			Schedule s = sDao.getSchedule(scheduleID);
			LocalTime startTime = s.getStartTime();



			//			LocalDate nextDate = inputDate.plusDays(1);
			//LocalTime inputTime = LocalTime.of(startTime.getHour(), startTime.getMinute());

			//			LocalDateTime nextDateTime = LocalDateTime.of(nextDate, inputTime);
			// if (this.getTimeSlot(scheduleID, dateTime).isOpen() == true) { return true; }

			if (secretCode.equals(s.getSecretCode())) {
				/*int valsAffected = 0;
				PreparedStatement ps = null;
				LocalTime t = startTime;
				while (t.isBefore(s.getEndTime().plusMinutes(s.getDuration()))) {
					LocalDateTime dateTime = LocalDateTime.of(inputDate, t);
					ps = conn.prepareStatement("UPDATE TimeSlots SET isOpen =? WHERE beginDateTime =? AND scheduleID =?;");
					ps.setString(1, "false");
					ps.setString(2, dateTime.toString());
					ps.setString(3, scheduleID);
					t.plusMinutes(s.getDuration());
					valsAffected = ps.executeUpdate();


				}
				ps.close();*/
				boolean status = false;
				LocalTime t = startTime;
				while (t.isBefore(s.getEndTime())) {

					status = this.closeTimeSlot(scheduleID, secretCode, date, t.toString());

					t=t.plusMinutes(s.getDuration());
					t=t.plusMinutes(0);
				}
				return status;

			}
			return false;

		} catch (Exception e) {
			throw new Exception("Failed in closing timeslots on day: " + e.getMessage());
		}

	}


	/*
	 * Close All slots on given time
	 */
	public boolean closeTimeSlotsAtTime(String scheduleID, String secretCode, String time) throws Exception {
		try {

			ScheduleDAO sDao = new ScheduleDAO();
			Schedule s = sDao.getSchedule(scheduleID);
			LocalDate startDate = s.getStartDate();
			if (secretCode.equals(s.getSecretCode())) {
				boolean status = false;
				LocalDate d = startDate;
				while (d.isBefore(s.getEndDate().plusDays(1))) { 
					String[] dateS = d.toString().split("-");
					String date ="";
					date = dateS[1]+ "/" + dateS[2]+ "/" + dateS[0];
					if (!LocalDate.of(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]), Integer.parseInt(dateS[2])).getDayOfWeek().equals(DayOfWeek.SATURDAY)
							&& !LocalDate.of(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]), Integer.parseInt(dateS[2])).getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
						status = this.closeTimeSlot(scheduleID, secretCode, date, time);
					}
					d=d.plusDays(1);
					d=d.plusDays(0);
				}
				return status;
			}else 
				return false;
		}  catch (Exception e) {
			throw new Exception("Failed in closing timeslot at time: " + e.getMessage());
		}
	}

	// shows available timeslots filter 
	/*public ArrayList<TimeSlot> showAvailableTimeslots(String scheduleID, String startDate, String endDate, String startTime, String endTime) throws Exception {

		try {

			Schedule s = new ScheduleDAO().getSchedule(scheduleID);
			int duration = s.getDuration();
			ArrayList<TimeSlot> availSlots = new ArrayList<TimeSlot>();

			int startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
			// checks if input startDate is not empty, and is before the schedule's startDate, else use default
			String[] dateStartArray = (!startDate.isEmpty() && (rewriteS(s.getStartDate().toString()).compareTo(startDate) < 0 )) ? startDate.split("/") : rewriteS(s.getStartDate().toString()).split("/");
			startMonth = Integer.parseInt(dateStartArray[0]);
			startDay = Integer.parseInt(dateStartArray[1]);
			startYear = Integer.parseInt(dateStartArray[2]);

			// checks if input end date is not empty and is before schedule's end date, else use default
			String[] dateEndArray = (!endDate.isEmpty() && (rewriteS(s.getEndDate().toString()).compareTo(endDate) < 0 )) ? endDate.split("/") : rewriteS(s.getEndDate().toString()).split("/");
			endMonth = Integer.parseInt(dateEndArray[0]);
			endDay = Integer.parseInt(dateEndArray[1]);
			endYear = Integer.parseInt(dateEndArray[2]);


			// check input start time is not empty and is after schedule's start time, else use default
			String[] timeStartArray = (!startTime.isEmpty() && convLT(startTime).isAfter(s.getStartTime())) ? startTime.split(":") : s.getStartTime().toString().split(":");
			startHour = Integer.parseInt(timeStartArray[0]);
			startMinute = Integer.parseInt(timeStartArray[1]);
			// check input end time is not empty and is before schedule's end time, else use default
			String[] timeEndArray = (!endTime.isEmpty() && convLT(endTime).isBefore(s.getEndTime())) ? endTime.split(":") : s.getEndTime().toString().split(":");
			endHour = Integer.parseInt(timeEndArray[0]);
			endMinute = Integer.parseInt(timeEndArray[1]);

			// fix start and end times to be evenly divisible to the hour by duration
			if (startMinute % duration != 0) {
				startMinute += duration - (startMinute %duration);
			}

			if ((endMinute % duration) != 0) {
				endMinute -= (endMinute % duration);
			}

			//LocalDateTime to be entered
			LocalDate sl = LocalDate.of(startYear, startMonth, startDay);
			LocalTime st = LocalTime.of(startHour, startMinute);
			LocalDate el = LocalDate.of(endYear, endMonth, endDay);
			LocalTime et = LocalTime.of(endHour, endMinute);

			while (sl.isBefore(el.plusDays(1))) {

				while (st.isBefore(et)) {
					TimeSlot ts = getTimeSlot(scheduleID, LocalDateTime.of(sl, st));
					if (ts.isOpen() == true) {
						availSlots.add(ts);
					}
					st =st.plusMinutes(duration);
				}
				st = LocalTime.of(startHour, startMinute);
				sl = sl.plusDays(1).plusDays(0);
			}


			return availSlots;

		} catch (Exception e) {
			throw new Exception("Error in showing available timeslots: " + e.getMessage());
		}
	} */

	/*
	 * method that filters available timeslot based on user input on parameters: 
	 * 	month, year, day of week, day of month, and time. 
	 */
	public ArrayList<TimeSlot> showAvailableTimeSlots(String scheduleID, String month, String year, String dayOfWeek, String day, String time) throws Exception {

		try {

			/* Configure input */	// assumes only valid rounded times
			int inputMonth, inputYear, inputDay;
			String[] inputTime;
			int inputHour, inputMinute;
			String weekday = dayOfWeek;

			inputMonth = (!month.isEmpty()) ? Integer.parseInt(month) : -1;
			inputYear = (!year.isEmpty()) ? Integer.parseInt(year) : -1;
			inputDay = (!day.isEmpty()) ? Integer.parseInt(day) : -1;

			if (!time.isEmpty()) {
				inputTime = time.split(":");
				inputHour = Integer.parseInt(inputTime[0]);
				inputMinute = Integer.parseInt(inputTime[1]);
			}
			else {
				inputHour = -1;
				inputMinute = -1;
			}


			ArrayList<TimeSlot> timeslots = this.getAllTimeSlots(scheduleID);


			if (inputMonth > 0) {
				timeslots = this.filterByMonth(timeslots, inputMonth);
			}
			if (inputYear > 0) {
				timeslots = this.filterByYear(timeslots, inputYear);
			}
			if (inputDay > 0) {
				timeslots = this.filterByDay(timeslots, inputDay);
			}

			if (!weekday.isEmpty()) {
				timeslots = this.filterByDayOfWeek(timeslots, weekday);
			}
			if (inputHour > -1 && inputMinute > -1) {
				timeslots = this.filterByTime(timeslots, inputHour, inputMinute);
			}


			return timeslots;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in showing available TimeSlots" + e.getMessage());

		}
	}


	public TimeSlot createTimeSlot(ResultSet resultSet) throws Exception {

		String beginDateTime = resultSet.getString("beginDateTime");
		String scheduleID = resultSet.getString("scheduleID");
		String secretCode = resultSet.getString("secretCode");
		String requester = resultSet.getString("requester");
		String isOpen = resultSet.getString("isOpen");
		return new TimeSlot(secretCode, beginDateTime, isOpen, requester, scheduleID);
	}

	/* additional methods to reformat strings*/ 
	/*private String rewriteS(String s) {

		String[] dateArray = s.split("-");
		int year = Integer.parseInt(dateArray[0]);
		int month= Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[2]);



		return (month +"/"+ day+"/" + year );


	}*/


	/*private LocalTime convLT(String s){
		String[] time = s.split(":");
		int hour = Integer.parseInt(time[0]);
		int minute = Integer.parseInt(time[1]);
		return LocalTime.of(hour, minute);

	}*/

	private ArrayList<TimeSlot> filterByMonth(ArrayList<TimeSlot> slots, int month) {
		ArrayList<TimeSlot> filteredSlots = new ArrayList<TimeSlot>();
		if (slots.size() > 0) {
			for (int i = 0; i < slots.size(); i++) {
				LocalDateTime ldt = slots.get(i).getBeginDateTime();
				if(ldt.getDayOfMonth() == month && slots.get(i).isOpen()) {
					filteredSlots.add(slots.get(i));
				}
			}
		}
		return filteredSlots;

	}

	private ArrayList<TimeSlot> filterByYear(ArrayList<TimeSlot> slots, int year) {
		ArrayList<TimeSlot> filteredSlots = new ArrayList<TimeSlot>();
		if(slots.size() > 0) {
			for (int i = 0; i < slots.size(); i++) {
				LocalDateTime ldt = slots.get(i).getBeginDateTime();
				if(ldt.getYear() == year && slots.get(i).isOpen()) {
					filteredSlots.add(slots.get(i));
				}
			}
		}

		return filteredSlots;
	}

	// selets timeslots of same day of month
	private ArrayList<TimeSlot> filterByDay(ArrayList<TimeSlot> slots, int day){
		ArrayList<TimeSlot> filteredSlots = new ArrayList<TimeSlot>();
		if(slots.size() > 0) {
			for (int i = 0; i < slots.size(); i++) {
				LocalDateTime ldt = slots.get(i).getBeginDateTime();
				if(ldt.getDayOfMonth() == day && slots.get(i).isOpen()) {
					filteredSlots.add(slots.get(i));
				}
			}
		}

		return filteredSlots;
	}

	private ArrayList<TimeSlot> filterByDayOfWeek(ArrayList<TimeSlot> slots, String dayOfWeek){
		ArrayList<TimeSlot> filteredSlots = new ArrayList<TimeSlot>();
		if (slots.size() > 0) {
			for (int i = 0; i < slots.size(); i++) {
				LocalDateTime ldt = slots.get(i).getBeginDateTime();
				if(ldt.getDayOfWeek().equals(DayOfWeek.valueOf(dayOfWeek)) && slots.get(i).isOpen()) {
					filteredSlots.add(slots.get(i));
				}
			}
		}

		return filteredSlots;
	}

	private ArrayList<TimeSlot> filterByTime(ArrayList<TimeSlot> slots, int hour, int min) {
		ArrayList<TimeSlot> filteredSlots = new ArrayList<TimeSlot>();
		if(slots.size() > 0) {
			for (int i = 0; i < slots.size(); i++) {
				LocalDateTime ldt = slots.get(i).getBeginDateTime();
				if(ldt.getHour() == hour && ldt.getMinute() == min && slots.get(i).isOpen()) {
					filteredSlots.add(slots.get(i));
				}
			}
		}

		return filteredSlots;
	}

}