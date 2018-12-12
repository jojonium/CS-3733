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
  numDeleted: number;
  scheds: IDSC[];
  
  constructor(
    private scheduleService: ScheduleService,
    public snackbar: MatSnackBar
  ) { }

  ngOnInit() {
  }
  
  onDeleteSubmit() {
    this.scheduleService.deleteScheduleOld(+this.delModel.age, this.delModel.password)
      .subscribe(response => {
        console.log(response);
        this.deleteSubmitted = true;
        var respBody = JSON.parse(response.body);
        if (respBody.httpCode == 202) {
          // TODO set numDeleted to the actual response number
          this.numDeleted = 1839;
        } else if (+respBody.httpCode > 400) {
          this.snackbar.open('Error, invalid request', 'DISMISS');
        }
        this.deleteFinished = true;
      });
    console.log(this.delModel);
  }
  
  onRetrieveSubmit() {
    this.scheduleService.reportActivity(+this.retModel.age, this.retModel.password)
      .subscribe(response => {
        console.log(response);
        this.retrieveSubmitted = true;
        var respBody = JSON.parse(response.body);
        if (respBody.httpCode == 200) {
          // TODO display the actual response schedules
          this.scheds = new Array<IDSC>(3);
          this.scheds[0] = new IDSC('111111', 'aaaaaa');
          this.scheds[1] = new IDSC('222222', 'bbbbbb');
          this.scheds[2] = new IDSC('333333', 'cccccc');
        } else if (+respBody.httpCode > 400) {
          this.snackbar.open('Error, invalid request', 'DISMISS');
        }
        this.retrieveFinished = true;
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
