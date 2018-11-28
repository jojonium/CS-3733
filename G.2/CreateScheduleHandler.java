
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import com.google.gson.Gson;


///NOTE: needs to be updated with proper path for our project DAOs'
import ScheduleDAO;

/**
 * Found gson JAR file from
 * https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar
 */
public class CreateScheduleHandler implements RequestHandler<S3Event, String> {

	public LambdaLogger logger = null;

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	boolean createSchedule(String name, String dateStart, String dateEnd, String timeStart, String timeEnd,  String duration) throws Exception {
		if (logger != null) { logger.log("in createSchedule"); }

		//variable setup
		ScheduleDAO daoS = new ScheduleDAO();
		int startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
		
		//parse the date Strings from the format: MM/DD/YYYY
		if(dateStart.length() == 11){
			String[] dateStartArray = dateStart.split("/");
			startYear = Integer.parseInt(dateStartArray[0]);
			startMonth = Integer.parseInt(dateStartArray[1]);
			startDay = Integer.parseInt(dateStartArray[2]);
		}
		if(dateEnd.length() == 11){
			String[] dateEndArray = dateEnd.split("/");
			endYear = Integer.parseInt(dateEndArray[0]);
			endMonth = Integer.parseInt(dateEndArray[1]);
			endDay = Integer.parseInt(dateEndArray[2]);
		}

		//parse the date Strings from the format: HH:MM
		if(timeStart.length() == 5 || timeStart.length() == 6){
			String[] timeStartArray = timeStart.split(":");
			startHour = Integer.parseInt(timeStartArray[0]);
			startMinute = Integer.parseInt(timeStartArray[1]);
		}
		if(timeEnd.length() == 5 || timeEnd.length() == 6){
			String[] timeEndArray = timeEnd.split(":");
			EndHour = Integer.parseInt(timeEndArray[0]);
			EndMinute = Integer.parseInt(timeEndArray[1]);
		}

		//parse the duration away
		String[] durationArray = duration.split(" ");
		durationInt = Integer.parseInt(durationArray[0]);

		//creating the localDateTime Objects
		LocalDateTime startDate = new LocalDateTime();
		startDate.of(startYear, startMonth, startDay, startHour, startMinute);
		LocalDateTime endDate = new LocalDateTime();
		endDate.of(endYear, endMonth, endDay, endHour, endMinute);
		//creating the schedule
		Schedule s = new Schedule(name, startDate.toLocalDate(), endDate.toLocalDate(), startDate.toLocalTime(), endDate.toLocalTime, durationInt);
		return daoS.addSchedule(s);
	}
	
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

		CreateScheduleResponse response = null;
		
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
				response = new CreateScheduleResponse("name", 200);  // OPTIONS needs a 200 response
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
			response = new CreateScheduleResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			CreateScheduleRequest req = new Gson().fromJson(body, CreateScheduleRequest.class);
			logger.log(req.toString());

			CreateScheduleResponse resp;
			try {
				if (createSchedule(req.name, req.dateStart, req.dateEnd, req.timeStart, req.timeEnd, req.duration)) {
					resp = new CreateScheduleResponse("Successfully created schedule:" + req.name);
				} else {
					resp = new CreateScheduleResponse("Unable to create schedule: " + req.name, 422);
				}
			} catch (Exception e) {
				resp = new CreateScheduleResponse("Unable to create schedule: " + req.name + "(" + e.getMessage() + ")", 403);
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
