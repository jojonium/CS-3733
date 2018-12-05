package edu.wpi.cs.algol.lambda.cancelmeeting;

public class CancelMeetingResponse {
	String message;
	int httpCode;
	
	public CancelMeetingResponse (String m, int code) {
		this.message = m;
		this.httpCode = code;
	}
	
	// 200 means success
	public CancelMeetingResponse (String m) {
		this.message = m;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "message: " + message;
	}
}
