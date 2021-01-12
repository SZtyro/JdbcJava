import { buttonPDFExport } from './../../main-app/ts/buttons';
import { BasicTable } from 'src/app/main-app/ts/basicTable';
import { Component, OnInit } from '@angular/core';
import { detailExpand } from 'src/app/main-app/ts/animations';
import { MatTableDataSource } from '@angular/material';
import { buttonDelete, buttonEdit } from 'src/app/main-app/ts/buttons';
import { TableContentComponent } from '../forms/table-content.component';

@Component({
  selector: 'app-table',
  templateUrl: './../../main-app/html/basicTable.html',
  animations: [detailExpand]
})
export class TableComponent extends BasicTable implements OnInit {

  metadata: Object[];

  ngOnInit(): void {

    this.name = this.route.snapshot.params['tableName'];
    this.route.data.subscribe(data => {
      this.dataSource = new MatTableDataSource(data.content);
      this.metadata = data.columns;
      this.columns = this.metadata.map(el => { return { name: el['name'] } })
    })
    this.underActions = [
      { icon: 'add', id: 'add', name: 'add' },
      buttonPDFExport
    ]

    this.extractColumnNames();
    this.actions = [
      buttonEdit,
      buttonDelete
    ]
  }

  onRowAction(actionId: any, row?: any) {
    switch (actionId) {
      case buttonEdit.id:

        break;

      default:
        break;
    }
  }

  onUnderAction(actionId: any) {
    switch (actionId) {
      case 'add':
        let dialogRef = this.dialog.open(TableContentComponent, {
          height: '80%',
          width: '80%',
          data: {
            metadata: this.metadata,
            name: this.name,
            tableName: this.name,
            exist: false
          }
        });

        break;

      default:
        break;
    }
  }

}
