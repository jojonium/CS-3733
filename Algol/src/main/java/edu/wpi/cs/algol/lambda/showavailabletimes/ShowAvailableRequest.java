package edu.wpi.cs.algol.lambda.showavailabletimes;

public class ShowAvailableRequest {
	String scheduleID;
	String month;
	String year;
	String dayOfWeek;
	String day;
	String time;
	
	public ShowAvailableRequest (String sid, String month, String year, String dayOfWeek, String day, String time) {
		this.scheduleID = sid;
		this.month = month;
		this.year = year;
		this.dayOfWeek = dayOfWeek;
		this.day = day;
		this.time = time;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShowAvailableRequest [scheduleID=" + scheduleID + ", month=" + month + ", year=" + year
				+ ", dayOfWeekh=" + dayOfWeek + ", day=" + day + ", time=" + time + "]";
	}
	
	
}
