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
	var sdateArg = form.sdate.value;
	var edateArg = form.edate.value;
	var stimeArg = form.stime.value;
	var etimeArg = form.etime.value;
	var nameArg = form.name.value;
	var durationArg = form.duration.value;
	if (sdateArg == "") {
		$("#start-date").css("border-color", "red")
		incFlag = true;
	}
	if (edateArg == "") {
		$("#end-date").css("border-color", "red");
		incFlag = true;
	}
	if (stimeArg == "") {
		$("#stime").css("border-color", "red");
		incFlag = true;
	}
	if (etimeArg == "") {
		$("#etime").css("border-color", "red");
		incFlag = true;
	}
	if (nameArg == "") {
		$("#name").css("border-color", "red");
		incFlag = true;
	}
	if (incFlag) return; // stop if form isn't completely filled out
	var data = {};
	data["name"] = nameArg;
	data["startDate"] = sdateArg;
	data["endDate"] = edateArg;
	data["startTime"] = stimeArg;
	data["endTime"] = etimeArg;
	data["duration"] = durationArg;
	var json = JSON.stringify(data);
	alert("JSON: " + json);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", createScheduleUrl, true);

	// send the JSON
	xhr.send(js);

	xhr.onloadend = function() {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log("XHR: " + xhr.responseText);
			// do something
		}
	}
};
