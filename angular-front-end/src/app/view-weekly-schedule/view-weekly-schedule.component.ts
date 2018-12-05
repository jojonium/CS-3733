import { Component, OnInit, Inject } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';


import { ScheduleService } from '../schedule.service';
import { ViewWeeklyScheduleResponse, ViewWeeklyScheduleResponseBody, TimeSlot, MyDate, MyTime } from './view-weekly-schedule-response';

export interface Tile {
  class: string;
  text: string;
  text2: string;
  click: string;
}

@Component({
  selector: 'app-view-weekly-schedule',
  templateUrl: './view-weekly-schedule.component.html',
  styleUrls: ['./view-weekly-schedule.component.css']
})
export class ViewWeeklyScheduleComponent implements OnInit {
  week : ViewWeeklyScheduleResponseBody;
  tiles : Tile[];
  numDays: number;
  numTimes: number;
  id: string;
  reqDate: string;
  errorMessage = '';
  createMeetingData: CreateMeetingData;
  weekdays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  dateArray: Date[];
  timeArray: MyTime[];
  
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
  
  prettyPrintDate(d : Date): string {
    return (d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear();
  }
  
  addDays(d : MyDate, i : number): MyDate {
    var nd = new Date(new Date(d.year, d.month - 1, d.day).valueOf() + i * 8.64e+7);
    return new MyDate(nd.getFullYear(), nd.getMonth() + 1, nd.getDate());
  }
  
  openCreateMeetingDialog(): void {
    const dialogRef = this.createMeetingDialog.open(CreateMeetingDialog, {
      width: '400px',
      data: this.createMeetingData
    });
  }
  
  timeSlotClick(text: string, index: number): void {
    console.log("openTimeSlotClick, index: " + index);
    if (text == "Open") {
      let d = this.dateArray[(index % (this.numDays + 1)) - 1];
      let t = this.timeArray[Math.floor(index / (this.numDays + 1)) - 1]
      this.createMeetingData = {date: d,
        dateString: this.prettyPrintDate(d),
        dayOfWeek: this.weekdays[d.getDay()],
        time: t,
        timeString: this.prettyPrintTime(t),
        scheduleID: this.id,
        requester: '',
        secretCode: '',
        backRef: this
      }
      this.openCreateMeetingDialog();
    }
  }
  
  makeTiles = (input : TimeSlot[]) => {
    this.numDays = (Math.abs(this.getDate(this.week.startDate).valueOf() - this.getDate(this.week.endDate).valueOf()) / 8.64e+7) + 1;
    this.numTimes = input.length / this.numDays;
    
    var tileIndex = 0;
    
    this.tiles = new Array<Tile>((this.numDays + 1) * (this.numTimes + 1));
    this.dateArray = new Array<Date>(this.numDays);
    this.timeArray = new Array<MyTime>(this.numTimes);

    
    console.log(`numDays: ${this.numDays}, numTimes: ${this.numTimes}`);
    
    this.dateArray = new Array<Date>(this.numDays);
    this.timeArray = new Array<MyTime>(this.numTimes);
    
    // make array of times used by this schedule
    var timeStrings = new Array<string>(this.numTimes);
    var runningTime = this.week.startTime;
    for (let i = 0; i < this.numTimes; ++i) {
      this.timeArray[i] = new MyTime(runningTime.hour, runningTime.minute, 0, 0);
      runningTime.minute += this.week.duration;
      if (runningTime.minute == 60) {
        runningTime.hour++;
        runningTime.minute = 0;
      }
    }
    
    // blank tile for the top left
    this.tiles[tileIndex++] = {class: "blank", text: "", text2: "", click: null};
    
    // add in the date headers at the top of each column
    var runningDate = this.getDate(this.week.startDate);
    for (let i = 0; i < this.numDays; ++i) {
      let extraClass = "";
      if (i == 0) extraClass += ' first-dh';
      if (i == this.numDays - 1) extraClass += ' last-dh';
      this.dateArray[i]  = runningDate;
      this.tiles[tileIndex++] = {class: "date-header" + extraClass,
        text: this.weekdays[runningDate.getDay()] + ",",
        text2: this.prettyPrintDate(runningDate),
        click: ''
      };
      runningDate = new Date(runningDate.valueOf() + 8.64e+7); // add a day
    }
    
    // add in the times and timeslots
    for (let i = 0, j = 0, r = -1, c; i < input.length + this.numTimes; ++i) {
      if (i % (this.numDays + 1) == 0) { // beginning of a row
        c = 0; r++;
        // add a time to the start of the row
        let extraClass = '';
        if (i == 0) extraClass += ' first-th';
        if (i == input.length + this.numTimes - this.numDays - 1) extraClass += ' last-th';
        this.tiles[tileIndex++] = {class: "time-header" + extraClass,
          text: this.prettyPrintTime(this.timeArray[j++]),
          text2: "",
          click: ""
        };
      } else {
        // add an open/closed timeslot
        // r tracks row (time), and c tracks column (date) of the table.
        // the corresponding index in the TimeSlot array is (c * numDays) + r
        this.tiles[tileIndex++] = {class: input[c * this.numDays + r].isOpen ? 'open' : 'closed',
          text: input[c * this.numDays + r].isOpen ? 'Open' : '—',
          text2: "",
          click: input[c * this.numDays + r].isOpen ? 'openTimeSlotClick()' : ''
        };
        c++;
      }
    }
  };
  
  getTimeSlots(): void {
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        console.log('ID: ' + params.get('id'));
        this.id = params.get('id');
        this.reqDate = (params.has('date')) ? params.get('date') : null;
        return this.scheduleService.getSchedule(this.id, this.reqDate);
      })
    ).subscribe(vwsResponse => {
      this.week = JSON.parse(vwsResponse.body);
      if (this.week.httpCode == '200') {
        console.log(this.week);
        this.makeTiles(this.week.timeSlots);
      } else if (this.week.httpCode == '400') {
        this.errorMessage = "400 Error: Unable to show schedule with ID " + this.id;
      }
    });
  }
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService,
    public createMeetingDialog: MatDialog,
  ) { }

  ngOnInit() {
    console.log('ngOnInit: about to call scheduleService.getSchedule');
    this.getTimeSlots();
  }

}



