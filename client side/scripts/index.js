const createScheduleUrl = "assblast.me";

var handleGetSchedule = function(e) {                                            
	var form = document.enterCodeForm;                                       
	var arg1 = form.code.value;                                              
	var data = {};                                                           
	data["scheduleID"] = arg1;                                               
	var js = JSON.stringify(data);                                           
	console.log("JS: " + js);
	var xhr = new XMLHttpRequest();
 };

var handleCreateSchedule = function(e) {
	var form = document.createScheduleForm;
	var sdateArg = form.sdate.value;
	var edateArg = form.edate.value;
	var stimeArg = form.stime.value;
	var etimeArg = form.etime.value;
	var nameArg = form.name.value;
	var durationArg = form.duration.value;
	var data = {};
	data["name"] = nameArg;
	data["startDate"] = sdateArg;
	data["endDate"] = edateArg;
	data["startTime"] = stimeArg;
	data["endTime"] = etimeArg;
	data["duration"] = durationArg;
	var json = JSON.stringify(data);
	console.log("JSON: " + json);
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
