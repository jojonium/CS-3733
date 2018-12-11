package edu.wpi.cs.algol.lambda.createmeeting;

public class CreateMeetingResponse {
	public String response;
	String password;
	public int httpCode;
	
	public CreateMeetingResponse (String m, int code) {
		this.response = m;
		this.httpCode = code;
	}
	
	// 200 means success
	public CreateMeetingResponse (String m, String password) {
		this.response = m;
		this.password = password;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "message: " + response + "secret code:" + password;
	}
}
