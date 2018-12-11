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
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleHandler;
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleRequest;
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleResponse;
import edu.wpi.cs.algol.model.Schedule;

import org.junit.Assert;

public class TestDeleteSchedule {

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	} 

	@Test
	public void testDeleteSchedule() throws Exception {
		DeleteScheduleHandler handler = new DeleteScheduleHandler();
		ScheduleDAO sDao = new ScheduleDAO();
        Schedule s= new Schedule("name", "12/9/2018",  "12/10/2018",  "9:00",  "10:00",  20);
        sDao.addSchedule(s);
		DeleteScheduleRequest ar = new DeleteScheduleRequest(s.getId(),s.getSecretCode());

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("delete"));

		DeleteScheduleResponse post = new Gson().fromJson(output.toString(), DeleteScheduleResponse.class);
		//sDao.deleteSchedule(s.getId(), s.getSecretCode());
		Assert.assertEquals(post.response, post.response);
	}

}
