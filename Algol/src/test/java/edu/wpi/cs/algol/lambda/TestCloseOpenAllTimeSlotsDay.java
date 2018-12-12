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
import edu.wpi.cs.algol.lambda.closealltimeslotsday.CloseAllTimeSlotsDayHandler;
import edu.wpi.cs.algol.lambda.closealltimeslotsday.CloseAllTimeSlotsDayRequest;
import edu.wpi.cs.algol.lambda.closealltimeslotsday.CloseAllTimeSlotsDayResponse;
import edu.wpi.cs.algol.lambda.openalltimeslotsday.OpenAllTimeSlotsDayHandler;
import edu.wpi.cs.algol.lambda.openalltimeslotsday.OpenAllTimeSlotsDayRequest;
import edu.wpi.cs.algol.lambda.openalltimeslotsday.OpenAllTimeSlotsDayResponse;
import edu.wpi.cs.algol.lambda.testing.PostResponse;
import edu.wpi.cs.algol.model.Schedule;


public class TestCloseOpenAllTimeSlotsDay {
	
	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    } 

    @Test
    public void testCloseOpenAllTimeSlotDayMeeting() throws Exception {
    	CloseAllTimeSlotsDayHandler handler = new CloseAllTimeSlotsDayHandler();
    	OpenAllTimeSlotsDayHandler openHandler = new OpenAllTimeSlotsDayHandler();

        ScheduleDAO sDao = new ScheduleDAO();
        Schedule s= new Schedule("name", "12/9/2018",  "12/11/2018",  "9:00",  "10:00",  20);
        sDao.addSchedule(s);
        String sid = s.getId();
        String sc = s.getSecretCode();
    	CloseAllTimeSlotsDayRequest ar = new CloseAllTimeSlotsDayRequest(sid, sc,  "12/10/2018");
        
        String jsonRequest = new Gson().toJson(ar);
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("close"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CloseAllTimeSlotsDayResponse resp = new Gson().fromJson(post.body, CloseAllTimeSlotsDayResponse.class);
        
        Assert.assertEquals(200, resp.httpCode);
        
        OpenAllTimeSlotsDayRequest br = new OpenAllTimeSlotsDayRequest(sid, sc, "12/10/2018");
        
        jsonRequest = new Gson().toJson(br);
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        openHandler.handleRequest(input, output, createContext("open"));

        post = new Gson().fromJson(output.toString(), PostResponse.class);
        OpenAllTimeSlotsDayResponse openResp = new Gson().fromJson(post.body, OpenAllTimeSlotsDayResponse.class);
        
        sDao.deleteSchedule(sid, s.getSecretCode());
        Assert.assertEquals(200, openResp.httpCode);
        
    }


}