export interface CreateMeetingData {
  date: Date,
  dateString: string,
  dayOfWeek: string,
  time: MyTime,
  timeString: string,
  scheduleID: string,
  requester: string,
  secretCode: string,
  backRef: ViewWeeklyScheduleComponent
}

export class CreateMeetingRequest{
  constructor(
    public requester: string,
    public scheduleID: string,
    public date: string,
    public time: string
  ) { }
}

export class CreateMeetingResponse {
  constructor(
    public body: string,
    public headers?: any
  ) {  }
}



@Component({
  selector: 'create-meeting-dialog',
  templateUrl: 'create-meeting-dialog.html',
})
export class CreateMeetingDialog {
  constructor(
    public dialogRef: MatDialogRef<CreateMeetingDialog>,
    @Inject(MAT_DIALOG_DATA) public data: CreateMeetingData,
    private scheduleService: ScheduleService
  ) { }
  
  message: string;
  message2: string;
  header = "Request meeting";
  secretCode: string;
  finished = false;
  closeButton = "CANCEL";

  closeCreateMeetingDialog(): void {
    this.dialogRef.close();
  }
  
  requestMeeting(data: CreateMeetingData): void {
    var modelToSend = {"requester": data.requester,
      "scheduleID": data.scheduleID,
      "date": data.dateString,
      "time": data.time.hour + ":" + data.time.minute
    };
    console.log(modelToSend);
    this.scheduleService.createMeeting(modelToSend)
      .subscribe(cmResponse => {
        console.log(`CreateMeetingComponent received response: ${cmResponse}`);
        var responseBody = JSON.parse(cmResponse.body);
        this.finished = true;
        this.closeButton = "CLOSE";
        if (responseBody.httpCode == 200 ) {          // success
          console.log("RESPONSE BODY:");
          console.log(responseBody);
          this.header = "Meeting created"
          this.message = "Your meeting was successfully scheduled! Your secret code is:";
          this.secretCode = responseBody['password'];
          this.message2 = "Write this down. You'll need it if you want to cancel your meeting in the future.";
          data.backRef.getTimeSlots();
        } else if (responseBody.httpCode == 400) {    // failure
          this.header = "409 error"
          this.message = "That time slot isn't open.";
        }
      });
  }
}
