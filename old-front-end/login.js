import {MDCRipple} from '@material/ripple';
import {MDCTextField} from '@material/textfield';
import {MDCSelect} from '@material/select';
import {MDCDialog} from '@material/dialog';
import {MDCLinearProgress} from '@material/linear-progress';

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

const progressBar = new MDCLinearProgress(document.querySelector('.mdc-linear-progress'));
progressBar.foundation_.close();


/* hack to display placeholder text on form field focus */
const nameInput = document.getElementById('name-input');
const startDateInput = document.getElementById('start-date-input');
const endDateInput = document.getElementById('end-date-input');
const startTimeInput = document.getElementById('start-time-input');
const endTimeInput = document.getElementById('end-time-input');
startDateInput.addEventListener('focus', () => startDateInput.placeholder = "dd/MM/YYYY");
startDateInput.addEventListener('blur', () => startDateInput.placeholder = "");
endDateInput.addEventListener('focus', () => endDateInput.placeholder = "dd/MM/YYYY");
endDateInput.addEventListener('blur', () => endDateInput.placeholder = "");
startTimeInput.addEventListener('focus', () => startTimeInput.placeholder = "9:00");
startTimeInput.addEventListener('blur', () => startTimeInput.placeholder = "");
endTimeInput.addEventListener('focus', () => endTimeInput.placeholder = "17:00");
endTimeInput.addEventListener('blur', () => endTimeInput.placeholder = "");


/* do some simple form validation */
startDate.listen('change', () => {
	if (!startDateInput.value.match(/^\d{1,2}\/\d{1,2}\/\d{4}$/)) {
		startDateInput.setCustomValidity("invalid");
	} else {
		startDateInput.setCustomValidity("");
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

/* bind updateProgressBar */
/* timeout is necessary so that the custom validation fires first */
nameInput.addEventListener('change', () => setTimeout(() => updateProgressBar(), 1));
startDateInput.addEventListener('change', () => setTimeout(() => updateProgressBar(), 1));
endDateInput.addEventListener('change', () => setTimeout(() => updateProgressBar(), 1));
startTimeInput.addEventListener('change', () => setTimeout(() => updateProgressBar(), 1));
endTimeInput.addEventListener('change', () => setTimeout(() => updateProgressBar(), 1));


/* use window namespace because it doesn't work otherwise */
window.updateProgressBar = () => {
	var progress = 0;
	if (nameInput.checkValidity())
		progress += .2;
	if (startDateInput.checkValidity())
		progress += .2;
	if (endDateInput.checkValidity())
		progress += .2;
	if (startTimeInput.checkValidity())
		progress += .2;
	if (endTimeInput.checkValidity())
		progress += .2;
	progressBar.foundation_.setProgress(progress);
	if (progress > 0) {
		progressBar.foundation_.open();
	} else {
		progressBar.foundation_.close();
	}
};

window.handleCreateSchedule = (e) => {
	const createScheduleUrl = "https://24f2jgxv5i.execute-api.us-east-2.amazonaws.com/Alpha/createschedule";
	
	document.querySelector('.create-schedule').disabled = "disabled";
	document.querySelector('.mdc-linear-progress').className += ' mdc-linear-progress--indeterminate';
	
	// grab data from form fields
	const form        = document.createScheduleForm;
	const sdateArg    = form.sdate.value;
	const edateArg    = form.edate.value;
	const stimeArg    = form.stime.value;
	const etimeArg    = form.etime.value;
	const nameArg     = form.name.value;
	const durationArg = form.duration.value;

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
			document.querySelector('.create-schedule').disabled = "";
			document.querySelector('.mdc-linear-progress').classList.remove('mdc-linear-progress--indeterminate');
			progressBar.foundation_.open();
		}
	}
};

window.handleViewSchedule = (e) => {
	// grab data from form fields
	const form = document.viewScheduleForm;
	const scheduleIDArg = form.scheduleID.value;
	const secretCodeArg = form.secretCode.value;
	
	console.log("scheduleID: " + scheduleIDArg + ", secretCode: " + secretCodeArg);
};

