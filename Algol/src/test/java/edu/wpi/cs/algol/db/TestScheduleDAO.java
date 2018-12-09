package edu.wpi.cs.algol.db;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.wpi.cs.algol.model.Schedule;

public class TestScheduleDAO {

	@Test
	public void testGet() {
		
		try {
			ScheduleDAO sDao = new ScheduleDAO();
			Schedule s = new Schedule("name","11/11/2011", "11/12/2011", "9:00","10:00", 30);
			ArrayList<Schedule> schedules = sDao.getAllSchedules();
			assertTrue(schedules.size() > 0);
			String id = schedules.get(0).getId();
			s = sDao.getSchedule(id);
			assertTrue(s != null);

		} catch (Exception e) {
			fail("Creation test error: " + e.getMessage());
		}
	}


	@Test
	public void testCreation() {
		try {
			ScheduleDAO sDao = new ScheduleDAO();
			Schedule s = new Schedule("name","11/11/2011", "11/12/2011", "9:00","10:00", 30);
			
			// add schedule
			boolean b1 = sDao.addSchedule(s);
			
			// delete schedule
			boolean b2 = sDao.deleteSchedule(s.getId(), s.getSecretCode());
			
			assertTrue(b1);
			assertTrue(b2);
			
		} catch (Exception e) {
			fail("Creation test error: " + e.getMessage());
		}
	}


	@Test
	public void testModification() {
		try {

		} catch (Exception e) {
			fail("Not yet implemented: ");
		}
	}

}
