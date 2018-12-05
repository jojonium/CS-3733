import { TestBed } from '@angular/core/testing';

import { CreateScheduleService } from './create-schedule.service';

describe('CreateScheduleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreateScheduleService = TestBed.get(CreateScheduleService);
    expect(service).toBeTruthy();
  });
});
