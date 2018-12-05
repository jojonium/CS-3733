import { Component, OnInit, Inject } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { CreateScheduleRequest } from './create-schedule-request';
import { ScheduleService } from '../schedule.service';
import { CreateScheduleResponse } from './create-schedule-response';
import { ViewWeeklyScheduleComponent } from '../view-weekly-schedule/view-weekly-schedule.component';

@Component({
  selector: 'app-create-schedule',
  templateUrl: './create-schedule.component.html',
  styleUrls: ['./create-schedule.component.css']
})
export class CreateScheduleComponent implements OnInit {
  heading = 'Create new schedule';
  displaySecretCode = '';
  displayID = '';

  progressBarValue = 0;
  progressBarMode = '';

  durations = [
    '10 minutes',
    '15 minutes',
    '20 minutes',
    '30 minutes',
    '60 minutes',
  ];

  model = new CreateScheduleRequest("", "", "", "", "", "");
  submitted = false;

  openFailDialog(): void {
    const dialogRef = this.failDialog.open(DialogFailure, {
      width: '400px',
    });
  };

  openSuccessDialog(sc: string, sid: string): void {
    const dialogRef = this.successDialog.open(DialogSuccess, {
      width: '400px',
      data: {secretCode: sc, scheduleID: sid}
    });
  }

  onSubmit() {
    console.log(`in CreateScheduleComponent.onSubmit()`);

    this.submitted = true;
    this.progressBarMode = 'indeterminate';
    if (this.model.isEmpty()) { return; }

    // make a new CreateScheduleRequest so we can parse the dates without screwing up
    // the date picker inputs
    var tempDate = new Date(this.model.startDate);
    var parsedStartDate = ((tempDate.getMonth() + 1) + '/' + tempDate.getDate() + '/' + tempDate.getFullYear());
    tempDate = new Date(this.model.endDate);
    var parsedEndDate = ((tempDate.getMonth() + 1) + '/' + tempDate.getDate() + '/' + tempDate.getFullYear());
    var modelToSend = new CreateScheduleRequest(this.model.name, this.model.duration, parsedStartDate, parsedEndDate, this.model.startTime, this.model.endTime);

    this.scheduleService.createSchedule(modelToSend)
      .subscribe(csResponse => {
        console.log(`CreateScheduleComponent received response: ${csResponse}`);
        this.progressBarMode = '';
        var responseBody = JSON.parse(csResponse.body);
        if (responseBody.httpCode == 201) {
          console.log("RESPONSE BODY: sc: " + responseBody.secretCode + " sid: " + responseBody.id);
          this.openSuccessDialog(responseBody.secretCode, responseBody.id);
          // success
        } else if (responseBody.httpCode == 400) {
          this.openFailDialog();
        }
        this.submitted = false;
      });
  }

  weekendFilter = (d: Date): boolean => {
    const day = d.getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0 && day !== 6;
  }

  constructor(
    private scheduleService: ScheduleService,
    public failDialog: MatDialog,
    public successDialog: MatDialog
  ) { }

  ngOnInit() {
  }

}


export interface SuccessDialogData {
  secretCode: string;
  scheduleID: string;
}


@Component({
  selector: 'dialog-failure',
  templateUrl: 'dialog-failure.html',
})
export class DialogFailure {
  constructor(
    public dialogRef: MatDialogRef<DialogFailure>,
  ) { }

  closeFailDialog(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'dialog-success',
  templateUrl: 'dialog-success.html',
})
export class DialogSuccess {
  constructor(
    public dialogRef: MatDialogRef<DialogSuccess>,
    @Inject(MAT_DIALOG_DATA) public data: SuccessDialogData
  ) { }

  closeSuccessDialog(): void {
    this.dialogRef.close();
  }
}
