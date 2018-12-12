package edu.wpi.cs.algol.lambda.deletescheduleold;

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

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.lambda.deletescheduleold.DeleteScheduleOldRequest;
import edu.wpi.cs.algol.lambda.deletescheduleold.DeleteScheduleOldResponse;

public class DeleteScheduleOldHandler implements RequestStreamHandler {
	// test
	public LambdaLogger logger = null;

	int deleteOldSchedules(String adminPass, int daysOld) throws Exception {
		// logger test
		if (logger != null) {
			logger.log("in deleteScheduleOld");
		}

		// variable setup
		ScheduleDAO daoS = new ScheduleDAO();
		try {
			return daoS.deleteOldSchedules(adminPass, daysOld);//breaks here

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

		DeleteScheduleOldResponse response = null;

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
				response = new DeleteScheduleOldResponse("name", 200); // OPTIONS needs a 200 response
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
			response = new DeleteScheduleOldResponse("Bad Request:" + pe.getMessage(), 422); // unable to process input
			responseJson.put("body", new Gson().toJson(response));
			processed = true;
			body = null;
		}

		if (!processed) {
			DeleteScheduleOldRequest req = new Gson().fromJson(body, DeleteScheduleOldRequest.class);

			logger.log(req.toString());

			DeleteScheduleOldResponse resp;
			if (logger != null) {
				logger.log(req.adminPass + " " + ", " + req.daysOld + " ");
			}
			try {
				int numDeleted = deleteOldSchedules(req.adminPass, req.daysOld);
				if (logger != null) {
					logger.log("/n delete old schedule function worked/n");
				}
				if (logger != null) {
					logger.log("/n" + numDeleted + "/n");
				}
				if (numDeleted>=0) {
					logger.log("DeleteScheduleOld worked");
					resp = new DeleteScheduleOldResponse(numDeleted);
					logger.log(resp.toString());
				} else {
					resp = new DeleteScheduleOldResponse("Unable to delete schedules " + req.daysOld + " days old", 400);
				}

			} catch (Exception e) {
				resp = new DeleteScheduleOldResponse(
						"Unable to delete schedules " + req.daysOld + " days old because of (" + e.getMessage() + ")", 404);
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
