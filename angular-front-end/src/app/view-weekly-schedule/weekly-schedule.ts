export class WeeklySchedule {
  constructor(
    public name: string,
    public duration: number,
    public scheduleID: string,
    public timeSlots: TimeSlot[]
  ) { }
}

export class TimeSlot {
  constructor(
    public scheduleID: string,
    public beginDateTime: string,
    public requester?: string,
    public secretCode?: string
  ) { }
}
