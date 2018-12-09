package edu.wpi.cs.algol.db;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

//import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.model.TimeSlot;

public class ScheduleDAO {

	java.sql.Connection conn;

	//LambdaLogger logger = null;

	public ScheduleDAO() {
		//logger = l;
		//logger.log("ScheduleDAO constructor...\n");
		try {
			conn = DatabaseUtil.connect(); // will need DatabaseUtil class
			//logger.log("Generating ScheduleDAO, checking conn: " + conn.toString() + "\n");
		} catch (Exception e) {
			conn = null;
			//logger.log("Error in generating DAO, exception: " + e +"\n" +e.toString());
		}

	}

	public Schedule getSchedule(String id) throws Exception {

		try {

			Schedule schedule = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE id=?;");

			ps.setString(1, id);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				schedule = createSchedule(resultSet);	// create schedule fcn
			}

			resultSet.close();
			ps.close();

			return schedule;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting schedule: " + e.getMessage());
		}

	}

	public ArrayList<Schedule> getAllSchedules() throws Exception {

		ArrayList<Schedule> schedules = new ArrayList<Schedule>();

		try {

			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Schedules;";
			ResultSet resultSet = statement.executeQuery(query);

			while(resultSet.next()) {
				Schedule s = createSchedule(resultSet);
				schedules.add(s);
			}

			resultSet.close();
			statement.close();
			return schedules;

		} catch (Exception e) {
			throw new Exception("Failed at getting all schedules: " + e.getMessage());
		}

	}

	public boolean addSchedule(Schedule schedule) throws Exception{

		try {
			PreparedStatement ps;
			//			logger.log("made PreparedStatement ps\n");
			ps = conn.prepareStatement("INSERT INTO Schedules (secretCode, id, name, startDate, endDate, startTime, endTime, duration, timestamp) values(?,?,?,?,?,?,?,?,?) ");
			//			logger.log("in addSchedule innitial declaration of conn: " + ps.toString() + "\n");
			ps.setString(1, schedule.getSecretCode());
			ps.setString(2, schedule.getId());
			ps.setString(3, schedule.name);
			//			logger.log("in addSchedule set name: " + ps.toString() + "\n");
			ps.setString(4, rewriteS(schedule.getStartDate().toString()));
			//			logger.log("addSchedule set startDate: " + ps.toString() + "\n");
			ps.setString(5, rewriteS(schedule.getEndDate().toString()));
			//			logger.log("in addSchedule set: endDate" + ps.toString() + "\n");
			ps.setString(6, schedule.getStartTime().toString());
			//			logger.log("in addSchedule setStartTime: " + ps.toString() + "\n");
			ps.setString(7, schedule.getEndTime().toString());
			//			logger.log("in addSchedule setEndTime: " + ps.toString() + "\n");
			ps.setInt(8, schedule.getDuration());
			ps.setString(9, schedule.getTimestamp().toString());

			
			ps.execute();


			TimeSlotDAO tDao = new TimeSlotDAO();
			for (int i = 0; i < schedule.getTimeSlots().size(); i++) {
				tDao.addTimeSlot(schedule.getTimeSlots().get(i));

			}
			
			ps.close();
			
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert schedule: " + e + "\n" + e.getMessage());
		}

	}

	public boolean deleteSchedule(String id, String secretCode) throws Exception {
		try {
			int numAffected = -1;
			// checks if secret code is valid
			if (secretCode.equals(this.getSchedule(id).getSecretCode())) {
				PreparedStatement ps = conn.prepareStatement("DELETE FROM Schedules WHERE id =? AND secretCode = ?");


				ps.setString(1, id);
				ps.setString(2, secretCode);

				numAffected = ps.executeUpdate();
				ps.close();
				if (numAffected == 1) {
					TimeSlotDAO daoT = new TimeSlotDAO(); 
					daoT.deleteAllTimeSlots(id);
				}

			}

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to delete Schedule: " + e.getMessage());
		}


	}

	// might just get schedule id and then update. 
	public boolean updateSchedule(Schedule schedule) throws Exception {

		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE Schedules SET startDate=?, endDate=?, startTime=?, endTime=? WHERE id=?;");
			ps.setString(1, rewriteS(schedule.getStartDate().toString()));
			ps.setString(2, rewriteS(schedule.getEndDate().toString()) );
			ps.setString(3, schedule.getStartTime().toString());
			ps.setString(4, schedule.getEndTime().toString());
			ps.setString(5, schedule.getId());

			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to update Schedule: " + e.getMessage());
		}
	}

	// Adjusting dates
	public boolean adjustDates(String id, String secretCode, String startDate, String endDate) throws Exception {
		try {
			if (this.getSchedule(id).getSecretCode().equals(secretCode)){
				PreparedStatement ps = conn.prepareStatement("UPDATE Schedules SET startDate=?, endDate=? WHERE id=?;");

				LocalDate sameStartDate = this.getSchedule(id).getStartDate();
				LocalDate sameEndDate = this.getSchedule(id).getEndDate();

				int numAffected = -1;

				ps.setString(1, (!startDate.isEmpty()) ? startDate : rewriteS(sameStartDate.toString()));
				ps.setString(2, (!endDate.isEmpty()) ? endDate : rewriteS(sameEndDate.toString()));
				ps.setString(3, id);

				numAffected = ps.executeUpdate();

				TimeSlotDAO tDao = new TimeSlotDAO();
				// add more timeslots
				if(!startDate.equals(rewriteS(sameStartDate.toString()))) {

					String[] splitDate = startDate.split("/");

					for (LocalDate d = LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[0]), 
							Integer.parseInt(splitDate[1])); d.isBefore(sameStartDate); d= d.plusDays(1)) {
						for (LocalTime t = this.getSchedule(id).getStartTime(); t.isBefore(this.getSchedule(id).getEndTime());
								t = t.plusMinutes(this.getSchedule(id).getDuration())) {

							tDao.addTimeSlot(new TimeSlot(LocalDateTime.of(d,t), id));

						}
					}
				}

				if(!endDate.equals(rewriteS(sameEndDate.toString()))) {

					String[] splitDate = endDate.split("/");
					sameEndDate = sameEndDate.plusDays(1);

					for (LocalDate d = sameEndDate; d.isBefore(LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[0]), 
							Integer.parseInt(splitDate[1])).plusDays(1)); d= d.plusDays(1)) {
						for (LocalTime t = this.getSchedule(id).getStartTime(); t.isBefore(this.getSchedule(id).getEndTime());
								t = t.plusMinutes(this.getSchedule(id).getDuration())) {

							tDao.addTimeSlot(new TimeSlot(LocalDateTime.of(d,t), id));

						}
					}
				}
				return (numAffected > 0);

			}

			return false;
		} catch (Exception e) {
			throw new Exception("Unable to adjust start/end dates: " + e.getMessage());
		}
	}

	// Adjusting times
	public boolean adjustTimes(String id, String secretCode, String startTime, String endTime) throws Exception {
		try {
			if (this.getSchedule(id).getSecretCode().equals(secretCode)){
				PreparedStatement ps = conn.prepareStatement("UPDATE Schedules SET startTime=?, endTime=? WHERE id=?;");

				LocalTime sameStartTime = this.getSchedule(id).getStartTime();
				LocalTime sameEndTime = this.getSchedule(id).getEndTime();

				int startHour, startMinute, endHour, endMinute;
				int duration = this.getSchedule(id).getDuration();
				String[] timeStartArray = startTime.split(":");
				startHour = Integer.parseInt(timeStartArray[0]);
				startMinute = Integer.parseInt(timeStartArray[1]);

				String[] timeEndArray = endTime.split(":");
				endHour = Integer.parseInt(timeEndArray[0]);
				endMinute = Integer.parseInt(timeEndArray[1]);

				// check for valid minutes 
				if (startMinute % duration != 0) {
					startMinute += duration - (startMinute %duration);
				}

				if ((endMinute % duration) != 0) {
					endMinute -= (endMinute % duration);
				}
				String newStartTime = convT(startHour, startMinute);
				String newEndTime = convT(endHour,endMinute);
				int numAffected = -1;

				ps.setString(1, (!startTime.isEmpty()) ? newStartTime : sameStartTime.toString());
				ps.setString(2, (!endTime.isEmpty()) ? newEndTime : sameEndTime.toString());
				ps.setString(3, id);

				numAffected = ps.executeUpdate();

				TimeSlotDAO tDao = new TimeSlotDAO();
				// add more timeslots
				if(!newStartTime.equals(sameStartTime.toString())) {

					String[] splitTime = newStartTime.split(":");
					int hour = Integer.parseInt(splitTime[0]);
					int minute = Integer.parseInt(splitTime[1]);

					for (LocalDate d = this.getSchedule(id).getStartDate(); d.isBefore(this.getSchedule(id).getEndDate().plusDays(1)); d= d.plusDays(1)) {
						for (LocalTime t = LocalTime.of(hour, minute); t.isBefore(this.getSchedule(id).getStartTime());
								t = t.plusMinutes(this.getSchedule(id).getDuration())) {

							tDao.addTimeSlot(new TimeSlot(LocalDateTime.of(d,t), id));

						}
					}
				}

				if(!newEndTime.equals(sameEndTime.toString())) {

					String[] splitTime = newEndTime.split(":");
					int hour = Integer.parseInt(splitTime[0]);
					int minute = Integer.parseInt(splitTime[1]);

					for (LocalDate d = this.getSchedule(id).getStartDate(); d.isBefore(this.getSchedule(id).getEndDate().plusDays(1)); d= d.plusDays(1)) {
						for (LocalTime t = this.getSchedule(id).getEndTime(); t.isBefore(LocalTime.of(hour, minute));
								t = t.plusMinutes(this.getSchedule(id).getDuration())) {

							tDao.addTimeSlot(new TimeSlot(LocalDateTime.of(d,t), id));

						}
					}
				}
				return (numAffected > 0);

			}

			return false;
		} catch (Exception e) {
			throw new Exception("Unable to adjust start/end dates: " + e.getMessage());
		}
	}

	// deletes old schedules past a certain number of days
	public boolean deleteOldSchedules(String adminPass, int daysOld) throws Exception {
		try {
			// checks if verification
			if (! adminPass.equals("KnoxMiami1839")) { return false; }

			// gets timestamp for request made
			Calendar calendar = Calendar.getInstance();
			Timestamp currentStamp = new java.sql.Timestamp(calendar.getTime().getTime());
			LocalDateTime stampDateTime = currentStamp.toLocalDateTime();

			// creates timestamp daysOld # of days old to be removed
			LocalDateTime oldStamp = LocalDateTime.of(LocalDate.of(stampDateTime.getYear(),stampDateTime.getMonth(),stampDateTime.getDayOfMonth()).minusDays(daysOld),
					LocalTime.of(stampDateTime.getHour(), stampDateTime.getMinute()));
			ArrayList<Schedule> schedules = new ArrayList<Schedule>();

			// get schedules to delete
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE timestamp < ?;");
			ps.setString(1, Timestamp.valueOf(oldStamp).toString());

			ResultSet resultSet = ps.executeQuery();

			while(resultSet.next()) {
				Schedule s = createSchedule(resultSet);
				schedules.add(s);
			}

			resultSet.close();
			ps.close();

			// deletes all schedules that are oldDays old
			int i = 0;

			while (i < schedules.size()) {
				Schedule s = schedules.get(i);
				deleteSchedule(s.getId(), s.getSecretCode());

			}

			return true;

		} catch (Exception e) {
			throw new Exception("Error in delete old schedules: " + e.getMessage());
		}
	}

	// method to get schedules created in the past n hours (pastHour)
	public ArrayList<Schedule> reportActivity (String adminPass, int pastHour) throws Exception {
		try {
			if (!adminPass.equals("KnoxMiami1839")) { throw new Exception();}
			ArrayList<Schedule> schedules = new ArrayList<Schedule>();			

			// gets timestamp for request made
			Calendar calendar = Calendar.getInstance();
			Timestamp currentStamp = new java.sql.Timestamp(calendar.getTime().getTime());
			LocalDateTime stampDateTime = currentStamp.toLocalDateTime();

			// creates timestamp pastHour # of hours to be searched
			LocalDateTime oldStamp = LocalDateTime.of(LocalDate.of(stampDateTime.getYear(),stampDateTime.getMonth(),stampDateTime.getDayOfMonth()),
					LocalTime.of(stampDateTime.getHour(), stampDateTime.getMinute()).minusHours(pastHour));
		

			// get schedules created in pastHour # of hours 
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE timestamp > ?;");
			ps.setString(1, Timestamp.valueOf(oldStamp).toString());

			ResultSet resultSet = ps.executeQuery();

			while(resultSet.next()) {
				Schedule s = createSchedule(resultSet);
				schedules.add(s);
			}

			resultSet.close();
			ps.close();



			return schedules;
			
			
		} catch (Exception e) {
			throw new Exception("Error in getting recent activity: " + e.getMessage());
		}
	}


	// for database
	private Schedule createSchedule(ResultSet resultSet) throws Exception {

		String secretCode = resultSet.getString("secretCode");
		String id = resultSet.getString("id");
		String name = resultSet.getString("name");
		String startDate = resultSet.getString("startDate");
		String endDate = resultSet.getString("endDate");
		String startTime = resultSet.getString("startTime");
		String endTime = resultSet.getString("endTime");
		int duration = Integer.parseInt(resultSet.getString("duration").substring(0, 2)); // regex minutes
		String timestamp = resultSet.getString("timestamp");
		Schedule s = new Schedule(secretCode, id, name, startDate, endDate, startTime, endTime, duration, timestamp);

		TimeSlotDAO tDao = new TimeSlotDAO();
		for (int i = 0; i < s.getTimeSlots().size(); i++) {
			tDao.addTimeSlot(s.getTimeSlots().get(i));

		}
		return s;
	}


	/* additional methods to reformat strings*/ 
	private String rewriteS(String s) {

		String[] dateArray = s.split("-");
		int year = Integer.parseInt(dateArray[0]);
		int month= Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[2]);



		return (month +"/"+ day+"/" + year );


	}

	private String convT(int hour, int minute) {

		return LocalTime.of(hour,minute).toString();
	}


}
