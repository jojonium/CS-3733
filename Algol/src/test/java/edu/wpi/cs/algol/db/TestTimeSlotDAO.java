package edu.wpi.cs.algol.db;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.model.TimeSlot;

public class TestTimeSlotDAO {

	@Test
	public void testCreation() {
		try {
			TimeSlot slot = new TimeSlot(LocalDateTime.now(), "");
			assertTrue(!slot.toString().isEmpty());
			ScheduleDAO sDao = new ScheduleDAO();
			TimeSlotDAO tDao = new TimeSlotDAO();
			Schedule s = new Schedule("aname","12/10/2018", "12/13/2018", "9:00","10:00", 30);
			sDao.addSchedule(s);
			TimeSlot ts = new TimeSlot("2018-12-11T09:00",s.getId(),"name");
			assertTrue(tDao.getAllTimeSlots(s.getId()).size() > 0);
			tDao.getTimeSlotFromCode(s.getId(), tDao.getAllTimeSlots(s.getId()).get(0).getSecretCode());
			assertTrue(tDao.closeTimeSlot(s.getId(), s.getSecretCode(),"12/11/2018" , "9:00"));
			assertTrue(tDao.openTimeSlot(s.getId(), s.getSecretCode(),"12/11/2018" , "9:00"));
			assertTrue(tDao.addMeeting(ts));
			assertFalse(tDao.cancelMeeting(ts.getSecretCode(),LocalDateTime.of(2018, 12, 11, 9, 0)));
			assertTrue(tDao.updateTimeSlot(ts));
			tDao.getWeeklyTimeSlots(s.getId(), "12-11-2018");
			tDao.getWeeklyTimeSlots(s.getId(), "");
			tDao.closeTimeSlotsAtTime(s.getId(), s.getSecretCode(), "9:30");
			tDao.closeTimeSlotsOnDay(s.getId(), s.getSecretCode(), "12/11/2018");
			tDao.openTimeSlotsAtTime(s.getId(), s.getSecretCode(), "9:30");
			tDao.openTimeSlotsOnDay(s.getId(), s.getSecretCode(), "12/11/2018");
			tDao.showAvailableTimeSlots(s.getId(), "12", "2018", "TUESDAY", "11","9:00");
			tDao.showAvailableTimeSlots(s.getId(), "", "", "TUESDAY", "11","9:00");
			
			tDao.deleteAllTimeSlots(s.getId());
			
			
			
			sDao.deleteSchedule(s.getId(), s.getSecretCode());


		} catch (Exception e) {
			fail("Test Creation error: " + e.getMessage());
		}
	}

}
