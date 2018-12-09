package edu.wpi.cs.algol.lambda.extenddate;

public class ExtendDateResponse {
	String message;;
	int httpCode;
	
	public ExtendDateResponse (String m, int code) {
		this.message = m;
		this.httpCode = code;
	}
	
	// 200 means success
	public ExtendDateResponse (String m) {
		this.message = m;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "message: " + message;
	}
}
