package edu.wpi.cs.algol.lambda.cancelmeeting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

public class CancelMeetingHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;
	public TimeSlot t;
	public Schedule s;

	boolean cancelMeeting(String date, String time, String sid) throws Exception {
		TimeSlotDAO daoT = new TimeSlotDAO();

		// parse into date and time
		String[] tsDate = date.split("/");
		int month = Integer.parseInt(tsDate[0]);
		int day = Integer.parseInt(tsDate[1]);
		int year = Integer.parseInt(tsDate[2]);
		String[] tsTime = time.split(":");
		int hour = Integer.parseInt(tsTime[0]);
		int minute = Integer.parseInt(tsTime[1]);

		LocalDateTime ldt = LocalDateTime.of(LocalDate.of(year, month, day), (LocalTime.of(hour, minute)));

		// compareDatetime with list of timeslots to add meeting to correct timeslot
		t = daoT.getTimeSlot(sid, ldt);

		// return true if meeting is created
		boolean cancelled = false;
		if (t.isOpen() == false) {
			t.setOpen(true);
			cancelled = true;
			t.setRequester(null);
			t.setSecretCode(null);
			daoT.updateTimeSlot(t);
		}
		return cancelled;
	}

	boolean checkCode(String passcode, String date, String time, String sid) throws Exception {

		ScheduleDAO daoS = new ScheduleDAO();
		TimeSlotDAO daoT = new TimeSlotDAO();
		boolean same = false;

		// parse into date and time
		String[] tsDate = date.split("/");
		int month = Integer.parseInt(tsDate[0]);
		int day = Integer.parseInt(tsDate[1]);
		int year = Integer.parseInt(tsDate[2]);
		String[] tsTime = time.split(":");
		int hour = Integer.parseInt(tsTime[0]);
		int minute = Integer.parseInt(tsTime[1]);

		LocalDateTime ldt = LocalDateTime.of(LocalDate.of(year, month, day), (LocalTime.of(hour, minute)));

		// compareDatetime with list of timeslots to add meeting to correct timeslot
		t = daoT.getTimeSlot(sid, ldt);
		s = daoS.getSchedule(sid);

		logger.log("Entered code:" + passcode);
		logger.log("\n Timeslot Code:" + t.getSecretCode());
		logger.log("\n Schedule Code:" + s.getSecretCode());

		if (t.getSecretCode() == passcode) {
			same = true;
		} else if (s.getSecretCode() == passcode) {
			same = true;
		}

		return same;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to create constant");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type", "application/json"); // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
		headerJson.put("Access-Control-Allow-Origin", "*");

		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		CancelMeetingResponse response = null;

		// extract body from incoming HTTP POST request. If any error, then return 422
		// error
		String body;
		boolean processed = false;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());

			String method = (String) event.get("httpMethod");
			if (method != null && method.equalsIgnoreCase("OPTIONS")) {
				logger.log("Options request");
				response = new CancelMeetingResponse("name", 200); // OPTIONS needs a 200 response
				responseJson.put("body", new Gson().toJson(response));
				processed = true;
				body = null;
			} else {
				body = (String) event.get("body");
				if (body == null) {
					body = event.toJSONString(); // this is only here to make testing easier
				}
			}
		} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new CancelMeetingResponse("Bad Request:" + pe.getMessage(), 422); // unable to process input
			responseJson.put("body", new Gson().toJson(response));
			processed = true;
			body = null;
		}

		if (!processed) {
			CancelMeetingRequest req = new Gson().fromJson(body, CancelMeetingRequest.class);
			logger.log(req.toString());

			CancelMeetingResponse resp;
			try {

				if (checkCode(req.secretCode, req.date, req.time, req.scheduleID) == true) {
					if (cancelMeeting(req.date, req.time, req.scheduleID) == true) {
						resp = new CancelMeetingResponse("You have succesfully cancelled a meeting.");
					} else {
						resp = new CancelMeetingResponse("Meeting cancel failure. No meeting scheduled.", 409);
					}
				} else {
					resp = new CancelMeetingResponse("Meeting cancel failure. Incorrect passcode.", 400);
				}

			}

			catch (Exception e) {
				resp = new CancelMeetingResponse("Unable to cancel meeting" + " because of (" + e.getMessage() + ")",
						400);
			}

			// compute proper response
			responseJson.put("body", new Gson().toJson(resp));
		}

		logger.log("end result:" + responseJson.toJSONString());
		logger.log(responseJson.toJSONString());
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseJson.toJSONString());
		writer.close();
	}

}
