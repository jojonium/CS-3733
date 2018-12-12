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

//import edu.wpi.cs.algol.db.ScheduleDAO;
import edu.wpi.cs.algol.lambda.createschedule.CreateScheduleHandler;
import edu.wpi.cs.algol.lambda.createschedule.CreateScheduleRequest;
import edu.wpi.cs.algol.lambda.createschedule.CreateScheduleResponse;
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleHandler;
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleRequest;
import edu.wpi.cs.algol.lambda.deleteschedule.DeleteScheduleResponse;
import edu.wpi.cs.algol.lambda.deletescheduleold.DeleteScheduleOldHandler;
import edu.wpi.cs.algol.lambda.deletescheduleold.DeleteScheduleOldRequest;
import edu.wpi.cs.algol.lambda.deletescheduleold.DeleteScheduleOldResponse;
import edu.wpi.cs.algol.lambda.extenddate.ExtendDateHandler;
import edu.wpi.cs.algol.lambda.extenddate.ExtendDateRequest;
import edu.wpi.cs.algol.lambda.extenddate.ExtendDateResponse;
import edu.wpi.cs.algol.lambda.extendtime.ExtendTimeHandler;
import edu.wpi.cs.algol.lambda.extendtime.ExtendTimeRequest;
import edu.wpi.cs.algol.lambda.extendtime.ExtendTimeResponse;
//import edu.wpi.cs.algol.model.Schedule;
import edu.wpi.cs.algol.lambda.testing.PostResponse;



