package edu.wpi.cs.algol.lambda.createschedule;
public class CreateScheduleResponse {
	String response;
	String secretCode;
	String id;
	int httpCode;
	
	
	public CreateScheduleResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}

	// 200 means success	
	public CreateScheduleResponse (String secretCode, String id) {
		this.secretCode = secretCode;
		this.id = id;
		this.httpCode = 201;
	}

	public String toString(){
		return ("Schedule " + response + " was sucsessfully created.");
	}
}