const createScheduleUrl = "https://24f2jgxv5i.execute-api.us-east-2.amazonaws.com/Alpha/createschedule";

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
	$("#create-box form table tr td input").css("border-color", "initial");

	// grab data from form fields
	var form        = document.createScheduleForm;
	var sdateArg    = form.sdate.value.trim();
	var edateArg    = form.edate.value.trim();
	var stimeArg    = form.stime.value.trim();
	var etimeArg    = form.etime.value.trim();
	var nameArg     = form.name.value.trim();
	var durationArg = form.duration.value;

	// do a basic check of the inputs
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
	// stop if form isn't completely filled out
	if (incFlag) return; 

	// populate a JSON with form data
	var data = {};
	data["name"]      = nameArg;
	data["startDate"] = sdateArg;
	data["endDate"]   = edateArg;
	data["startTime"] = stimeArg;
	data["endTime"]   = etimeArg;
	data["duration"]  = durationArg;
	var json = JSON.stringify(data);
	console.log(json);

	var xhr = new XMLHttpRequest();
	xhr.open("POST", createScheduleUrl, true);
	
	// send the collected data as JSON
	xhr.send(json);

	// handle the server's response
	xhr.onloadend = function() {
		console.log(xhr);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log("XHR: " + this.responseText);
			var respjson = JSON.parse(this.responseText);
			console.log(respjson);
			respjson = JSON.parse(respjson.body);
			console.log(respjson);
			if (respjson.httpCode == '201') {
				var mySecret = respjson.secretCode;
				var mySid = respjson.id;
				$("#create-box").append("<p>Success!</p>");
				$("#create-box").append("<p>In case you want to " +
					"modify this schedule in the future, your " +
					"secret code is <b>" + mySecret + "</b>.</p>");
				$("#create-box").append('<a href="schedule.html?' +
					'scheduleID=' + mySid + '"><button class="' +
					'ui-button ui-widget ui-corner-all" ' +
					'type="button">View Schedule</a></button?');
			} else if (respjson.httpCode == '400') {
				$("#create-box").append("<p>Unexpected error!" +
					" Invalid input.</p>");
			}
		}
	}
};
