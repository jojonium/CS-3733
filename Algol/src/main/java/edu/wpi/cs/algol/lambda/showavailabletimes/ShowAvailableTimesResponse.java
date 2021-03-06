package edu.wpi.cs.algol.lambda.showavailabletimes;

import java.util.ArrayList;


import edu.wpi.cs.algol.model.TimeSlot;

public class ShowAvailableTimesResponse {
	String message;
	ArrayList<TimeSlot> view;
	int httpCode;

	public ShowAvailableTimesResponse(String m, int code) {
		this.message = m;
		this.httpCode = code;
	}

	// 200 means success
	public ShowAvailableTimesResponse(String m, ArrayList<TimeSlot> view) {
		this.message = m;
		this.view = view;
		this.httpCode = 200;
	}

	public String toString() {
		//toString overloaded to show details of response
		String retString = "\nShow Available Report\n";
		for (TimeSlot t : view) {
			if (t.getBeginDateTime() != null) {
				retString += "TimeSlot: " + t.getBeginDateTime() + "\n";
			}

			else {
				retString += "TimeSlot null error \n";
			}

		} // end of for()
		return retString;
	}
}
