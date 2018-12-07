package edu.wpi.cs.algol.db;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

	public boolean addSchedule(Schedule schedule) throws Exception{

		try {
			PreparedStatement ps;
			//			logger.log("made PreparedStatement ps\n");
			ps = conn.prepareStatement("INSERT INTO Schedules (secretCode, id, name, startDate, endDate, startTime, endTime, duration) values(?,?,?,?,?,?,?,?) ");
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

			ps.execute();


			TimeSlotDAO tDao = new TimeSlotDAO();
			for (int i = 0; i < schedule.getTimeSlots().size(); i++) {
				tDao.addTimeSlot(schedule.getTimeSlots().get(i));

			}

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
	/*public boolean adjustTimes(String id, String secretCode, String startTime, String endTime) throws Exception {
		try {
			if (this.getSchedule(id).getSecretCode().equals(secretCode)){
				PreparedStatement ps = conn.prepareStatement("UPDATE Schedules SET startTime=?, endTime=? WHERE id=?;");

				LocalTime sameStartDate = this.getSchedule(id).getStartTime();
				LocalTime sameEndDate = this.getSchedule(id).getEndTime();

				int numAffected = -1;

				ps.setString(1, (!startTime.isEmpty()) ? startTime : rewriteS(sameStartDate.toString()));
				ps.setString(2, (!endTime.isEmpty()) ? endTime : rewriteS(sameEndDate.toString()));
				ps.setString(3, id);

				numAffected = ps.executeUpdate();

				TimeSlotDAO tDao = new TimeSlotDAO();
				// add more timeslots
				if(!startTime.equals(rewriteS(sameStartDate.toString()))) {
					
					String[] splitDate = startTime.split(":");

					for (LocalDate d = LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[0]), 
							Integer.parseInt(splitDate[1])); d.isBefore(sameStartDate); d= d.plusDays(1)) {
						for (LocalTime t = this.getSchedule(id).getStartTime(); t.isBefore(this.getSchedule(id).getEndTime());
								t = t.plusMinutes(this.getSchedule(id).getDuration())) {
						
							tDao.addTimeSlot(new TimeSlot(LocalDateTime.of(d,t), id));
						
						}
					}
				}

				if(!endTime.equals(rewriteS(sameEndDate.toString()))) {
					
					String[] splitDate = endTime.split(":");
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
	}*/


	private Schedule createSchedule(ResultSet resultSet) throws Exception {

		String secretCode = resultSet.getString("secretCode");
		String id = resultSet.getString("id");
		String name = resultSet.getString("name");
		String startDate = resultSet.getString("startDate");
		String endDate = resultSet.getString("endDate");
		String startTime = resultSet.getString("startTime");
		String endTime = resultSet.getString("endTime");
		int duration = Integer.parseInt(resultSet.getString("duration").substring(0, 2)); // regex minutes

		Schedule s = new Schedule(secretCode, id, name, startDate, endDate, startTime, endTime, duration);

		TimeSlotDAO tDao = new TimeSlotDAO();
		for (int i = 0; i < s.getTimeSlots().size(); i++) {
			tDao.addTimeSlot(s.getTimeSlots().get(i));

		}
		return s;
	}

	private String rewriteS(String s) {

		String[] dateArray = s.split("-");
		int year = Integer.parseInt(dateArray[0]);
		int month= Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[2]);



		return (month +"/"+ day+"/" + year );


	}



}
