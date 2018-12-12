package edu.wpi.cs.algol.lambda.reportactivity;


import edu.wpi.cs.algol.model.Schedule;

public class ReportActivityObject {
	public String scheduleID;
	public String secretCode;
	public ReportActivityObject(Schedule s) {
	this.scheduleID = s.getId();
	this.secretCode = s.getSecretCode();
			
		}
	}
