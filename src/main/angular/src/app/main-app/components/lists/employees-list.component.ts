import { detailExpand } from '../../ts/animations';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { DataSource } from '@angular/cdk/table';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { BasicTable } from 'src/app/main-app/ts/basicTable';

@Component({
  selector: 'app-employees-list',
  templateUrl: '../../html/basicTable.html',
  animations: [
    detailExpand
  ]
})
export class EmployeesListComponent extends BasicTable implements OnInit {



  ngOnInit(): void {
    this.columns = ['mail', 'institution'];
    this.name = "employees"
    this.route.data.subscribe(data => {
      console.log(data)
      this.dataSource = new MatTableDataSource(data.employees);

    })
  }


  onRowClick() {
    throw new Error('Method not implemented.');
  }

  onAction(actionId: any) {
    console.log(actionId)
  }
}
