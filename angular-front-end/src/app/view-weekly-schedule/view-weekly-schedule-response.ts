export class ViewWeeklyScheduleResponse {
  constructor(
    public body: string,
    public headers?: any
  ) {  }
}

export class ViewWeeklyScheduleResponseBody {
  constructor(
    public name: string,
    public duration: number,
    public endDate: MyDate,
    public endTime: MyTime,
    public startDate: MyDate,
    public startTime: MyTime,
    public timeSlots: TimeSlot[]
  ) { }
}

export class MyDate {
  constructor(
    public year: number,
    public month: number,
    public day: number
  ) { }
}

export class MyTime {
  constructor(
    public hour: number,
    public minute: number,
    public second: number,
    public nano: number
  ) { }
}

export interface TimeSlot {
  beginDateTime: {
    date: MyDate,
    time: MyTime,
  },
  isOpen: boolean,
  scheduleID: string
}
