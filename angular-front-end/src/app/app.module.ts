import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';
import { ShowOnDirtyErrorStateMatcher } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { AmazingTimePickerModule } from 'amazing-time-picker';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';


import { AppComponent } from './app.component';
import { CreateScheduleComponent, DialogFailure, DialogSuccess } from './create-schedule/create-schedule.component';


@NgModule({
  declarations: [
    AppComponent,
    CreateScheduleComponent,
    DialogFailure,
    DialogSuccess
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatProgressBarModule,
    AmazingTimePickerModule,
    HttpClientModule,
    MatDialogModule
  ],
  entryComponents: [
    DialogFailure,
    DialogSuccess
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
