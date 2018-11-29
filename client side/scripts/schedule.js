var showWeekUrl = "I need this from Matt";
var dialog;

$(document).ready(function() {
	var xhr = new XMLHttpRequest();
	var sid = location.search.substring(1).match(/scheduleID=(.*)/)[1];
	console.log(sid);
	xhr.open("GET", showWeekUrl + "?scheduleID=" + sid, true);
	xhr.send();

	console.log("sent");

	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log("XHR: " + xhr.responseText);
			processResponse(xhr.responseText);
		} else {
			// handle errors here
		}
	};
	
	colorTable();
});

var processResponse = function(result) {
	console.log("res: " + result);
	var js = JSON.parse(result);
	console.log("js: " + js);
};

var colorTable = function() {
	$("table.weekly-schedule tr td").each(function() {
		if ($(this).text() == "Open") {
			$(this).addClass("open");
			$(this).removeClass("closed filled");
			$(this).click(function() {
				scheduleMeeting(this);
			});
		} else if ($(this).text() == "Closed") {
			$(this).addClass("closed");
			$(this).removeClass("open filled");
		} else {
			$(this).addClass("filled");
			$(this).removeClass("open closed");
		}
	});
};

var scheduleMeeting = function(jq) {
	var time = $('th:first-child', $(jq).parents('tr')).text();
	var day = $(jq).closest('table').find('th').eq($(jq).index()).text();
	$("#day-span").text(day);
	$("#time-span").text(time);
	dialog.dialog("open");
};
