package edu.wpi.cs.algol.lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.GetObjectRequest;
//import com.amazonaws.services.s3.model.S3Object;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
//import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.model.Schedule;

/**
 * Found gson JAR file from
 * https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar
 */
public class CreateScheduleHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;
	public Schedule s;
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	boolean createSchedule(String name, String dateStart, String dateEnd, String timeStart, String timeEnd, String duration) throws Exception {
		if (logger != null) { logger.log("in createSchedule"); }

		//variable setup
		ScheduleDAO daoS = new ScheduleDAO(logger);
		
		
		int durationInt = Integer.parseInt(duration.substring(0, 2));
		logger.log("Parsed duration to get integer: "+ durationInt+"\n");
		//creating the schedule
		s = new Schedule(name, dateStart, dateEnd, timeStart, timeEnd, durationInt);
		
		return daoS.addSchedule(s);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to create a schedule \n");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type",  "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");
	        
		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		CreateScheduleResponse response = null;
		
		// extract body from incoming HTTP POST request. If any error, then return 422 error
		String body;
		boolean processed = false;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString() + "\n");
			
			String method = (String) event.get("httpMethod");
			if (method != null && method.equalsIgnoreCase("OPTIONS")) {
				logger.log("Options request");
				response = new CreateScheduleResponse("name", 201);  // OPTIONS needs a 200 response
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
			response = new CreateScheduleResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        logger.log(responseJson.toJSONString()+ "\n");
	        processed = true;
	        body = null;
		}

		if (!processed) {
			CreateScheduleRequest req = new Gson().fromJson(body, CreateScheduleRequest.class);
			logger.log("New Req for !processed" + req.toString() + "\n");

			CreateScheduleResponse resp;
			try {
				if (createSchedule(req.name, req.startDate, req.endDate, req.startTime, req.endTime, req.duration)) {
					logger.log("createSchedule miraculously turned true");
					resp = new CreateScheduleResponse(s.getSecretCode(), s.getId());
					logger.log("Successful creation of schedule");
				} else {
					resp = new CreateScheduleResponse("Unable to create schedule: " + req.name, 400);
					logger.log("Unable to create schedule:  " + req.name + " 400" + "\n" );
				}
			} catch (Exception e) {
				resp = new CreateScheduleResponse("Unable to create schedule: " + req.name + "(" + e.getMessage() + ")", 400);
				logger.log("Unable to create schedule:  " + req.name + " 400. Error Message: " + e + "\n" );
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
