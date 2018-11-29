package edu.wpi.cs.algol.db;


import java.sql.*;

import edu.wpi.cs.algol.model.Schedule;

public class ScheduleDAO {
	
	java.sql.Connection conn;

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
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE id=?;");
			ps.setString(1, schedule.getId());
			ResultSet resultSet = ps.executeQuery();

			// already present?
			while (resultSet.next()) {

				//Schedule s = createSchedule(resultSet);
				resultSet.close();
				return false;
			}
				
			ps = conn.prepareStatement("INSERT INTO Schedules (name, startDate, endDate, startTime, endTime, duration) values(?,?,?,?,?,?) ");
			// toString methods not exactly functional
			ps.setString(1, schedule.name);
			ps.setString(2, schedule.getStartDate().toString());
			ps.setString(3, schedule.getEndDate().toString());
			ps.setString(4, schedule.getStartTime().toString());
			ps.setString(5, schedule.getEndTime().toString());
			ps.setInt(6, schedule.duration); // regex minutes
			ps.execute();
			return true;

		} catch (Exception e) {
            throw new Exception("Failed to insert schedule: " + e.getMessage());
        }

	}



	private Schedule createSchedule(ResultSet resultSet) throws Exception {
		// update field types of visibility?
		String name = resultSet.getString("name");
		String startDate = resultSet.getString("startDate");
		String endDate = resultSet.getString("endDate");
		String startTime = resultSet.getString("startTime");
		String endTime = resultSet.getString("endTime");
		int duration = resultSet.getInt("duration"); // regex minutes

		return new Schedule(name, startDate, endDate, startTime, endTime, duration);

	}


}
