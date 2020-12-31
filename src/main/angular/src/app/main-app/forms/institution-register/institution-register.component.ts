import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-institution-register',
  templateUrl: './institution-register.component.html',
  styleUrls: ['./institution-register.component.scss']
})
export class InstitutionRegisterComponent implements OnInit {

  institution = { name: null };

  constructor() { }

  ngOnInit(): void {
  }

}