public class TestCreateExtendDeleteSchedule {

	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	} 

	@Test
	public void testCreateExtendDeleteSchedule() throws Exception {
		CreateScheduleHandler createHandler = new CreateScheduleHandler();

		//ScheduleDAO sDao = new ScheduleDAO();

		/**** Successful Creation ****/
		CreateScheduleRequest createGoodRequest = new CreateScheduleRequest("name", "12/9/2018",  "12/10/2018",  "9:00",  "10:00",  "20");

		String jsonRequest = new Gson().toJson(createGoodRequest);

		InputStream input = new ByteArrayInputStream(jsonRequest.getBytes());
		OutputStream output = new ByteArrayOutputStream();

		createHandler.handleRequest(input, output, createContext("create"));

		PostResponse createPost = new Gson().fromJson(output.toString(), PostResponse.class);
		CreateScheduleResponse createResp = new Gson().fromJson(createPost.body, CreateScheduleResponse.class);


		// checks for successful response code
		Assert.assertEquals(201, createResp.getHttpCode());

		/**** Successful Date Adjustments ****/
		ExtendDateHandler extendDateHandler = new ExtendDateHandler();

        ExtendDateRequest extendDateGoodRequest = new ExtendDateRequest(createResp.getId(), createResp.getSecretCode(),  "12/8/2018",  "12/11/2018");

        jsonRequest = new Gson().toJson(extendDateGoodRequest);
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        extendDateHandler.handleRequest(input, output, createContext("extend"));
        PostResponse adjustPost = new Gson().fromJson(output.toString(), PostResponse.class);
        ExtendDateResponse adjustResp = new Gson().fromJson(adjustPost.body, ExtendDateResponse.class);
        
        Assert.assertEquals(200, adjustResp.getHttpCode());
	
        
        
        /**** Successful Time Adjustments ****/
        ExtendTimeHandler extendTimeHandler = new ExtendTimeHandler();

        ExtendTimeRequest extendTimeGoodRequest = new ExtendTimeRequest(createResp.getId(), createResp.getSecretCode(),  "8:00",  "11:00");

        jsonRequest = new Gson().toJson(extendTimeGoodRequest);
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        extendTimeHandler.handleRequest(input, output, createContext("extend"));
        PostResponse extendTimePost = new Gson().fromJson(output.toString(), PostResponse.class);
        ExtendTimeResponse extendTimeResp = new Gson().fromJson(extendTimePost.body, ExtendTimeResponse.class);
        
        Assert.assertEquals(200, extendTimeResp.httpCode);
        
        
		/**** Failed Date Adjustments ****/
        ExtendDateRequest extendDateBadRequest = new ExtendDateRequest("", "",  "",  "");

        jsonRequest = new Gson().toJson(extendDateBadRequest);
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        extendDateHandler.handleRequest(input, output, createContext("extend"));
        adjustPost = new Gson().fromJson(output.toString(), PostResponse.class);
        adjustResp = new Gson().fromJson(adjustPost.body, ExtendDateResponse.class);
        
        Assert.assertEquals(400, adjustResp.getHttpCode());

        
        /**** Failed Time Adjustments ****/
        ExtendTimeRequest extendTimeBadRequest = new ExtendTimeRequest("", "",  "",  "");

        jsonRequest = new Gson().toJson(extendTimeBadRequest);
        
        input = new ByteArrayInputStream(jsonRequest.getBytes());
        output = new ByteArrayOutputStream();

        extendTimeHandler.handleRequest(input, output, createContext("extend"));
        extendTimePost = new Gson().fromJson(output.toString(), PostResponse.class);
        extendTimeResp = new Gson().fromJson(extendTimePost.body, ExtendTimeResponse.class);
        
        Assert.assertEquals(400, extendTimeResp.httpCode);
        
		
		/**** Successful Deletion ****/
		DeleteScheduleHandler deleteHandler = new DeleteScheduleHandler();
		DeleteScheduleRequest deleteGoodRequest = new DeleteScheduleRequest(createResp.getId(),createResp.getSecretCode());

		jsonRequest = new Gson().toJson(deleteGoodRequest);

		input = new ByteArrayInputStream(jsonRequest.getBytes());
		output = new ByteArrayOutputStream();

		deleteHandler.handleRequest(input, output, createContext("delete"));
		PostResponse deletePost = new Gson().fromJson(output.toString(), PostResponse.class);
		DeleteScheduleResponse deleteResp = new Gson().fromJson(deletePost.body, DeleteScheduleResponse.class);

		Assert.assertEquals(200, deleteResp.getHttpCode());


		/**** Failed creation ****/

		CreateScheduleRequest createBadRequest = new CreateScheduleRequest("", "",  "",  "",  "",  "");


		jsonRequest = new Gson().toJson(createBadRequest);

		input = new ByteArrayInputStream(jsonRequest.getBytes());
		output = new ByteArrayOutputStream();

		createHandler.handleRequest(input, output, createContext("create"));

		createPost = new Gson().fromJson(output.toString(), PostResponse.class);
		createResp = new Gson().fromJson(createPost.body, CreateScheduleResponse.class);

		Assert.assertEquals(400, createResp.getHttpCode());


		/**** Failed deletion ****/
		
		DeleteScheduleRequest deleteBadRequest = new DeleteScheduleRequest(createResp.getId(),createResp.getSecretCode());

		jsonRequest = new Gson().toJson(deleteBadRequest);

		input = new ByteArrayInputStream(jsonRequest.getBytes());
		output = new ByteArrayOutputStream();

		deleteHandler.handleRequest(input, output, createContext("delete"));
		deletePost = new Gson().fromJson(output.toString(), PostResponse.class);
		deleteResp = new Gson().fromJson(deletePost.body, DeleteScheduleResponse.class);

		Assert.assertEquals(404, deleteResp.getHttpCode());

		// remember to delete the created schedule from database
		//sDao.deleteSchedule(resp.getId(), resp.getSecretCode());
			
		
		// Delete old Schedules
		DeleteScheduleOldHandler deleteOldHanlder= new DeleteScheduleOldHandler();
		DeleteScheduleOldRequest deleteOldRequest = new DeleteScheduleOldRequest("secret code",10000);

		jsonRequest = new Gson().toJson(deleteOldRequest);

		input = new ByteArrayInputStream(jsonRequest.getBytes());
		output = new ByteArrayOutputStream();

		deleteOldHanlder.handleRequest(input, output, createContext("deleteold"));
		PostResponse deleteOldPost = new Gson().fromJson(output.toString(), PostResponse.class);
		DeleteScheduleOldResponse deleteOldResp = new Gson().fromJson(deleteOldPost.body, DeleteScheduleOldResponse.class);

		Assert.assertEquals(400, deleteOldResp.httpCode);
	}


}
