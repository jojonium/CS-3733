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

}

export class Model {
  constructor(
    age: string,
    password: string
  ) { }
}
