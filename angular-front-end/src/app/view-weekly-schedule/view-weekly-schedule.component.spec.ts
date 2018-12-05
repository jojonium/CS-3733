import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewWeeklyScheduleComponent } from './view-weekly-schedule.component';

describe('ViewWeeklyScheduleComponent', () => {
  let component: ViewWeeklyScheduleComponent;
  let fixture: ComponentFixture<ViewWeeklyScheduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewWeeklyScheduleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewWeeklyScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
