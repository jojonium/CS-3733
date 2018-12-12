import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material';

import { ScheduleService } from '../schedule.service';
import { TimeSlot, MyDate, MyTime } from '../view-weekly-schedule/view-weekly-schedule-response';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  progressBarMode = '';
  submitted = false;
  id: string;
  details: ScheduleDetails;
  model = new SearchRequest('', '', '', '', '', '');
  allDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
  validMonths: string[];
  validYears: string[];
  validDays: string[];
  timeSlots: TimeSlot[];
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService,
    public snackbar: MatSnackBar
  ) { }
  
  onSubmit() {
    this.submitted = true;
    this.progressBarMode = "indeterminate";
    var modelToSend = new SearchRequest(this.id,
      (this.model.month) ? this.model.month.slice(0, 3) : '',
      (this.model.year) ? this.model.year : '',
      (this.model.dayOfWeek) ? this.model.dayOfWeek.toUpperCase() : '',
      (this.model.day) ? this.model.day : '',
      (this.model.time) ? this.model.time : '');
    console.log("MODEL TO SEND");
    
    console.log(modelToSend);
    this.scheduleService.showAvailableTimes(modelToSend)
      .subscribe(response => {
        console.log(response);
        var respBody = JSON.parse(response.body);
        if (respBody.httpCode == 200 || respBody.httpCode == 404) {
          this.timeSlots = respBody.view;
        } else if (+respBody.httpCode >= 400) {
          this.snackbar.open('Error, invalid request', 'DISMISS');
        }
        this.submitted = false;
        this.progressBarMode = '';
      });
  }
  
  setup() {
    let startMonth = this.details.startDate.month - 1;
    let endMonth = this.details.endDate.month;
    this.validMonths = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'].slice(startMonth, endMonth);
    
    let startYear = this.details.startDate.year;
    let endYear = this.details.endDate.year;
    this.validYears = new Array<string>();
    var x;
    for (x = +startYear; x <= +endYear; ++x) {
      this.validYears.push(""+x);
    }
    
    var startDate = new Date(this.details.startDate.year, this.details.startDate.month - 1, this.details.startDate.day);
    var endDate = new Date(this.details.endDate.year, this.details.endDate.month - 1, this.details.endDate.day);
    
    this.validDays = new Array<string>();
    
    if (endDate.valueOf() - startDate.valueOf() > 86400000 * 5) { // more than 5 days apart
      this.validDays = this.allDays;
    } else {
      var runningDate = startDate;
      do {
        x = runningDate.getDay();
        this.validDays.push(this.allDays[x - 1]);
        runningDate = new Date(runningDate.valueOf() + 86400000);
      } while (x != endDate.getDay());
    }
    console.log(this.validDays);
    
  }

  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        console.log('ID: ' + params.get('id'));
        this.id = params.get('id');
        return this.scheduleService.retrieveScheduleDetails(this.id);
      })
    ).subscribe(response => {
      var respBody = JSON.parse(response.body);
      if (respBody.httpCode == '200') {
        console.log(respBody);
        this.details = respBody;
        this.setup();
      } else if (respBody.httpCode == '400') {
        this.snackbar.open("400 Error: Unable to show schedule with ID " + this.id, "DISMISS");
      }
    });
  }
}

export class SearchRequest {
  constructor(
    public scheduleID: string,
    public month: string,
    public year: string,
    public dayOfWeek: string,
    public day: string,
    public time: string
  ) { }
}

export class ScheduleDetails {
  constructor(
    public name: string,
    public duration: number,
    public endDate: MyDate,
    public endTime: MyTime,
    public startDate: MyDate,
    public startTime: MyTime,
    public timeSlots: TimeSlot[],
    public httpCode: string
  ) { }
}
