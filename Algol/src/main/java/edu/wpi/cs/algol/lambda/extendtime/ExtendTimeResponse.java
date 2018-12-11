package edu.wpi.cs.algol.lambda.extendtime;

public class ExtendTimeResponse {
	public String message;;
	public int httpCode;
	
	public ExtendTimeResponse (String m, int code) {
		this.message = m;
		this.httpCode = code;
	}
	
	// 200 means success
	public ExtendTimeResponse (String m) {
		this.message = m;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "message: " + message;
	}
}
