package edu.wpi.cs.algol.lambda.deleteschedule;
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

	public class DeleteScheduleHandler implements RequestStreamHandler {
		//test
		public LambdaLogger logger = null;
		
		boolean deleteSchedule(String sid, String scd) throws Exception {
			//logger test
			if (logger != null) { logger.log("in deleteSchedule"); }

			//variable setup
			ScheduleDAO daoS = new ScheduleDAO();
			try {
				return daoS.deleteSchedule(sid, scd);
				
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

			DeleteScheduleResponse response = null;
			
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
					response = new DeleteScheduleResponse("name", 200);  // OPTIONS needs a 200 response
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
				response = new DeleteScheduleResponse("Bad Request:" + pe.getMessage(), 422);  // unable to process input
		        responseJson.put("body", new Gson().toJson(response));
		        processed = true;
		        body = null;
			}

			if (!processed) {
				DeleteScheduleRequest req = new Gson().fromJson(body, DeleteScheduleRequest.class);
				
				logger.log(req.toString());

				DeleteScheduleResponse resp;
				if (logger != null) { logger.log(req.scheduleID + " " +  ", " + req.secretCode + " "); }
				try {
					if (deleteSchedule(req.scheduleID, req.secretCode)) {
						logger.log("deleteSchedule worked");
						resp = new DeleteScheduleResponse(req.scheduleID);
						logger.log("schedule successfully deleted");
					}
					else {
						resp = new DeleteScheduleResponse("Unable to delete Schedule: " + req.scheduleID, 400);
					}
						
					
				} catch (Exception e) {
					resp = new DeleteScheduleResponse("Unable to delete schedule: " + req.scheduleID + " because of (" + e.getMessage() + ")", 404);
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
