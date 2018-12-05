export class CreateScheduleRequest {
  constructor(
    public name: string,
    public duration: string,
    public startDate: string,
    public endDate: string,
    public startTime: string,
    public endTime: string
  ) {  }

  isEmpty(): boolean {
    return !this.name || !this.duration || !this.startDate || !this.endDate || !this.startTime || !this.endTime;
  }
}
