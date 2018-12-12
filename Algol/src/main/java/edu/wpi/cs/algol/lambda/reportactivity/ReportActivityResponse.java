package edu.wpi.cs.algol.lambda.reportactivity;

import java.util.ArrayList;

import edu.wpi.cs.algol.model.Schedule;

public class ReportActivityResponse {
	public String response;
	public ArrayList<ReportActivityObject> RPOS;
	public int httpCode;
	
	/* used for errors or other responses that require a message */
	public ReportActivityResponse(String r, int code) {
		this.response = r;
		this.httpCode = code;
	}

	/* used for successful responses */
	public ReportActivityResponse(ArrayList<Schedule> schedules) {
		for(Schedule s: schedules) {
			RPOS.add(new ReportActivityObject(s));
		}
		this.httpCode = 200;
	}

	public String toString(){
		String retString = "\nAdmin Activity Report\n";
		for(ReportActivityObject rpo: RPOS) {
		if (rpo.scheduleID !=null && rpo.secretCode !=null) {
			retString+= "scheduleID: " + rpo.scheduleID + ", secretCode: " + rpo.secretCode + "\n";
		}
		
		else {
			retString+="Schedule ID or Secret Code null error \n";
		}
			
	}//end of for()
		return retString;
	}
	
}
