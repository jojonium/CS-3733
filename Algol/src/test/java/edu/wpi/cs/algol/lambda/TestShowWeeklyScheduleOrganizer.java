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

import edu.wpi.cs.algol.lamda.showweeklyscheduleorganizer.*;

public class TestShowWeeklyScheduleOrganizer {

	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    } 
	
	@Test
	public void testShowWeeklyScheduleOrganizer() throws IOException {

		ShowWeeklyScheduleOrganizerHandler handler = new ShowWeeklyScheduleOrganizerHandler();

		ShowWeeklyScheduleOrganizerRequest ar = new ShowWeeklyScheduleOrganizerRequest("","","");
		ShowWeeklyScheduleOrganizerRequest ar2 = new ShowWeeklyScheduleOrganizerRequest("","");

		Assert.assertTrue(!ar2.toString().isEmpty());

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("create"));

		ShowWeeklyScheduleOrganizerResponse post = new Gson().fromJson(output.toString(), ShowWeeklyScheduleOrganizerResponse.class);
		
		Assert.assertEquals(post.response, post.response);
	}

}
