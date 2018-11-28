$(document).ready(function() {
	colorTable();
});

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
	alert("Meeting scheduled for " + day + " at " + time);
};
