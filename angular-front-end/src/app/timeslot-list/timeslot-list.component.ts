import { Component, OnInit, Input, Inject } from '@angular/core';
import { TimeSlot, MyDate, MyTime } from '../view-weekly-schedule/view-weekly-schedule-response';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-timeslot-list',
  templateUrl: './timeslot-list.component.html',
  styleUrls: ['./timeslot-list.component.css']
})
export class TimeslotListComponent implements OnInit {
  @Input() timeSlots: TimeSlot[];
  displayedColumns: string[] = ['Date', 'Time', 'Request meeting'];
  days: string[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  
  constructor(
    public scheduleService: ScheduleService,
    public createMeetingDialog: MatDialog
  ) { }

  ngOnInit() {
  }
  
  getDate = (d : MyDate) => {
    return new Date(d.year, d.month - 1, d.day);
  };
  
  prettyPrintTime(t : MyTime): string {
    let period = ' AM';
    let hour = t.hour;
    let minute = t.minute.toString();
    if (t.hour > 11) period = ' PM';
    if (t.hour > 12) hour = t.hour - 12;
    if (minute.length < 2)
      minute += "0";
    return hour.toString() + ':' + minute + period;
  }
  
  prettyPrintDateWithDay(d : Date): string {
    return this.days[d.getDay()] + ', ' + (d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear();
  }
  
  prettyPrintDate(d: Date): string {
    return (d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear();
  }

  requestMeeting = (ts: TimeSlot) => {
    console.log(ts);
    var d = this.getDate(ts.beginDateTime.date);
    var t = ts.beginDateTime.time;
    var createMeetingData = {
            date: d,
            dateString: this.prettyPrintDate(d),
            dayOfWeek: this.days[d.getDay()],
            time: t,
            timeString: this.prettyPrintTime(t),
            scheduleID: ts.scheduleID,
            requester: '',
            secretCode: '',
            backRef: this
    }
    console.log(createMeetingData);
    this.openCreateMeetingDialog(createMeetingData);
  }
  
  openCreateMeetingDialog(data: CreateMeetingData2): void {
    const dialogRef = this.createMeetingDialog.open(CreateMeetingDialog2, {
      width: '400px',
      data: data
    });
  }
}
  
  
  
export interface CreateMeetingData2 {
  date: Date,
  dateString: string,
  dayOfWeek: string,
  time: MyTime,
  timeString: string,
  scheduleID: string,
  requester: string,
  secretCode: string,
  backRef: TimeslotListComponent
}


@Component({
  selector: 'create-meeting-dialog',
  templateUrl: 'create-meeting-dialog.html',
})
export class CreateMeetingDialog2 {
  constructor(
    public dialogRef: MatDialogRef<CreateMeetingDialog2>,
    @Inject(MAT_DIALOG_DATA) public data: CreateMeetingData2,
    private scheduleService: ScheduleService
  ) { }
  
  message: string;
  message2: string;
  header = "Request meeting";
  secretCode: string;
  finished = false;
  submitted = false;
  closeButton = "CANCEL";

  closeCreateMeetingDialog(): void {
    this.dialogRef.close();
  }
  
  requestMeeting(data: CreateMeetingData2): void {
    var modelToSend = {"requester": data.requester,
      "scheduleID": data.scheduleID,
      "date": data.dateString,
      "time": data.time.hour + ":" + data.time.minute
    };
    console.log(modelToSend);
    this.submitted = true;
    this.scheduleService.createMeeting(modelToSend)
      .subscribe(cmResponse => {
        console.log(`CreateMeetingComponent received response: ${cmResponse}`);
        var responseBody = JSON.parse(cmResponse.body);
        this.finished = true;
        this.closeButton = "CLOSE";
        if (responseBody.httpCode == 201  || responseBody.httpCode == 200) {          // success
          console.log("RESPONSE BODY:");
          console.log(responseBody);
          this.header = "Meeting created"
          this.message = "Your meeting was successfully scheduled! Your secret code is:";
          this.secretCode = responseBody['password'];
          this.message2 = "Write this down. You'll need it if you want to cancel your meeting in the future.";
        } else if (responseBody.httpCode == 400) {    // failure
          this.header = "409 error"
          this.message = "That time slot isn't open.";
        }
      });
  }
}

