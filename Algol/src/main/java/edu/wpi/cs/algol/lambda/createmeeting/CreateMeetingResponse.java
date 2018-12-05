package edu.wpi.cs.algol.lambda.createmeeting;

public class CreateMeetingResponse {
	String message;
	String password;
	int httpCode;
	
	public CreateMeetingResponse (String m, int code) {
		this.message = m;
		this.httpCode = code;
	}
	
	// 200 means success
	public CreateMeetingResponse (String m, String password) {
		this.message = m;
		this.password = password;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "message: " + message + "secret code:" + password;
	}
}
