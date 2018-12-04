import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { ScheduleService } from '../schedule.service';
import { ViewWeeklyScheduleResponse, ViewWeeklyScheduleResponseBody, TimeSlot, MyDate, MyTime } from './view-weekly-schedule-response';

export interface Tile {
  class: string;
  text: string;
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
  id: string;
  errorMessage: string;
  
  getDate = (d : MyDate) => {
    return new Date(d.year, d.month - 1, d.day);
  };
  
  prettyPrintTime = (t : MyTime) => {
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
  
  prettyPrintDate = (d : MyDate) => {
    return d.month + '/' + d.day + '/' + d.year;
  }
  
  addDays = (d : MyDate, i : number) => {
    var nd = new Date(new Date(d.year, d.month - 1, d.day).valueOf() + i * 8.64e+7);
    return new MyDate(nd.getFullYear(), nd.getMonth() + 1, nd.getDate());
  }
  
  makeTiles = (input : TimeSlot[]) => {
    this.numDays = (Math.abs(this.getDate(this.week.startDate).valueOf() - this.getDate(this.week.endDate).valueOf()) / 8.64e+7) + 1;
    // make array of time strings
    var numTimes = input.length / this.numDays;
    console.log(numTimes);
    var timeStrings = new Array<string>(numTimes);
    timeStrings[0] = this.prettyPrintTime(this.week.startTime);
    var runningTime = this.week.startTime;
    for (let i = 1; i < numTimes; ++i) {
      runningTime.minute += this.week.duration;
      if (runningTime.minute == 60) {
        runningTime.hour++;
        runningTime.minute = 0;
      }
      timeStrings[i] = this.prettyPrintTime(runningTime);
    }
    console.log(timeStrings);
    // blank tile for the top left
    this.tiles.push({class: "blank", text: ""});
    
    // add in the date headers at the top of each column
    var runningDate = this.week.startDate;
    for (let i = 0; i < this.numDays; ++i) {
      let extraClass = "";
      if (i == 0) extraClass += ' first-dh';
      if (i == this.numDays - 1) extraClass += ' last-dh';
      this.tiles.push({class: "date-header" + extraClass,
        text: this.prettyPrintDate(runningDate)
      });
      runningDate = this.addDays(runningDate, 1);
    }
    
    console.log(this.tiles);
    
    // add in the times and timeslots
    for (let i = 0, j = 0, k = 0; i < input.length + numTimes; ++i) {
      if (i % (this.numDays + 1) == 0) { // beginning of a row
        // add a time to the start of the row
        let extraClass = '';
        if (i == 0) extraClass += ' first-th';
        if (i == input.length + numTimes - this.numDays) extraClass += ' last-th';
        this.tiles.push({class: "time-header" + extraClass,
          text: timeStrings[k++]
        });
      } else {
        // add an open/closed timeslot
        this.tiles.push({class: input[j].isOpen ? 'open' : 'closed',
          text: input[j].isOpen ? 'Open' : '—'
        });
        j++;
      }
    }
  };
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService
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
    this.week = JSON.parse("{\"name\":\"Multi-week schedule\",\"startDate\":{\"year\":2018,\"month\":12,\"day\":3},\"endDate\":{\"year\":2018,\"month\":12,\"day\":8},\"startTime\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0},\"endTime\":{\"hour\":11,\"minute\":0,\"second\":0,\"nano\":0},\"duration\":60,\"timeSlots\":[{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":3},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":3},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":4},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":4},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":5},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":5},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":true,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":6},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":6},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":7},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":7},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":8},\"time\":{\"hour\":9,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"},{\"beginDateTime\":{\"date\":{\"year\":2018,\"month\":12,\"day\":8},\"time\":{\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0}},\"isOpen\":false,\"scheduleID\":\"nm111n\"}],\"httpCode\":200}");
    console.log(this.week);
    this.makeTiles(this.week.timeSlots);
  }

}
