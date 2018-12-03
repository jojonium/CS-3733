import { Component, OnInit } from '@angular/core';
import { WeeklySchedule } from './weekly-schedule';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-view-weekly-schedule',
  templateUrl: './view-weekly-schedule.component.html',
  styleUrls: ['./view-weekly-schedule.component.css']
})
export class ViewWeeklyScheduleComponent implements OnInit {
  week$ : Observable<WeeklySchedule>;
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService
  ) { }

  ngOnInit() {
    this.week$ = this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.scheduleService.getSchedule(params.get('id')))
    );
  }

}
