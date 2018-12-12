package edu.wpi.cs.algol.lambda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;

//import static org.junit.Assert.*;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.lambda.retrievescheduledetails.*;
import edu.wpi.cs.algol.lambda.testing.PostResponse;

public class TestRetrieveScheduleDetails {

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	} 

	@Test
	public void testRetrieveDetails() throws Exception {
		ScheduleDAO sDao = new ScheduleDAO();
		Schedule s = new Schedule("aaname","12/10/2018","12/20/2018","9:00","10:00",20);
		sDao.addSchedule(s);
		RetrieveScheduleDetailsHandler handler = new RetrieveScheduleDetailsHandler();

		RetrieveScheduleDetailsRequest ar = new RetrieveScheduleDetailsRequest(s.getId());

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("get"));

		PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
		RetrieveScheduleDetailsResponse resp = new Gson().fromJson(post.body, RetrieveScheduleDetailsResponse.class);

		sDao.deleteSchedule(s.getId(), s.getSecretCode());
		Assert.assertEquals(200, resp.httpCode);

	}

}
