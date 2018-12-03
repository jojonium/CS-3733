package edu.wpi.cs.algol.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import edu.wpi.cs.algol.model.TimeSlot;

public class Main {
	public static void main(String args[]) {
		try {
			//DatabaseUtil.connect();	//LocalDateTime.of(LocalDate.of(11, 12, 2018),LocalTime.of(2, 0))
			TimeSlotDAO tDao = new TimeSlotDAO();
			//tDao.addTimeSlot(new TimeSlot("2018-12-11T02:00","746zpl"));
			//tDao.updateTimeSlot(new TimeSlot("2018-12-11T02:00","746zpl", "Joe"));
			//System.out.println(LocalDateTime.of(LocalDate.of(2018, 12, 11), LocalTime.of(2, 0)).toString());
			System.out.println(tDao.getTimeSlot("746zpl", LocalDateTime.of(LocalDate.of(2018, 12, 11), LocalTime.of(2, 0))).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
