package edu.wpi.cs.algol.db;

import edu.wpi.cs.algol.model.Schedule;

public class Main {
	public ScheduleDAO sDao;
	public static void main(String args[]) {
		try {
			ScheduleDAO sDao = new ScheduleDAO();
		//	sDao.addSchedule(new Schedule("hein","12/11/2018","12/16/2018","9:00","17:00",20));
			Schedule s = sDao.getSchedule("FS9RxX");
		//	System.out.println(s.toString());
			
			System.out.println(sDao.deleteSchedule(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
