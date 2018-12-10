import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  retModel = new Model('', '');
  delModel = new Model('', '');
  retrieveSubmitted = false;
  deleteSubmitted = false;
  
  constructor() { }

  ngOnInit() {
  }
  
  onDeleteSubmit() {
    // TODO implement
    console.log(this.delModel);
  }
  
  onRetrieveSubmit() {
    // TODO implement
    console.log(this.retModel);
  }

}

export class Model {
  constructor(
    public age: string,
    public password: string
  ) { }
}
