package edu.wpi.cs.algol.db;

import java.sql.*;
import java.time.LocalDateTime;

//import edu.wpi.cs.algol.model.Schedule;
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
			ps.setString(2, beginDateTime.toString());
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				timeSlot = createTimeSlot(resultSet);
			}

			resultSet.close();
			ps.close();

			return timeSlot;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Filed in getting timeslot: " + e.getMessage());
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


	/*public boolean addTimeSlot(TimeSlot timeSlot) throws Exception {



	}*/

	// something for getting multiple timeslots for various reasons


	public TimeSlot createTimeSlot(ResultSet resultSet) throws Exception {
		
		String beginDateTime = resultSet.getString("beginDateTime");
		String scheduleID = resultSet.getString("scheduleID");
		
		return new TimeSlot(beginDateTime, scheduleID);
	}


}