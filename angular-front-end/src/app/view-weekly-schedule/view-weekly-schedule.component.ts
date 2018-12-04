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
  errorMessage: string;
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
    if (t.hour > 12) {
      period = ' PM';
      hour = t.hour - 12;
    }
    if (minute.length < 2)
      minute += "0";
    return hour.toString() + ':' + minute + period;
  }
  
  prettyPrintDate(d : Date): string {
    return this.weekdays[d.getDay()] + ', ' + (d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear();
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
        time: t,
        timeString: this.prettyPrintTime(t),
        scheduleID: this.id,
        requester: '',
        secretCode: ''
      }
      this.openCreateMeetingDialog();
    }
  }
  
  makeTiles = (input : TimeSlot[]) => {
    this.numDays = (Math.abs(this.getDate(this.week.startDate).valueOf() - this.getDate(this.week.endDate).valueOf()) / 8.64e+7) + 1;
    this.numTimes = input.length / this.numDays;
    
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
    
    console.log(this.timeArray);
    
    // blank tile for the top left
    this.tiles.push({class: "blank", text: "", click: null});
    
    // add in the date headers at the top of each column
    var runningDate = this.getDate(this.week.startDate);
    for (let i = 0; i < this.numDays; ++i) {
      let extraClass = "";
      if (i == 0) extraClass += ' first-dh';
      if (i == this.numDays - 1) extraClass += ' last-dh';
      this.dateArray[i]  = runningDate;
      this.tiles.push({class: "date-header" + extraClass,
        text: this.prettyPrintDate(runningDate),
        click: ''
      });
      runningDate = new Date(runningDate.valueOf() + 8.64e+7); // add a day
    }
    
    // add in the times and timeslots
    for (let i = 0, j = 0, k = 0; i < input.length + this.numTimes; ++i) {
      if (i % (this.numDays + 1) == 0) { // beginning of a row
        // add a time to the start of the row
        let extraClass = '';
        if (i == 0) extraClass += ' first-th';
        if (i == input.length + this.numTimes - this.numDays - 1) extraClass += ' last-th';
        this.tiles.push({class: "time-header" + extraClass,
          text: this.prettyPrintTime(this.timeArray[k++]),
          click: ''
        });
      } else {
        // add an open/closed timeslot
        this.tiles.push({class: input[j].isOpen ? 'open' : 'closed',
          text: input[j].isOpen ? 'Open' : '—',
          click: input[j++].isOpen ? 'openTimeSlotClick()' : ''
        });
      }
    }
  };
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService,
    public createMeetingDialog: MatDialog,
  ) { }

  ngOnInit() {

    console.log('ngOnInit: about to call scheduleService.getSchedule');
    this.tiles = [];
    /*this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        console.log('ID: ' + params.get('id'));
        this.id = params.get('id');
        return this.scheduleService.getSchedule(params.get('id'))
      })
    ).subscribe(vwsResponse => {
      this.week = JSON.parse(vwsResponse.body);
      if (this.week.httpCode == '200') {
        console.log(this.week.timeSlots);
        this.makeTiles(this.week.timeSlots);
      } else if (this.week.httpCode == '400') {
        this.errorMessage = "400 Error: Unable to show schedule with ID " + this.id;
      }
    });*/
    this.week = JSON.parse("{\"name\":\"Multi-week schedule\",\"startDate\":{\"year\":2018,\"month\":12,\"day\":3},\"endDate\":{\"year\":2018,\"month\":12,\"day\":8},\"startTime\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":0,\"second\":0,\"nano\":0},\"duration\":60,\"timeSlots\":[{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":3},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":true,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":3},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":4},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":true,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":4},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":5},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":true,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":5},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":true,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":6},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":6},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":7},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":7},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":8},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":true,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":8},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"}],\"httpCode\":200}");
    console.log(this.week);
    this.makeTiles(this.week.timeSlots);
  }

}



export interface CreateMeetingData {
  date: Date,
  dateString: string,
  time: MyTime,
  timeString: string,
  scheduleID: string,
  requester: string,
  secretCode: string
}

@Component({
  selector: 'create-meeting-dialog',
  templateUrl: 'create-meeting-dialog.html',
})
export class CreateMeetingDialog {
  constructor(
    public dialogRef: MatDialogRef<CreateMeetingDialog>,
    @Inject(MAT_DIALOG_DATA) public data: CreateMeetingData
  ) { }

  closeCreateMeetingDialog(): void {
    this.dialogRef.close();
  }
}
