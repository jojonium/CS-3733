package edu.wpi.cs.algol.db;



import java.util.ArrayList;



//import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.model.TimeSlot;


public class Main {
//	public LambdaLogger logger = null;
	public static void main(String args[]) {
		try {
			
			//DatabaseUtil.connect();	//LocalDateTime.of(LocalDate.of(11, 12, 2018),LocalTime.of(2, 0))
			ScheduleDAO sDao = new ScheduleDAO();
			TimeSlotDAO tDao = new TimeSlotDAO();
			//tDao.addTimeSlot(new TimeSlot("2018-12-11T02:00","746zpl"));
			//tDao.updateTimeSlot(new TimeSlot("2018-12-11T02:00","746zpl", "Joe"));
			//System.out.println(LocalDateTime.of(LocalDate.of(2018, 12, 11), LocalTime.of(2, 0)).toString());
			//System.out.println(tDao.getTimeSlot("746zpl", LocalDateTime.of(LocalDate.of(2018, 12, 11), LocalTime.of(2, 0))).toString());
			//Schedule s =new Schedule("letzt","11/11/2018","11/12/2018","9:00","10:00",20);
			//sDao.addSchedule(s);
			Schedule s = sDao.getSchedule("xZz12l");
			ArrayList<TimeSlot> ts = tDao.getAllTimeSlots(s.getId());
			for (int i = 0; i < ts.size() ; i ++) {
				System.out.println(ts.toString());
			}
			System.out.println(s.getDuration());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
