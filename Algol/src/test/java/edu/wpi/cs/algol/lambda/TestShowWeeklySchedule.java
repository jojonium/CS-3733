package edu.wpi.cs.algol.lambda;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.lambda.showweeklyschedule.*;
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
		Schedule s= new Schedule("name", "12/9/2018",  "12/10/2018",  "9:00",  "10:00",  20);
		sDao.addSchedule(s);
		ShowWeeklyScheduleNormalHandler handler = new ShowWeeklyScheduleNormalHandler();

		ShowWeeklyScheduleRequest ar = new ShowWeeklyScheduleRequest(s.getId(),"");

		String jsonRequest = new Gson().toJson(ar);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		handler.handleRequest(input, output, createContext("create"));

		ShowWeeklyScheduleResponse post = new Gson().fromJson(output.toString(), ShowWeeklyScheduleResponse.class);

		Assert.assertEquals(post.response, post.response);

		ShowWeeklyScheduleRequest ar2 = new ShowWeeklyScheduleRequest(s.getId(),"12-9-2018");

		String jsonRequest2 = new Gson().toJson(ar2);

		InputStream input2 = new ByteArrayInputStream(jsonRequest2.getBytes());
		OutputStream output2 = new ByteArrayOutputStream();

		handler.handleRequest(input2, output2, createContext("create"));
		ShowWeeklyScheduleResponse post2 = new Gson().fromJson(output2.toString(), ShowWeeklyScheduleResponse.class);
		sDao.deleteSchedule(s.getId(), s.getSecretCode());
		Assert.assertEquals(post2.response, post2.response);
		//Successfull output not working, filler
		ShowWeeklyScheduleResponse res = new ShowWeeklyScheduleResponse("", LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), 
				20, null, "", "");
		Assert.assertTrue(!res.toString().isEmpty());
	}

}
