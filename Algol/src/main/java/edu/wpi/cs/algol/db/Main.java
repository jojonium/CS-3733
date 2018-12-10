package edu.wpi.cs.algol.db;



//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.sql.Timestamp;

//import com.amazonaws.services.lambda.runtime.LambdaLogger;

//import edu.wpi.cs.algol.model.Schedule;
//import edu.wpi.cs.algol.model.TimeSlot;


public class Main {
	//	public LambdaLogger logger = null;
	public static void main(String args[]) {
		try {

			//DatabaseUtil.connect();	//LocalDateTime.of(LocalDate.of(11, 12, 2018),LocalTime.of(2, 0))
//			ScheduleDAO sDao = new ScheduleDAO();
//			TimeSlotDAO tDao = new TimeSlotDAO();
			//tDao.addTimeSlot(new TimeSlot("2018-12-11T02:00","746zpl"));
			//tDao.updateTimeSlot(new TimeSlot("2018-12-11T02:00","746zpl", "Joe"));
			//System.out.println(LocalDateTime.of(LocalDate.of(2018, 12, 11), LocalTime.of(2, 0)).toString());
			//System.out.println(tDao.getTimeSlot("746zpl", LocalDateTime.of(LocalDate.of(2018, 12, 11), LocalTime.of(2, 0))).toString());
			//Schedule s =new Schedule("letzt","11/11/2018","11/12/2018","9:00","10:00",20);
			//sDao.addSchedule(s);
			//Schedule s = new Schedule("new what","12/21/2018","12/28/2018","9:00","10:00",30);;
			//sDao.addSchedule(s);
			//ArrayList<TimeSlot> ts = tDao.getAllTimeSlots(s.getId());
			//boolean stat = tDao.openTimeSlotsOnDay("11eAte", "T83ifQ", "12/10/2018");
			//tDao.updateTimeSlot(new TimeSlot("2018-12-11T09:00","83hRny" ,"JC"));
			//tDao.openTimeSlotsAtTime("zT8V9q", "tbbYvI", "09:30");
			//System.out.println(stat);//Fy4f0r	0c8vn1
			//sDao.deleteSchedule("488cmb", "P89fBN");
			//tDao.closeTimeSlotsAtTime("83hRny", "3043xB", "9:00");
			/*ArrayList<TimeSlot> ts = tDao.getWeeklyTimeSlots("3omb5e","12-14-2018");
			System.out.println(ts.size());
			for(int i = 0 ; i < ts.size(); i++ ) {
				System.out.println(ts.get(i).toString());
			} */ //Ci79fp	Testing	12/3/2018	12/14/2018
			//sDao.adjustDates("Ci79fp", "Osk9ly", "11/29/2018", "12/22/2018");
			//			ArrayList<TimeSlot> ts = tDao.getWeeklyTimeSlots("47cc1u","12-17-2018");
			//			TimeSlot firstts = ts.get(0);
			//			TimeSlot lastts = ts.get(ts.size()-1);
			//			LocalDate endOfWeek = LocalDate.of(lastts.getBeginDateTime().getYear(), lastts.getBeginDateTime().getMonth(), lastts.getBeginDateTime().getDayOfMonth());
			//			LocalDate startOfWeek = LocalDate.of(firstts.getBeginDateTime().getYear(),firstts.getBeginDateTime().getMonth() , firstts.getBeginDateTime().getDayOfMonth());

			//			LocalDate startOfWeek = LocalDate.of(2018, 12, 3);
			//			LocalDate endOfWeek = LocalDate.of(2018, 12,7 );

			//			boolean hasPreviousWeek = startOfWeek.plusDays(-3).isAfter(s.getStartDate().plusDays(-1));
			//			
			//			boolean hasNextWeek = endOfWeek.plusDays(3).isBefore(s.getEndDate().plusDays(1)); 
			//			
			//			System.out.println(hasPreviousWeek + " " + hasNextWeek);
			/*ArrayList<Schedule> schedules = sDao.getAllSchedules();
			for (int i = 0; i < schedules.size(); i++) {
				System.out.println(schedules.get(i).toString());
			}*/
			//Schedule s = new Schedule("name", "11/11/2011", "11/11", "", "", 20);
//
//			Calendar calendar = Calendar.getInstance();
//			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			// old date
			//LocalDateTime adate = LocalDateTime.of(2018, 12, 8, 21, 7);
			// new time
			/*String s = currentTimestamp.toString();
			System.out.println(s);
			System.out.println(currentTimestamp.toString().compareTo(Timestamp.valueOf(adate).toString()));
			System.out.println(Timestamp.valueOf(adate).toString().compareTo(currentTimestamp.toString()));
			System.out.println(Timestamp.valueOf(adate).toString()); */
			//System.out.println(Timestamp.valueOf(currentTimestamp.toString()).toString());
			//System.out.println("09:00".compareTo("10:00"));
			//sDao.addSchedule(new Schedule("asdfgh","asdfgh","name", "11/11/2011", "11/12/2011", "9:00", "10:00",20, currentTimestamp.toString()));
			//sDao.addSchedule(new Schedule("name","11/11/2011", "11/12/2011", "9:00","10:00", 30));
			//System.out.println("".isEmpty());
			//System.out.println(tDao.showAvailableTimeslots("5veypd", "","","","").size());
			//sDao.adjustDates("YJwNE8", "df28h4", "12/9/2018", "");
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
