package edu.wpi.cs.algol.lamda.showweeklyscheduleadmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
//import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.Month;
import java.util.ArrayList;
//import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.db.TimeSlotDAO;
import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.model.TimeSlot;

public class ShowWeeklyScheduleOrganizerHandler implements RequestStreamHandler {
	private LambdaLogger logger = null;

	ArrayList<TimeSlot> getWeeklyScheduleSlots(String id, String dateStart) throws Exception {
		if (logger != null) { logger.log("in getWeeklyScheduleSlots"); }
		

		// variable setup
//		ScheduleDAO daoS = new ScheduleDAO();
		TimeSlotDAO daoT = new TimeSlotDAO();
		
		return daoT.getWeeklyTimeSlots(id, dateStart);
		//ArrayList<TimeSlot> allts = new ArrayList<TimeSlot>();
		/*ArrayList<TimeSlot> weekts = new ArrayList<TimeSlot>();
		//allts = daoT.getAllTimeSlots(id);
		Schedule s = daoS.getSchedule(id);
		TimeSlot startts;
		if (!dateStart.isEmpty()) {			// a valid date has been included
			String[] date = dateStart.split("-");
			//String[] time = dateTime.split(":");

			int month = Integer.parseInt(date[0]);	// parsing might not work correctly
			int day = Integer.parseInt(date[1]);
			int year = Integer.parseInt(date[2]);
			LocalDateTime ldt;

			ldt = LocalDateTime.of(LocalDate.of(year, month, day), s.getStartTime());
			// check which date begins on 
			startts = daoT.getTimeSlot(id, ldt);

			if(startts.getBeginDateTime().getDayOfWeek() == DayOfWeek.MONDAY) {

				for (int i = 0; i < 4; i ++) {
					for(LocalTime time = (s.getStartTime().getMinute()% s.getDuration() == 0) ? s.getStartTime() : s.getStartTime().plusMinutes(s.getDuration() - s.getStartTime().getMinute()%s.getDuration()); time.isBefore(s.getEndTime()); time = time.plusMinutes(s.getDuration())) {
						weekts.add(daoT.getTimeSlot(id, LocalDateTime.of(LocalDate.of(year, month, day).plusDays(i), time)));
					}
				}

			}

			else {
				int newDay = day;
				while (startts.getBeginDateTime().getDayOfWeek() != DayOfWeek.MONDAY) {
					startts = daoT.getTimeSlot(id, LocalDateTime.of(LocalDate.of(year, month, newDay--), s.getStartTime()));
					if (startts == null) {
						startts = daoT.getTimeSlot(id, LocalDateTime.of(LocalDate.of(year, month, newDay++), s.getStartTime()));
						break;
					}
				}
				for (int i = 0; i < 4; i ++) {
					for(LocalTime time = (s.getStartTime().getMinute()% s.getDuration() == 0) ? s.getStartTime() : s.getStartTime().plusMinutes(s.getDuration() - s.getStartTime().getMinute()%s.getDuration()); time.isBefore(s.getEndTime()); time = time.plusMinutes(s.getDuration())) {
						weekts.add(daoT.getTimeSlot(id, LocalDateTime.of(LocalDate.of(year, month, day).plusDays(i), time)));
					}
				}
			}


			return weekts;


		}

		else {
			
			startts = daoT.getTimeSlot(id, LocalDateTime.of(s.getStartDate(),s.getStartTime()));
			
			int day = startts.getBeginDateTime().getDayOfMonth();
			int month = startts.getBeginDateTime().getMonthValue();
			int year = startts.getBeginDateTime().getYear();

			for (int i = 0; startts.getBeginDateTime().getDayOfWeek() != DayOfWeek.SATURDAY; i ++) {
				for(LocalTime time = (s.getStartTime().getMinute()% s.getDuration() == 0) ? s.getStartTime() : s.getStartTime().plusMinutes(s.getDuration() - s.getStartTime().getMinute()%s.getDuration()); time.isBefore(s.getEndTime()); time = time.plusMinutes(s.getDuration())) {
					startts = daoT.getTimeSlot(id, LocalDateTime.of(LocalDate.of(year, month, day).plusDays(i), time));
					
					weekts.add(startts);

				}
			}


			return weekts;

		}*/

		//		int hour = Integer.parseInt(time[0]);
		//		int min = Integer.parseInt(time[1]);

		/*LocalDateTime ldt = LocalDateTime.of(LocalDate.of(year, month, day), 
				LocalTime.of(hour, min));
		TimeSlot timeslot = daoT.getTimeSlot(id, ldt);
		TimeSlot beginWeekDay = daoT.getTimeSlot(id, timeslot.getBeginDateTime());;
		for (int i = 0; i < 7; i++) {
			if (timeslot.getBeginDateTime().minusDays(i).getDayOfWeek() == DayOfWeek.SUNDAY) {
				beginWeekDay = daoT.getTimeSlot(id, timeslot.getBeginDateTime().minusDays(i));
			}
		}

		Iterator<TimeSlot> iterator = allts.iterator();
		while (iterator.hasNext()) {
			for (int i = 0; i < 6; i++) {
				if (iterator.next().getBeginDateTime() == beginWeekDay.getBeginDateTime().plusDays(i)) {
					weekts.add(iterator.next());
				}
			}
		}*/


		/* weekts.add(all timeslots within a week after beginWeek timeslot) */

		/*
		 * int durationInt = Integer.parseInt(duration.substring(0, 2));
		 * logger.log("Parsed duration to get integer: "+ durationInt+"\n"); //creating
		 * the schedule s = new Schedule(name, dateStart, dateEnd, timeStart, timeEnd,
		 * durationInt);
		 */


	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to show a weekly schedule in normal mode... \n");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type", "application/json"); // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
		headerJson.put("Access-Control-Allow-Origin", "*");

		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		ShowWeeklyScheduleOrganizerResponse response = null;

