import { Module } from './ts/interfaces/module';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-plan',
  templateUrl: './plan.component.html',
  styleUrls: ['./plan.component.scss']
})
export class PlanComponent implements OnInit {

  summaryCount = 0;
  employeesCount = 20;

  extensions: Module[] = [
    { icon: 'storage', name: 'modules.title.database', description: 'modules.description.database', cost: 5 }
  ]

  plan: Module[] = [

  ]

  constructor() { }

  ngOnInit(): void {
  }

  cancelSubscription(extension) {
    let elem = this.plan.find(el => el === extension);
    this.summaryCount -= elem.cost;
    this.extensions.push(elem)
    this.plan.splice(this.plan.findIndex(el => el === extension), 1);
  }

  buy(extension) {
    let elem = this.extensions.find(el => el === extension);
    this.summaryCount += elem.cost;
    this.plan.push(elem)
    this.extensions.splice(this.extensions.findIndex(el => el === extension), 1);
  }

  save() {

  }
}
