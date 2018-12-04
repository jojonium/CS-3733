import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-schedule-form',
  templateUrl: './view-schedule-form.component.html',
  styleUrls: ['./view-schedule-form.component.css']
})
export class ViewScheduleFormComponent implements OnInit {
  model = new ViewScheduleModel('', '');
  submitted = false;
  
  onSubmit(): void {
    this.submitted = true;
    if (this.model.date != '') {
      var tempDate = new Date(this.model.date);
      var parsedTempDate = ((tempDate.getMonth() + 1) + '-' + tempDate.getDate() + '-' + tempDate.getFullYear());
      this.router.navigate(['/schedule/' + this.model.id + '/' + parsedTempDate]);
    } else {
      this.router.navigate(['/schedule/' + this.model.id]);
    }
  }
  
  constructor(private router: Router) { }

  ngOnInit() {
  }

}


export class ViewScheduleModel {
  constructor(
    public id: string,
    public date: string
  ) { }
}
