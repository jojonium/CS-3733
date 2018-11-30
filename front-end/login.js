import {MDCRipple} from '@material/ripple';
import {MDCTextField} from '@material/textfield';
import {MDCSelect} from '@material/select';
import {MDCDialog} from '@material/dialog';

new MDCTextField(document.querySelector('.name'));
const startDate = new MDCTextField(document.querySelector('.start-date'));
const endDate = new MDCTextField(document.querySelector('.end-date'));
const startTime = new MDCTextField(document.querySelector('.start-time'));
const endTime = new MDCTextField(document.querySelector('.end-time'));

new MDCTextField(document.querySelector('.schedule-id'));
new MDCTextField(document.querySelector('.secret-code'));

new MDCRipple(document.querySelector('.create-schedule'));
new MDCRipple(document.querySelector('.view-schedule'));

const select = new MDCSelect(document.querySelector('.mdc-select'));

const createScheduleSuccessDialog = new MDCDialog(document.querySelector('.create-schedule-success-dialog'));
const createScheduleFailureDialog = new MDCDialog(document.querySelector('.create-schedule-failure-dialog'));


/* do some simple form validation */
startDate.listen('change', () => {
	if (!document.querySelector('#start-date-input').value.match(/^\d{1,2}\/\d{1,2}\/\d{4}$/)) {
		document.querySelector('#start-date-input').setCustomValidity("invalid");
	} else {
		document.querySelector('#start-date-input').setCustomValidity("");
	}
});

endDate.listen('change', () => {
	if (!document.querySelector('#end-date-input').value.match(/^\d{1,2}\/\d{1,2}\/\d{4}$/)) {
		document.querySelector('#end-date-input').setCustomValidity("invalid");
	} else {
		document.querySelector('#end-date-input').setCustomValidity("");
	}
});

startTime.listen('change', () => {
	if (!document.querySelector('#start-time-input').value.match(/^\d{1,2}:\d{2}$/)) {
		document.querySelector('#start-time-input').setCustomValidity("invalid");
	} else {
		document.querySelector('#start-time-input').setCustomValidity("");
	}
});

endTime.listen('change', () => {
	if (!document.querySelector('#end-time-input').value.match(/^\d{1,2}:\d{2}$/)) {
		document.querySelector('#end-time-input').setCustomValidity("invalid");
	} else {
		document.querySelector('#end-time-input').setCustomValidity("");
	}
});

/* use window namespace because it doesn't work otherwise */
window.handleCreateSchedule = (e) => {
	const createScheduleUrl = "https://24f2jgxv5i.execute-api.us-east-2.amazonaws.com/Alpha/createschedule";
	var incFlag = false;

	// grab data from form fields
	var form        = document.createScheduleForm;
	var sdateArg    = form.sdate.value;
	var edateArg    = form.edate.value;
	var stimeArg    = form.stime.value;
	var etimeArg    = form.etime.value;
	var nameArg     = form.name.value;
	var durationArg = form.duration.value;

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
				document.getElementById("sc-span").innerHTML = mySecret;
				createScheduleSuccessDialog.open();
			} else if (respjson.httpCode == '400') {
				createScheduleFailureDialog.open();
			}
		}
	}
};

