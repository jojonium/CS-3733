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
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { RouterModule, Routes } from '@angular/router'

import { AppComponent } from './app.component';
import { CreateScheduleComponent, DialogFailure, DialogSuccess } from './create-schedule/create-schedule.component';
import { ViewWeeklyScheduleComponent } from './view-weekly-schedule/view-weekly-schedule.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AppRoutingModule } from './app-routing.module';


@NgModule({
  declarations: [
    AppComponent,
    CreateScheduleComponent,
    DialogFailure,
    DialogSuccess,
    ViewWeeklyScheduleComponent,
    PageNotFoundComponent
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
    HttpClientModule,
    MatDialogModule,
    AppRoutingModule
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
