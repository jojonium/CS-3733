package edu.wpi.cs.algol.lambda.createschedule;
public class CreateScheduleResponse {
	public String response;
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

	/**
	 * @return the secretCode
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the httpCode
	 */
	public int getHttpCode() {
		return httpCode;
	}
	
	
}