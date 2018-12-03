import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { ScheduleService } from '../schedule.service';
import { ViewWeeklyScheduleResponse, ViewWeeklyScheduleResponseBody } from './view-weekly-schedule-response';

@Component({
  selector: 'app-view-weekly-schedule',
  templateUrl: './view-weekly-schedule.component.html',
  styleUrls: ['./view-weekly-schedule.component.css']
})
export class ViewWeeklyScheduleComponent implements OnInit {
  week : ViewWeeklyScheduleResponseBody;
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService
  ) { }

  ngOnInit() {
    console.log('ngOnInit: about to call scheduleService.getSchedule');
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        console.log('ID: ' + params.get('id'));
        return this.scheduleService.getSchedule(params.get('id'))
      })
    ).subscribe(vwsResponse => { this.week = JSON.parse(vwsResponse.body) });
  }

}
