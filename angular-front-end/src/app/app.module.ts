import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';
import { ShowOnDirtyErrorStateMatcher } from '@angular/material';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { RouterModule, Routes } from '@angular/router'
import { MatGridListModule } from '@angular/material/grid-list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';


import { AppComponent } from './app.component';
import { CreateScheduleComponent, DialogFailure, DialogSuccess } from './create-schedule/create-schedule.component';
import { ViewWeeklyScheduleComponent, CreateMeetingDialog, OpenCloseAllDialog } from './view-weekly-schedule/view-weekly-schedule.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AppRoutingModule } from './app-routing.module';
import { ViewScheduleFormComponent } from './view-schedule-form/view-schedule-form.component';
import { DeletedComponent } from './deleted/deleted.component';
import { AboutComponent } from './about/about.component';
import { AdminComponent } from './admin/admin.component';


@NgModule({
  declarations: [
    AppComponent,
    CreateScheduleComponent,
    DialogFailure,
    DialogSuccess,
    CreateMeetingDialog,
    OpenCloseAllDialog,
    ViewWeeklyScheduleComponent,
    PageNotFoundComponent,
    ViewScheduleFormComponent,
    DeletedComponent,
    AboutComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    MatCardModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatToolbarModule,
    MatGridListModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatExpansionModule,
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
    DialogSuccess,
    CreateMeetingDialog,
    OpenCloseAllDialog
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
