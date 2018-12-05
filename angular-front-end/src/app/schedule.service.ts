import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { CreateScheduleRequest } from './create-schedule/create-schedule-request';
import { CreateScheduleResponse } from './create-schedule/create-schedule-response';
import { ViewWeeklyScheduleResponse } from './view-weekly-schedule/view-weekly-schedule-response';
import { CreateMeetingRequest, CreateMeetingResponse } from './view-weekly-schedule/view-weekly-schedule.component';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  constructor(
    private http: HttpClient
  ) { }

  private createScheduleUrl = "https://24f2jgxv5i.execute-api.us-east-2.amazonaws.com/Alpha/createschedule";
  private viewWeeklyScheduleUrl = "https://24f2jgxv5i.execute-api.us-east-2.amazonaws.com/Alpha/viewweeklyschedule";
  private createMeetingUrl = "https://24f2jgxv5i.execute-api.us-east-2.amazonaws.com/Alpha/createmeeting";


  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  /* POSTs a new schedule to the server */
  createSchedule(csRequest: CreateScheduleRequest): Observable<CreateScheduleResponse> {
    console.log('ScheduleService.createSchedule(): Attempting to send...');
    console.log(csRequest);

    return this.http.post<CreateScheduleResponse>(this.createScheduleUrl, csRequest, this.httpOptions).pipe(
      tap((csResponse: CreateScheduleResponse) => {
        console.log('received csResponse:');
        console.log(csResponse);
      }),
      catchError(this.handleError<CreateScheduleResponse>('createSchedule')));
  }
  
  /* POSTS a new meeting to the server */
  createMeeting(cmRequest: CreateMeetingRequest): Observable<CreateScheduleResponse> {
    console.log('ScheduleService.createMeeting(): Attempting to send...');
    console.log(cmRequest);
    
    return this.http.post<CreateMeetingResponse>(this.createMeetingUrl, cmRequest, this.httpOptions).pipe(
      tap((cmResponse: CreateMeetingResponse) => {
        console.log('received cmResponse:');
        console.log(cmResponse);
      }),
      catchError(this.handleError<CreateMeetingResponse>('createMeeting')));
  }

  
  /* GETs a schedule from the server */
  getSchedule(scheduleID: string, date: string | null): Observable<ViewWeeklyScheduleResponse> {
    console.log('ScheduleService.getSchdeule(): Attempting to send with scheduleID=' + scheduleID + ' and date=' + date);

    var parameters = {"scheduleID":scheduleID};
    if (date != null) parameters['date'] = date;

    console.log(parameters);
    
    return this.http.get<ViewWeeklyScheduleResponse>(this.viewWeeklyScheduleUrl, {
      params: parameters,
    }).pipe(
      tap((vwsResponse: ViewWeeklyScheduleResponse) => {
        console.log('received vwsResponse:');
        console.log(vwsResponse);
      }),
      catchError(this.handleError<ViewWeeklyScheduleResponse>('getSchedule')));
  }
  

    

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
