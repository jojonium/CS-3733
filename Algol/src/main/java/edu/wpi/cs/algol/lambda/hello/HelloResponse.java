package edu.wpi.cs.algol.lambda.hello;

public class HelloResponse {
	String message;
	int httpCode;
	
	public HelloResponse (String m, int code) {
		this.message = m;
		this.httpCode = code;
	}
	
	// 200 means success
	public HelloResponse (String m) {
		this.message = m;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "message: " + message;
	}
}
