package edu.wpi.cs.algol.lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import edu.wpi.cs.algol.model.TimeSlot;

public class ShowWeeklyScheduleNormalHandler implements RequestStreamHandler {
	private LambdaLogger logger = null;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to show a weekly schedule in normal mode... \n");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type",  "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");
	        
		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		ShowWeeklyScheduleResponse response = null;
		
		// extract body from incoming HTTP GET request. If any error, then return 422 error
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
				response = new ShowWeeklyScheduleResponse("name", 201);  // OPTIONS needs a 200 response
		        responseJson.put("body", new Gson().toJson(response));
		        logger.log(responseJson.toJSONString()+ "\n");
		        processed = true;
		        body = null;
			} else {
				body = (String)event.get("body");
				if (body == null) {
					body = event.toJSONString();  // this is only here to make testing easier
					
				}
				logger.log(body.toString()+ "\n");
			}
		} catch (ParseException pe) {
			logger.log(pe.toString() + "\n");
			response = new ShowWeeklyScheduleResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        logger.log(responseJson.toJSONString()+ "\n");
	        processed = true;
	        body = null;
		}

		if (!processed) {
			ShowWeeklyScheduleRequest req = new Gson().fromJson(body, ShowWeeklyScheduleRequest.class);
			logger.log("New Req for !processed" + req.toString() + "\n");

			ShowWeeklyScheduleResponse resp;
			try {
				/*
				 * TODO Actually grab a schedule from the database
				 * BEGIN PLACEHOLDER
				 */
				ArrayList<TimeSlot> ts = new ArrayList<TimeSlot>();
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 9, 0, 0), true, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 9, 30, 0), true, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 10, 0, 0), false, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 10, 30, 0), true, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 11, 0, 0), true, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 26, 11, 30, 0), false, null, req.scheduleID));

				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 9, 0, 0), false, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 9, 30, 0), false, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 10, 0, 0), false, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 10, 30, 0), true, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 11, 0, 0), true, null, req.scheduleID));
				ts.add(new TimeSlot(null, LocalDateTime.of(2018, Month.NOVEMBER, 27, 11, 30, 0), false, null, req.scheduleID));
				
				resp = new ShowWeeklyScheduleResponse(req.scheduleID, "An Example Schedule", "11/26/2018", "11/27/2018", "9:00",
						"12:00", "30 minutes", ts);
				logger.log("Created response: " + resp.toString() + "\n");
				/*
				 * END PLACEHOLDER
				 */
			} catch (Exception e) {
				resp = new ShowWeeklyScheduleResponse("Unable to show schedule " + req.scheduleID + " (" + e.getMessage() + ")", 400);
				logger.log("Unable to show schedule:  " + req.scheduleID + " 400. Error Message: " + e + "\n" );
			}

			// compute proper response
	        responseJson.put("body", new Gson().toJson(resp));  
	        
	        logger.log( "\n"+ responseJson.toJSONString() + "\n");
		}
		
        logger.log("end result:" + responseJson.toJSONString() + "\n");
        logger.log(responseJson.toJSONString() + "\n");
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
	}
}
