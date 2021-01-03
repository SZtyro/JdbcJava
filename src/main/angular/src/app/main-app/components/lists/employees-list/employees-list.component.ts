import { DataSource } from '@angular/cdk/table';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { BasicTable } from 'src/app/main-app/ts/basicTable';

@Component({
  selector: 'app-employees-list',
  templateUrl: '../../../html/basicTable.html',
  styleUrls: ['./employees-list.component.scss']
})
export class EmployeesListComponent extends BasicTable implements OnInit {

  ngOnInit(): void {
    this.columns = ['mail']
    this.route.data.subscribe(data => {
      console.log(data)
      this.dataSource = new MatTableDataSource(data.employees);
      
    })
  }

}
