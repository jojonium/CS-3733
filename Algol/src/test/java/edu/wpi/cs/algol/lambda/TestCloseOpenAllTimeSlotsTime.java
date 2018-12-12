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
import edu.wpi.cs.algol.lambda.closealltimeslotstime.CloseAllTimeSlotsTimeHandler;
import edu.wpi.cs.algol.lambda.closealltimeslotstime.CloseAllTimeSlotsTimeRequest;
import edu.wpi.cs.algol.lambda.closealltimeslotstime.CloseAllTimeSlotsTimeResponse;
import edu.wpi.cs.algol.lambda.openalltimeslotstime.OpenAllTimeSlotsTimeHandler;
import edu.wpi.cs.algol.lambda.openalltimeslotstime.OpenAllTimeSlotsTimeRequest;
import edu.wpi.cs.algol.lambda.openalltimeslotstime.OpenAllTimeSlotsTimeResponse;
import edu.wpi.cs.algol.lambda.testing.PostResponse;
import edu.wpi.cs.algol.model.Schedule;


public class TestCloseOpenAllTimeSlotsTime {
	
	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    } 

    @Test
    public void testCloseOpenAllTimeSlotTimeMeeting() throws Exception {
    	CloseAllTimeSlotsTimeHandler handler = new CloseAllTimeSlotsTimeHandler();
    	OpenAllTimeSlotsTimeHandler openHandler = new OpenAllTimeSlotsTimeHandler();

        ScheduleDAO sDao = new ScheduleDAO();
        Schedule s= new Schedule("name", "12/9/2018",  "12/11/2018",  "9:00",  "10:00",  20);
        sDao.addSchedule(s);
        String sid = s.getId();
        String sc = s.getSecretCode();
    	CloseAllTimeSlotsTimeRequest ar = new CloseAllTimeSlotsTimeRequest(sid, sc,  "9:00");
        
        String jsonRequest = new Gson().toJson(ar);
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("close"));

        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        CloseAllTimeSlotsTimeResponse resp = new Gson().fromJson(post.body, CloseAllTimeSlotsTimeResponse.class);
        
        Assert.assertEquals(200, resp.httpCode);
        
        OpenAllTimeSlotsTimeRequest br = new OpenAllTimeSlotsTimeRequest(sid, sc, "9:00");
        
        jsonRequest = new Gson().toJson(br);
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        openHandler.handleRequest(input, output, createContext("open"));

        post = new Gson().fromJson(output.toString(), PostResponse.class);
        OpenAllTimeSlotsTimeResponse openResp = new Gson().fromJson(post.body, OpenAllTimeSlotsTimeResponse.class);
        
        sDao.deleteSchedule(sid, s.getSecretCode());
        Assert.assertEquals(200, openResp.httpCode);
        
    }


}
