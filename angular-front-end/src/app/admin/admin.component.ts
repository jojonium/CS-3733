import { Component, OnInit } from '@angular/core';
import { ScheduleService } from '../schedule.service';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  retModel = new Model('', '');
  delModel = new Model('', '');
  retrieveSubmitted = false;
  retrieveFinished = false;
  deleteSubmitted = false;
  deleteFinished = false;
  numDeleted = -1;
  scheds: IDSC[];
  
  constructor(
    private scheduleService: ScheduleService,
    public snackbar: MatSnackBar
  ) { }

  ngOnInit() {
  }
  
  onDeleteSubmit() {
    this.deleteSubmitted = true;
    this.deleteFinished = false;
    this.retrieveFinished = false;
    this.retrieveSubmitted = false;
    this.numDeleted = -1;
    this.scheds = null;
    this.scheduleService.deleteScheduleOld(+this.delModel.age, this.delModel.password)
      .subscribe(response => {
        console.log(response);
        var respBody = JSON.parse(response.body);
        if (respBody.httpCode == 202) {
          this.numDeleted = respBody.numDeleted;
          this.deleteFinished = true;
        } else if (+respBody.httpCode >= 400) {
          this.snackbar.open('Error, invalid request', 'DISMISS');
          this.deleteSubmitted = false;
          this.deleteFinished = false;
        }
      });
    console.log(this.delModel);
  }
  
  onRetrieveSubmit() {
    this.deleteSubmitted = false;
    this.deleteFinished = false;
    this.retrieveFinished = false;
    this.retrieveSubmitted = true;
    this.numDeleted = -1;
    this.scheds = null;
    this.scheduleService.reportActivity(+this.retModel.age, this.retModel.password)
      .subscribe(response => {
        console.log(response);
        var respBody = JSON.parse(response.body);
        if (respBody.httpCode == 200 || respBody.httpCode == 404) {
          // TODO display the actual response schedules
          this.scheds = respBody.RPOS;
          this.retrieveFinished = true;
        } else if (+respBody.httpCode >= 400) {
          this.snackbar.open('Error, invalid request', 'DISMISS');
          this.retrieveSubmitted = false;
          this.retrieveFinished = false;
        }
      });
  }

}

export class Model {
  constructor(
    public age: string,
    public password: string
  ) { }
}

export class IDSC {
  constructor(
    public scheduleID: string,
    public secretCode: string
  ) { }
}
