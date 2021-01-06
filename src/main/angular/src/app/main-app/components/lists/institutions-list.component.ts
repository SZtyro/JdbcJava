import { detailExpand } from '../../ts/animations';
import { MatTableDataSource } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { BasicTable } from 'src/app/main-app/ts/basicTable';
import { trigger, state, style, transition, animate } from '@angular/animations';


@Component({
  selector: 'app-institutions-list',
  templateUrl: '../../html/basicTable.html',
  animations: [
    detailExpand
  ],
})
export class InstitutionsListComponent extends BasicTable implements OnInit {
 


  ngOnInit(): void {
    this.name = "institutions";
    this.columns = ['name'];
    this.actions = [
      { id: "delete", icon: "delete", backgroundColor: "rgb(230,20,20)" },
      { id: "add", icon: "add", backgroundColor: "#11FF20" }
    ]
    this.route.data.subscribe(data => {
      this.dataSource = new MatTableDataSource(data.institutions);
    })
  }

  onRowClick() {
    throw new Error('Method not implemented.');
  }

  onAction(actionId) {
    console.log(actionId)
  }
}
