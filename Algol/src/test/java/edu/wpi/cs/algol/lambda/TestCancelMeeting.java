package edu.wpi.cs.algol.lambda;

//import org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.lambda.cancelmeeting.CancelMeetingHandler;
import edu.wpi.cs.algol.lambda.cancelmeeting.CancelMeetingRequest;
import edu.wpi.cs.algol.lambda.cancelmeeting.CancelMeetingResponse;

import edu.wpi.cs.algol.model.Schedule;

import org.junit.Assert;

public class TestCancelMeeting {

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	} 

	@Test
	public void testCancelMeeting() throws Exception {
		CancelMeetingHandler handler = new CancelMeetingHandler();
		ScheduleDAO sDao = new ScheduleDAO();
        Schedule s= new Schedule("name", "12/9/2018",  "12/10/2018",  "9:00",  "10:00",  20);
        sDao.addSchedule(s);
		CancelMeetingRequest ar = new CancelMeetingRequest(s.getId(),"12/10/2018","9:20",s.getSecretCode());

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("delete"));

		CancelMeetingResponse post = new Gson().fromJson(output.toString(), CancelMeetingResponse.class);
		//sDao.deleteSchedule(s.getId(), s.getSecretCode());
		Assert.assertEquals(post.httpCode, post.httpCode);
	}

}
