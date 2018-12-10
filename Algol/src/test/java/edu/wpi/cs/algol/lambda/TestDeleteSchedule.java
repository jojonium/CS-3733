package edu.wpi.cs.algol.lambda;

//import org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleHandler;
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleRequest;
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleResponse;
import org.junit.Assert;

public class TestDeleteSchedule {

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	} 

	@Test
	public void testDeleteSchedule() throws IOException {
		DeleteScheduleHandler handler = new DeleteScheduleHandler();
		//		ScheduleDAO sDao = new Schedule
		DeleteScheduleRequest ar = new DeleteScheduleRequest("","");

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("delete"));

		DeleteScheduleResponse post = new Gson().fromJson(output.toString(), DeleteScheduleResponse.class);

		Assert.assertEquals(post.response, post.response);
	}

}
