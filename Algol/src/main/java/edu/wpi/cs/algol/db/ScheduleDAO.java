package edu.wpi.cs.algol.db;


import java.sql.*;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.algol.model.Schedule;

public class ScheduleDAO {
	
	java.sql.Connection conn;
	
	LambdaLogger logger = null;

	public ScheduleDAO() {
	//	logger = l;
	//	logger.log("ScheduleDAO constructor...\n");
		try {
			conn = DatabaseUtil.connect(); // will need DatabaseUtil class
	//		logger.log("Generating ScheduleDAO, checking conn: " + conn.toString() + "\n");
		} catch (Exception e) {
			conn = null;
	//		logger.log("Error in generating DAO, exception: " + e +"\n" +e.toString());
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
		//	logger.log("made PreparedStatement ps\n");
			ps = conn.prepareStatement("INSERT INTO Schedules (secretCode, id, name, startDate, endDate, startTime, endTime, duration) values(?,?,?,?,?,?,?,?) ");
		//	logger.log("in addSchedule innitial declaration of conn: " + ps.toString() + "\n");
			ps.setString(1, schedule.getSecretCode());
			ps.setString(2, schedule.getId());
			ps.setString(3, schedule.name);
			//logger.log("in addSchedule set name: " + ps.toString() + "\n");
			ps.setString(4, rewriteS(schedule.getStartDate().toString()));
		//	logger.log("addSchedule set startDate: " + ps.toString() + "\n");
			ps.setString(5, rewriteS(schedule.getEndDate().toString()));
			//logger.log("in addSchedule set: endDate" + ps.toString() + "\n");
			ps.setString(6, schedule.getStartTime().toString());
		//	logger.log("in addSchedule setStartTime: " + ps.toString() + "\n");
			ps.setString(7, schedule.getEndTime().toString());
			ps.setInt(8, schedule.duration); 
		//	logger.log("in addSchedule setEndTime: " + ps.toString() + "\n");
			ps.execute();
			return true;

		} catch (Exception e) {
            throw new Exception("Failed to insert schedule: " + e + "\n" + e.getMessage());
        }

	}
	
	public boolean deleteSchedule(Schedule schedule) throws Exception {
		
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Schedules WHERE id =?");
			ps.setString(1, schedule.getId());
			int numAffected = ps.executeUpdate();
			ps.close();
			
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
	private Schedule createSchedule(ResultSet resultSet) throws Exception {
		
		String secretCode = resultSet.getString("secretCode");
		String id = resultSet.getString("id");
		String name = resultSet.getString("name");
		String startDate = resultSet.getString("startDate");
		String endDate = resultSet.getString("endDate");
		String startTime = resultSet.getString("startTime");
		String endTime = resultSet.getString("endTime");
		int duration = Integer.parseInt(resultSet.getString("duration").substring(0, 2)); // regex minutes

		return new Schedule(secretCode, id, name, startDate, endDate, startTime, endTime, duration);

	}
	
	private String rewriteS(String s) {

		String[] dateArray = s.split("-");
		int year = Integer.parseInt(dateArray[0]);
		int month= Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[2]);


		
		return (month +"/"+ day+"/" + year );
		
		
	}
	


}
