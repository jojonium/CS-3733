package edu.wpi.cs.algol.lamda.showweeklyscheduleorganizer;

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

	ArrayList<TimeSlot> getWeeklyScheduleSlots(String id, String secretCode, String dateStart) throws Exception {
		if (logger != null) { logger.log("in getWeeklyScheduleSlots"); }



		//		ScheduleDAO daoS = new ScheduleDAO();
		TimeSlotDAO daoT = new TimeSlotDAO();

		try {
			
			ScheduleDAO daoS = new ScheduleDAO();
			Schedule s = daoS.getSchedule(id);
			if (s.getSecretCode().equals(secretCode)) {
				return daoT.getWeeklyTimeSlots(id, dateStart);
			}
			else
				throw new Exception("Failue due to incorrect secretCode");
		} catch (Exception e){
			throw new Exception ("Failed to get organizer's weekly schedule: " + e.getMessage());
		}





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
				ArrayList<TimeSlot> ts = getWeeklyScheduleSlots(req.scheduleID, req.secretCode, req.date);
				logger.log("Assign var ts: " + ts.toString() + "\n");


				// successful processing
				ScheduleDAO sDao = new ScheduleDAO();


				Schedule s = sDao.getSchedule(req.scheduleID);
				logger.log("Retrieving schedule with input secretCode: " + s.toString() + "\n");
				TimeSlot firstts = ts.get(0);
				TimeSlot lastts = ts.get(ts.size()-1);
				logger.log("Retrieving last timeslot of weekly schedule: " + lastts.toString() + "\n");
				LocalDate endOfWeek = LocalDate.of(lastts.getBeginDateTime().getYear(), lastts.getBeginDateTime().getMonth(), lastts.getBeginDateTime().getDayOfMonth());
				LocalDate startOfWeek = LocalDate.of(firstts.getBeginDateTime().getYear(),firstts.getBeginDateTime().getMonth() , firstts.getBeginDateTime().getDayOfMonth());
				logger.log("Retrieving endOfWeek date: " + endOfWeek.toString() + "\n");
				
				// enable/disable scrolling through weeks
				boolean hasPreviousWeek = startOfWeek.plusDays(-3).isAfter(s.getStartDate().plusDays(-1));
				boolean hasNextWeek = endOfWeek.plusDays(3).isBefore(s.getEndDate().plusDays(1)); 
				
				resp = new ShowWeeklyScheduleOrganizerResponse(s.getName(),startOfWeek,endOfWeek,
						LocalTime.of(firstts.getBeginDateTime().getHour(), firstts.getBeginDateTime().getMinute()),
						LocalTime.of(lastts.getBeginDateTime().getHour(), lastts.getBeginDateTime().getMinute()),s.getDuration(),
						ts, String.valueOf(hasPreviousWeek), String.valueOf(hasNextWeek));
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
