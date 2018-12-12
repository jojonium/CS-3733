package edu.wpi.cs.algol.lambda;

//import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.lambda.closetimeslot.CloseTimeSlotHandler;
import edu.wpi.cs.algol.lambda.closetimeslot.CloseTimeSlotRequest;
import edu.wpi.cs.algol.lambda.closetimeslot.CloseTimeSlotResponse;
import edu.wpi.cs.algol.lambda.createmeeting.CreateMeetingResponse;
import edu.wpi.cs.algol.lambda.opentimeslot.OpenTimeSlotHandler;
import edu.wpi.cs.algol.lambda.opentimeslot.OpenTimeSlotRequest;
import edu.wpi.cs.algol.lambda.opentimeslot.OpenTimeSlotResponse;
//import edu.wpi.cs.algol.db.ScheduleDAO;
//import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.lambda.testing.PostResponse;
import edu.wpi.cs.algol.model.Schedule;


public class TestCloseOpenTimeSlots {
	
	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    } 

    @Test
    public void testCloseTimeSlotMeeting() throws Exception {
    	CloseTimeSlotHandler handler = new CloseTimeSlotHandler();
    	OpenTimeSlotHandler openHandler = new OpenTimeSlotHandler();

        ScheduleDAO sDao = new ScheduleDAO();
        Schedule s= new Schedule("name", "12/9/2018",  "12/11/2018",  "9:00",  "10:00",  20);
        sDao.addSchedule(s);
        String sid = s.getId();
        String sc = s.getSecretCode();
    	CloseTimeSlotRequest ar = new CloseTimeSlotRequest(sid, sc,  "12/10/2018",  "9:00");
        
        //String ccRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(ar);
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("close"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CloseTimeSlotResponse resp = new Gson().fromJson(post.body, CloseTimeSlotResponse.class);
        
        Assert.assertEquals(200, resp.httpCode);
        
        OpenTimeSlotRequest br = new OpenTimeSlotRequest(sid, sc, "12/10/2018",  "9:00");
        
        //String ccRequest = new Gson().toJson(ar);
        jsonRequest = new Gson().toJson(br);
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        openHandler.handleRequest(input, output, createContext("open"));

        post = new Gson().fromJson(output.toString(), PostResponse.class);
        OpenTimeSlotResponse openResp = new Gson().fromJson(post.body, OpenTimeSlotResponse.class);
        //System.out.println(resp);
        
        sDao.deleteSchedule(sid, s.getSecretCode());
        Assert.assertEquals(200, openResp.httpCode);
        
    }


}
