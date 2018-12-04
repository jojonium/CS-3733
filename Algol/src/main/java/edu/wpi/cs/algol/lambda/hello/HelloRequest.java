package edu.wpi.cs.algol.lambda.hello;

public class HelloRequest {
	String name;
	
	public HelloRequest (String n) {
		this.name = n;
	}
	
	public String toString() {
		return "name: " + name;
	}
}
