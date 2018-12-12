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
    public timeSlots: TimeSlot[],
    public hasPreviousWeek: string,
    public hasNextWeek: string,
    public trueStartDate: MyDate,
    public trueEndDate: MyDate,
    public httpCode: string
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

export class TimeSlot {
  constructor(
    public beginDateTime: {
      date: MyDate,
      time: MyTime,
    },
    public isOpen: boolean,
    public scheduleID: string,
    public requester?: string,
    public secretCode?: string
  ) { }
}
