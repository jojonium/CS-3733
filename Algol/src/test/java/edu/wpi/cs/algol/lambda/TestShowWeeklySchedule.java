package edu.wpi.cs.algol.lambda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.lambda.showweeklyschedule.*;
import edu.wpi.cs.algol.lambda.testing.PostResponse;
import edu.wpi.cs.algol.model.Schedule;

public class TestShowWeeklySchedule {

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	} 

	@Test 
	public void testShowWeeklySchedule() throws Exception{
		ScheduleDAO sDao = new ScheduleDAO();
		Schedule s= new Schedule("name", "12/10/2018",  "12/11/2018",  "9:00",  "10:00",  20);
		sDao.addSchedule(s);
		ShowWeeklyScheduleNormalHandler handler = new ShowWeeklyScheduleNormalHandler();

		ShowWeeklyScheduleRequest ar = new ShowWeeklyScheduleRequest(s.getId(),"");

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("show"));
		
		PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
		ShowWeeklyScheduleResponse resp = new Gson().fromJson(post.body, ShowWeeklyScheduleResponse.class);

		Assert.assertEquals(200, resp.httpCode);

		ar = new ShowWeeklyScheduleRequest("","12-10-2018");

		jsonRequest = new Gson().toJson(ar);

		input = new ByteArrayInputStream(jsonRequest.getBytes());
		output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("show"));
		post = new Gson().fromJson(output.toString(), PostResponse.class);
		resp = new Gson().fromJson(post.body, ShowWeeklyScheduleResponse.class);
		
		Assert.assertEquals(400, resp.httpCode);

		sDao.deleteSchedule(s.getId(), s.getSecretCode());

	}
	
	@Test (expected = NullPointerException.class)
	public void testExceptionsThrown() throws IOException {
		
		ShowWeeklyScheduleNormalHandler handler = new ShowWeeklyScheduleNormalHandler();
		ShowWeeklyScheduleRequest ar = new ShowWeeklyScheduleRequest("","12-10-2018");

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output= new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("show"));
		PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
		ShowWeeklyScheduleResponse resp = new Gson().fromJson(post.body, ShowWeeklyScheduleResponse.class);
		
		Assert.assertEquals(400, resp.httpCode);

		
	}

}
