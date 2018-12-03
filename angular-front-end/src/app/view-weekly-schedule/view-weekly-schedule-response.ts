export class ViewWeeklyScheduleResponse {
  constructor(
    public body: string,
    public headers?: any
  ) {  }
}

export class ViewWeeklyScheduleResponseBody {
  constructor(
    public name: string,
    public startDate: string,
    public endDate: string,
    public startTime: string,
    public endTime: string,
    public duration: string,
    public scheduleID: string,
    public timeSlots: TimeSlot[],
    public httpCode: string
  ) { }
}

export interface TimeSlot {
  beginDateTime: {
    date: {
      year: number,
      month: number,
      day: number
    },
    time: {
      hour: number,
      minute: number,
      second: number,
      nano: number,
    }
  },
  isOpen: boolean
}
