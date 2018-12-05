import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewScheduleFormComponent } from './view-schedule-form.component';

describe('ViewScheduleFormComponent', () => {
  let component: ViewScheduleFormComponent;
  let fixture: ComponentFixture<ViewScheduleFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewScheduleFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewScheduleFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
