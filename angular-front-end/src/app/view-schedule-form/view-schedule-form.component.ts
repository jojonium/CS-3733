import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-schedule-form',
  templateUrl: './view-schedule-form.component.html',
  styleUrls: ['./view-schedule-form.component.css']
})
export class ViewScheduleFormComponent implements OnInit {
  model = ViewScheduleModel;
  submitted = false;
  
  onSubmit(): void {
    this.submitted = true;
    this.router.navigate(['/schedule/' + this.model.id]);
  }
  
  constructor(private router: Router) { }

  ngOnInit() {
  }

}


export class ViewScheduleModel {
  constructor(
    id: string
  ) { }
}
