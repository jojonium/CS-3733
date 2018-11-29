const createScheduleUrl = "assblast.me";

var handleGetSchedule = function(e) {                                            
	var form = document.enterCodeForm;                                       
	var arg1 = form.sid.value;                                              
	console.log(arg1);
	if (arg1.toLowerCase() == "cs3733") {
		window.open('klotski.html', '_self', false);
		return;
	}
	var xhr = new XMLHttpRequest();
 };

var handleCreateSchedule = function(e) {
	var incFlag = false;
	$("#create-box form table tr td input").css("border-color", "initial")
	var form = document.createScheduleForm;
	var sdateArg = form.sdate.value.trim();
	var edateArg = form.edate.value.trim();
	var stimeArg = form.stime.value.trim();
	var etimeArg = form.etime.value.trim();
	var nameArg = form.name.value.trim();
	var durationArg = form.duration.value;
	var dateRegex = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
	var timeRegex = /^\d{1,2}:\d{2}$/;
	if (!sdateArg.match(dateRegex)) {
		$("#start-date").css("border-color", "red")
		incFlag = true;
	}
	if (!edateArg.match(dateRegex)) {
		$("#end-date").css("border-color", "red");
		incFlag = true;
	}
	if (!stimeArg.match(timeRegex)) {
		$("#stime").css("border-color", "red");
		incFlag = true;
	}
	if (!etimeArg.match(timeRegex)) {
		$("#etime").css("border-color", "red");
		incFlag = true;
	}
	if (nameArg == "") {
		$("#name").css("border-color", "red");
		incFlag = true;
	}
	if (incFlag) return; // stop if form isn't completely filled out
	var queryString = "?";
	queryString += "name=" + nameArg;
	queryString += "&startDate=" + sdateArg;
	queryString += "&endDate=" + edateArg;
	queryString += "&startTime=" + stimeArg;
	queryString += "&endTime=" + etimeArg;
	queryString += "&duration=" + durationArg;
	console.log("query " + queryString);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", createScheduleUrl + queryString, true);

	xhr.onloadend = function() {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log("XHR: " + xhr.responseText);
			var mySecret = "8Bj3v9"; // TODO
			var mySid = "0239jf0329jf"; // TODO	
			$("#create-box").append("<p>Success!</p>");
			$("#create-box").append("<p>In case you want to " +
				"modify this schedule in the future, your " +
				"secret code is <b>" + mySecret + "</b>.</p>");
			$("#create-box").append('<a href="schedule.html?' +
				'scheduleID=' + mySid + '"><button class="' +
				'ui-button ui-widget ui-corner-all" ' +
				'type="button">View Schedule</a></button?');
			// do something
		}
	}
};
