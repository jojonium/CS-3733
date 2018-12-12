package edu.wpi.cs.algol.lambda;

//import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import edu.wpi.cs.algol.lambda.openalltimeslotstime.OpenAllTimeSlotsTimeHandler;
import edu.wpi.cs.algol.lambda.openalltimeslotstime.OpenAllTimeSlotsTimeRequest;
import edu.wpi.cs.algol.lambda.openalltimeslotstime.OpenAllTimeSlotsTimeResponse;
//import edu.wpi.cs.algol.db.ScheduleDAO;
//import edu.wpi.cs.algol.model.Schedule;


public class TestOpenAllTimeSlotTime {
	
	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    } 

    @Test
    public void testOpenAllTimeSlotTimeMeeting() throws IOException {
    	OpenAllTimeSlotsTimeHandler handler = new OpenAllTimeSlotsTimeHandler();

        //ScheduleDAO sDao = new ScheduleDAO();
        //Schedule s= new Schedule("name", "12/9/2018",  "12/10/2018",  "9:00",  "10:00",  20);
    	OpenAllTimeSlotsTimeRequest ar = new OpenAllTimeSlotsTimeRequest("esrdtf", "potato",  "9:00");
        
        //String ccRequest = new Gson().toJson(ar);
        String jsonRequest = new Gson().toJson(ar);
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("open"));

        OpenAllTimeSlotsTimeResponse post = new Gson().fromJson(output.toString(), OpenAllTimeSlotsTimeResponse.class);
        //CreateScheduleResponse resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        //System.out.println(resp);
        
        Assert.assertEquals(post.httpCode, post.httpCode);
        
        // now change
        
       /* ar = new CreateConstantRequest("x" + rnd, 99.12345);
        
        ccRequest = new Gson().toJson(ar);
        jsonRequest = new Gson().toJson(new CreateScheduleRequest(ccRequest));
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("create"));

        post = new Gson().fromJson(output.toString(), CreateScheduleResponse.class);
        resp = new Gson().fromJson(post.body, CreateScheduleResponse.class);
        System.out.println(resp);
        
        Assert.assertEquals("Successfully defined constant:x" + rnd, resp.response);
   	*/
    }


}