		// extract body from incoming HTTP GET request. If any error, then return 422
		// error
		String body;
		boolean processed = false;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString() + "\n");

			String method = (String) event.get("httpMethod");
			if (method != null && method.equalsIgnoreCase("OPTIONS")) {
				logger.log("Options request\n");
				response = new ShowWeeklyScheduleOrganizerResponse("name", 201); // OPTIONS needs a 200 response
				responseJson.put("body", new Gson().toJson(response));
				logger.log(responseJson.toJSONString() + "\n");
				processed = true;
				body = null;
			} else {
				body = (String) event.get("body");
				if (body == null) {
					body = event.toJSONString(); // this is only here to make testing easier

				}
				logger.log("JSON body: " + body.toString() + "\n");
			}
		} catch (ParseException pe) {
			logger.log(pe.toString() + "\n");
			response = new ShowWeeklyScheduleOrganizerResponse("Bad Request:" + pe.getMessage(), 422); // unable to process input
			responseJson.put("body", new Gson().toJson(response));
			logger.log(responseJson.toJSONString() + "\n");
			processed = true;
			body = null;
		}

		if (!processed) {
			ShowWeeklyScheduleOrganizerRequest req = new Gson().fromJson(body, ShowWeeklyScheduleOrganizerRequest.class);
			logger.log("New Req for !processed: " + req.toString() + "\n");

			ShowWeeklyScheduleOrganizerResponse resp;
			try {
				ArrayList<TimeSlot> ts = getWeeklyScheduleSlots(req.scheduleID, req.date);
				logger.log("Assign var ts: " + ts.toString() + "\n");
				
				/*
				 * ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 9, 0,
				 * 0), true, null, req.scheduleID)); ts.add(new TimeSlot(null,
				 * LocalDateTime.of(2018, Month.NOVEMBER, 26, 9, 30, 0), true, null,
				 * req.scheduleID)); ts.add(new TimeSlot(null, LocalDateTime.of(2018,
				 * Month.NOVEMBER, 26, 10, 0, 0), false, null, req.scheduleID)); ts.add(new
				 * TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 10, 30, 0), true,
				 * null, req.scheduleID)); ts.add(new TimeSlot(null, LocalDateTime.of(2018,
				 * Month.NOVEMBER, 26, 11, 0, 0), true, null, req.scheduleID)); ts.add(new
				 * TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 11, 30, 0), false,
				 * null, req.scheduleID));
				 * 
				 * ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 9, 0,
				 * 0), false, null, req.scheduleID)); ts.add(new TimeSlot(null,
				 * LocalDateTime.of(2018, Month.NOVEMBER, 27, 9, 30, 0), false, null,
				 * req.scheduleID)); ts.add(new TimeSlot(null, LocalDateTime.of(2018,
				 * Month.NOVEMBER, 27, 10, 0, 0), false, null, req.scheduleID)); ts.add(new
				 * TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 10, 30, 0), true,
				 * null, req.scheduleID)); ts.add(new TimeSlot(null, LocalDateTime.of(2018,
				 * Month.NOVEMBER, 27, 11, 0, 0), true, null, req.scheduleID)); ts.add(new
				 * TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 11, 30, 0), false,
				 * null, req.scheduleID));
				 */
				// successful processing
				ScheduleDAO sDao = new ScheduleDAO();
				
				
				Schedule s = sDao.getSchedule(req.scheduleID);
				logger.log("Retrieving schedule with input ID: " + s.toString() + "\n");
				TimeSlot firstts = ts.get(0);
				TimeSlot lastts = ts.get(ts.size()-1);
				logger.log("Retrieving last timeslot of weekly schedule: " + lastts.toString() + "\n");
				LocalDate endOfWeek = LocalDate.of(lastts.getBeginDateTime().getYear(), lastts.getBeginDateTime().getMonth(), lastts.getBeginDateTime().getDayOfMonth());
				LocalDate startOfWeek = LocalDate.of(firstts.getBeginDateTime().getYear(),firstts.getBeginDateTime().getMonth() , firstts.getBeginDateTime().getDayOfMonth());
				logger.log("Retrieving endOfWeek date: " + endOfWeek.toString() + "\n");
				resp = new ShowWeeklyScheduleOrganizerResponse(s.getName(),startOfWeek,endOfWeek,LocalTime.of(firstts.getBeginDateTime().getHour(), firstts.getBeginDateTime().getMinute()),LocalTime.of(lastts.getBeginDateTime().getHour(), lastts.getBeginDateTime().getMinute()),s.getDuration(), ts);
				logger.log("ShowWeeklySchedule response: " + resp.toString() + "\n");
			} catch (Exception e) {
				resp = new ShowWeeklyScheduleOrganizerResponse(
						"Unable to show schedule " + req.scheduleID + " (" + e + ")", 400);
				logger.log("Unable to show schedule:  " + req.scheduleID + " 400. Error Message: " + e + "\n");
			}

			// compute proper response
			responseJson.put("body", new Gson().toJson(resp));

			//logger.log("\n" + responseJson.toJSONString() + "\n");
		}

		logger.log("end result:" + responseJson.toJSONString() + "\n");
		//logger.log(responseJson.toJSONString() + "\n");
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseJson.toJSONString());
		writer.close();
	}
}
