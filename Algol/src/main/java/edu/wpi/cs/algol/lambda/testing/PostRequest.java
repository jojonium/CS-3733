package edu.wpi.cs.algol.lambda.testing;

public class PostRequest {
	String body;
	
	public PostRequest(String s) {
		body = s;
	}
	public String getBody() {
		return this.body;
	}
}
