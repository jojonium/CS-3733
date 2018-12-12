package edu.wpi.cs.algol.lambda;

//import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.db.TimeSlotDAO;
import edu.wpi.cs.algol.lambda.createmeeting.CreateMeetingHandler;
import edu.wpi.cs.algol.lambda.createmeeting.CreateMeetingRequest;
import edu.wpi.cs.algol.lambda.createmeeting.CreateMeetingResponse;
import edu.wpi.cs.algol.lambda.testing.PostResponse;
//import edu.wpi.cs.algol.db.ScheduleDAO;
//import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.model.Schedule;


public class TestCreateMeeting {
	
	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    } 

    @Test
    public void testCreateMeeting() throws Exception {
        CreateMeetingHandler handler = new CreateMeetingHandler();

        //TimeSlotDAO tDao = new TimeSlotDAO();
        ScheduleDAO sDao = new ScheduleDAO();
        Schedule s= new Schedule("test schedule", "12/09/2018",  "12/18/2018",  "10:00",  "12:00",  20);
        sDao.addSchedule(s);
        String sid = s.getId();
        CreateMeetingRequest ar = new CreateMeetingRequest("Hagan", sid,  "12/10/2018",  "10:00");
        
        //String ccRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(ar);
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("create"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CreateMeetingResponse resp = new Gson().fromJson(post.body, CreateMeetingResponse.class);
        //System.out.println(resp);
        
        sDao.deleteSchedule(sid, s.getSecretCode());
        System.out.println(post.toString());
        System.out.println("yodel: " + resp.httpCode);
        Assert.assertEquals(200, resp.httpCode);
        
        
        // now change
        
    }

}
