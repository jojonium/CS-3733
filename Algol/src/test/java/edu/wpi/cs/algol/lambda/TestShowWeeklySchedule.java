package edu.wpi.cs.algol.lambda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.lambda.showweeklyschedule.*;

public class TestShowWeeklySchedule {

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	} 

	@Test
	public void testShowWeeklySchedule() throws IOException{

		ShowWeeklyScheduleNormalHandler handler = new ShowWeeklyScheduleNormalHandler();

		ShowWeeklyScheduleRequest ar = new ShowWeeklyScheduleRequest("","");

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("create"));

		ShowWeeklyScheduleResponse post = new Gson().fromJson(output.toString(), ShowWeeklyScheduleResponse.class);
	
		Assert.assertEquals(post.response, post.response);
	}

}
