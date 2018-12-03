import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { CreateScheduleRequest } from './create-schedule-request';
import { CreateScheduleResponse } from './create-schedule-response';

@Injectable({
  providedIn: 'root'
})
export class CreateScheduleService {

  constructor(
    private http: HttpClient
  ) { }

  private createScheduleUrl = "https://24f2jgxv5i.execute-api.us-east-2.amazonaws.com/Alpha/createschedule";

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  /* POSTs a new schedule to the server */
  createSchedule(csRequest: CreateScheduleRequest): Observable<CreateScheduleResponse> {
    console.log('CreateScheduleService.createSchedule(): Attempting to send...');
    console.log(csRequest);
    console.log(JSON.stringify(csRequest));

    return this.http.post<CreateScheduleResponse>(this.createScheduleUrl, csRequest, this.httpOptions).pipe(
      tap((csResponse: CreateScheduleResponse) => {
        console.log('received csResponse:');
        console.log(csResponse);
      }),
      catchError(this.handleError<CreateScheduleResponse>('createSchedule')));

  }




  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
