package edu.wpi.cs.algol.lambda.openalltimeslotstime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.TimeSlotDAO;

public class OpenAllTimeSlotTimeHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;

	public boolean OpenTimeSlots(String sid, String scd, String t) throws Exception {
		if (logger != null) {
			logger.log("in OpenTimeSlots");
		}

		TimeSlotDAO daoTS = new TimeSlotDAO();

		try {
			return daoTS.openTimeSlotsAtTime(sid, scd, t);

		} catch (Exception e) {
			throw e;
		}

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

		OpenAllTimeSlotTimeResponse response = null;

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
				response = new OpenAllTimeSlotTimeResponse("name", 200); // OPTIONS needs a 200 response
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
			response = new OpenAllTimeSlotTimeResponse("Bad Request:" + pe.getMessage(), 422); // unable to process input
			responseJson.put("body", new Gson().toJson(response));
			processed = true;
			body = null;
		}

		if (!processed) {
			OpenAllTimeSlotTimeRequest req = new Gson().fromJson(body, OpenAllTimeSlotTimeRequest.class);

			logger.log(req.toString());

			OpenAllTimeSlotTimeResponse resp;
			try {
				OpenTimeSlots(req.scheduleID, req.secretCode, req.time);
				logger.log("OpenAllTimeSlotDay worked");
				resp = new OpenAllTimeSlotTimeResponse(req.scheduleID);
				logger.log("TimeSlots successfully opened");

			} catch (Exception e) {
				resp = new OpenAllTimeSlotTimeResponse("Unable to open timeslots at time " + req.time + " of schedule "
						+ req.scheduleID + " because of (" + e.getMessage() + ")", 404);
			}

			// compute proper response
			responseJson.put("body", new Gson().toJson(resp));
		}

		logger.log("end result:" + responseJson.toJSONString() + "\n");
		logger.log(responseJson.toJSONString());
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(responseJson.toJSONString());
		writer.close();
	}

}
