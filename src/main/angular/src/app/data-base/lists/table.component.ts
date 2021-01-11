import { BasicTable } from 'src/app/main-app/ts/basicTable';
import { Component, OnInit } from '@angular/core';
import { detailExpand } from 'src/app/main-app/ts/animations';
import { MatTableDataSource } from '@angular/material';
import { buttonDelete, buttonEdit } from 'src/app/main-app/ts/buttons';

@Component({
  selector: 'app-table',
  templateUrl: './../../main-app/html/basicTable.html',
  animations: [detailExpand]
})
export class TableComponent extends BasicTable implements OnInit {

  ngOnInit(): void {

    this.name = this.route.snapshot.params['tableName'];
    this.route.data.subscribe(data => {
      this.dataSource = new MatTableDataSource()
    })
    this.columns = [
      { name: 'name' }
    ]
    this.extractColumnNames();
    this.actions = [
      buttonEdit,
      buttonDelete
    ]
  }

  onAction(actionId: any, row: any) {
    switch (actionId) {
      case buttonEdit.id:
        
        break;

      default:
        break;
    }
  }

}
