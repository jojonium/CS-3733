package edu.wpi.cs.algol.db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

	//2018-12-11T02:00
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

	public TimeSlot createTimeSlot(ResultSet resultSet) throws Exception {
		
		String beginDateTime = resultSet.getString("beginDateTime");
		String scheduleID = resultSet.getString("scheduleID");
		String secretCode = resultSet.getString("secretCode");
		String requester = resultSet.getString("requester");
		String isOpen = resultSet.getString("isOpen");
		return new TimeSlot(secretCode, beginDateTime, isOpen, requester, scheduleID);
	}


}