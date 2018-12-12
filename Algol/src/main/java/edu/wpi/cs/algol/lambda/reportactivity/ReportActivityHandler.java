package edu.wpi.cs.algol.lambda.reportactivity;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.lambda.reportactivity.ReportActivityRequest;
import edu.wpi.cs.algol.lambda.reportactivity.ReportActivityResponse;
import edu.wpi.cs.algol.model.Schedule;

public class ReportActivityHandler implements RequestStreamHandler {
	//test
	public LambdaLogger logger = null;
	public ArrayList<Schedule> schedules;
boolean reportActivity (String adminPass, int pastHour) throws Exception {
		if (logger != null) { logger.log("in ReportActivity"); }

		//variable setup
		ScheduleDAO daoS = new ScheduleDAO();
		try {
			schedules = daoS.reportActivity(adminPass, pastHour);
			return true;
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
		headerJson.put("Content-Type",  "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");
	        
		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		ReportActivityResponse response = null;
		
		// extract body from incoming HTTP POST request. If any error, then return 422 error
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
				response = new ReportActivityResponse("name", 200);  // OPTIONS needs a 200 response
		        responseJson.put("body", new Gson().toJson(response));
		        processed = true;
		        body = null;
			} else {
				body = (String)event.get("body");
				if (body == null) {
					body = event.toJSONString();  // this is only here to make testing easier
				}
			}
		} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new ReportActivityResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			ReportActivityRequest req = new Gson().fromJson(body, ReportActivityRequest.class);
			
			logger.log(req.toString());

			ReportActivityResponse resp;
			try {
				
				if (reportActivity(req.adminPass, req.pastHour)) {
					logger.log("ReportActivity worked");
					resp = new ReportActivityResponse(schedules);
					logger.log(resp.toString());
				}
				else {
					resp = new ReportActivityResponse("Unable to report schedules " + req.pastHour + "hours back", 400);
				}
					
				
			} catch (Exception e) {
				resp = new ReportActivityResponse("Unable to report schedules " + req.pastHour + " hours back because of (" + e.getMessage() + ")", 404);
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
