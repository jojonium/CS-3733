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

//import edu.wpi.cs.algol.db.ScheduleDAO;
//import edu.wpi.cs.algol.model.Schedule;

import edu.wpi.cs.algol.lambda.retrievedetails.RetrieveDetailsHandler;
import edu.wpi.cs.algol.lambda.retrievedetails.RetrieveDetailsRequest;
import edu.wpi.cs.algol.lambda.retrievedetails.RetrieveDetailsResponse;
import edu.wpi.cs.algol.lambda.testing.PostResponse;
import edu.wpi.cs.algol.model.Schedule;


public class TestRetrieveDetails {
	
	Context createContext(String apiCall) {
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    } 

    @Test
    public void retrieveDetailsDate() throws Exception {
    	RetrieveDetailsHandler handler = new RetrieveDetailsHandler();

        ScheduleDAO sDao = new ScheduleDAO();
        Schedule s= new Schedule("name", "12/9/2018",  "12/10/2018",  "9:00",  "10:00",  20);
        sDao.addSchedule(s);
        String sid = s.getId();
        String sc = s.getSecretCode();
    	RetrieveDetailsRequest ar = new RetrieveDetailsRequest(sid, sc);

        String jsonRequest = new Gson().toJson(ar);
        
        InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
        OutputStream output = new ByteArrayOutputStream();

        handler.handleRequest(input, output, createContext("get"));
        
        PostResponse post = new Gson().fromJson(output.toString(), PostResponse.class);
        RetrieveDetailsResponse resp = new Gson().fromJson(post.body, RetrieveDetailsResponse.class);
        
        sDao.deleteSchedule(sid, s.getSecretCode());
        Assert.assertEquals(200, resp.httpCode);
        
    }


}
