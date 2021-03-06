import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { CreateScheduleComponent, DialogFailure, DialogSuccess } from './create-schedule/create-schedule.component';
import { ViewWeeklyScheduleComponent } from './view-weekly-schedule/view-weekly-schedule.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ViewScheduleFormComponent } from './view-schedule-form/view-schedule-form.component';
import { DeletedComponent } from './deleted/deleted.component';
import { AboutComponent } from './about/about.component';
import { AdminComponent } from './admin/admin.component';
import { LicenseComponent } from './license/license.component';
import { SearchComponent } from './search/search.component';

const appRoutes: Routes = [
  { path: 'schedule/:id/:date', component: ViewWeeklyScheduleComponent },
  { path: 'schedule/:id', component: ViewWeeklyScheduleComponent },
  { path: 'schedule', component: ViewScheduleFormComponent },
  { path: 'search/:id', component: SearchComponent },
  { path: 'about', component: AboutComponent },
  { path: 'deleted', component: DeletedComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'license', component: LicenseComponent },
  { path: '', pathMatch: 'full', component: CreateScheduleComponent },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(
      appRoutes
    ),
    CommonModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
