package edu.wpi.cs.algol.lambda.closetimeslot;
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
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleRequest;
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleResponse;

public class CloseTimeSlotHandler implements RequestStreamHandler {
	//test
	public LambdaLogger logger = null;
	
	boolean CloseTimeSlot(String sid, String scd, String d, String t) throws Exception {
		if (logger != null) { logger.log("in CloseTimeSlot"); }

		//variable setup
		TimeSlotDAO daoTS = new TimeSlotDAO();
		try {
			return daoTS.closeTimeSlot(sid, scd, d, t);
			
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

		CloseTimeSlotResponse response = null;
		
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
				response = new CloseTimeSlotResponse("name", 200);  // OPTIONS needs a 200 response
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
			response = new CloseTimeSlotResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			CloseTimeSlotRequest req = new Gson().fromJson(body, CloseTimeSlotRequest.class);
			
			logger.log(req.toString());

			CloseTimeSlotResponse resp;
			try {
				CloseTimeSlot(req.scheduleID, req.secretCode, req.date, req.time);
					logger.log("CloseTimeSlot worked");
					resp = new CloseTimeSlotResponse(req.scheduleID);
					logger.log("TimeSlot successfully closed");
				
			} catch (Exception e) {
				resp = new CloseTimeSlotResponse("Unable to close time slot at " + req.time + " at date " + req.date + " of schedule " + req.scheduleID + " because of (" + e.getMessage() + ")", 404);
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
