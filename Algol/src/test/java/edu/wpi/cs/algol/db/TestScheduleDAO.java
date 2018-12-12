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
			Schedule s1 = new Schedule("name","11/11/2011", "11/12/2011", "9:00","10:00", 30);
			assertTrue(!s.toString().isEmpty());
			sDao.addSchedule(s);
			sDao.addSchedule(s1);
			ArrayList<Schedule> schedules = sDao.getAllSchedules();
			assertTrue(schedules.size() > 0);
			String id = schedules.get(0).getId();
			s = sDao.getSchedule(id);
			sDao.deleteSchedule(s.getId(), s.getSecretCode());
			sDao.deleteSchedule(s1.getId(), s1.getSecretCode());


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
			
			// schedule modification
			boolean b2 = sDao.adjustDates(s.getId(), s.getSecretCode(), "11/10/2011", "11/12/2011");
			boolean b3 = sDao.adjustDates(s.getId(), s.getSecretCode(), "11/10/2011", "11/13/2011");
			boolean b4 = sDao.adjustTimes(s.getId(), s.getSecretCode(), "8:00", "10:00");
			boolean b5 = sDao.adjustTimes(s.getId(), s.getSecretCode(), "8:00", "11:00");
			
			// update
			sDao.updateSchedule(s);
			
			// delete schedule
			boolean b = sDao.deleteSchedule(s.getId(), s.getSecretCode());
			
			assertTrue(b1);
			assertTrue(b2);
			assertTrue(b3);
			assertTrue(b4);
			assertTrue(b5);
			
			assertTrue(b);
			
		} catch (Exception e) {
			fail("Creation test error: " + e.getMessage());
		}
	}

}
