package edu.wpi.cs.algol.db;


import java.sql.*;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.algol.model.Schedule;

public class ScheduleDAO {
	
	java.sql.Connection conn;
	
	LambdaLogger logger = null;

	public ScheduleDAO() {

		try {
			conn = DatabaseUtil.connect(); // will need DatabaseUtil class
		} catch (Exception e) {
			conn = null;
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
			
			ps = conn.prepareStatement("INSERT INTO Schedules (name, startDate, endDate, startTime, endTime, duration) values(?,?,?,?,?,?) ");
			logger.log("in addSchedule innitial declaration of conn: " + ps.toString() + "\n");

			ps.setString(1, schedule.name);
			logger.log("in addSchedule set name: " + ps.toString() + "\n");
			ps.setString(2, schedule.getStartDate().toString());
			logger.log("addSchedule set startDate: " + ps.toString() + "\n");
			ps.setString(3, schedule.getEndDate().toString());
			logger.log("in addSchedule set: endDate" + ps.toString() + "\n");
			ps.setString(4, schedule.getStartTime().toString());
			logger.log("in addSchedule setStartTime: " + ps.toString() + "\n");
			ps.setString(5, schedule.getEndTime().toString());
			ps.setInt(6, schedule.duration); 
			logger.log("in addSchedule setEndTime: " + ps.toString() + "\n");
			ps.execute();
			return true;

		} catch (Exception e) {
            throw new Exception("Failed to insert schedule: " + e + "\n" + e.getMessage());
        }

	}

	//private static 

	private Schedule createSchedule(ResultSet resultSet) throws Exception {
		// update field types of visibility?
		String name = resultSet.getString("name");
		String startDate = resultSet.getString("startDate");
		String endDate = resultSet.getString("endDate");
		String startTime = resultSet.getString("startTime");
		String endTime = resultSet.getString("endTime");
		int duration = Integer.parseInt(resultSet.getString("duration").substring(0, 2)); // regex minutes

		return new Schedule(name, startDate, endDate, startTime, endTime, duration);

	}


}
