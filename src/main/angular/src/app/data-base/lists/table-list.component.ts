import { detailExpand } from './../../main-app/ts/animations';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { BasicTable } from 'src/app/main-app/ts/basicTable';
import { buttonDelete } from 'src/app/main-app/ts/buttons';

@Component({
  selector: 'app-table-list',
  templateUrl: './../../main-app/html/basicTable.html',
  animations: [detailExpand]
})
export class TableListComponent extends BasicTable implements OnInit {


  ngOnInit(): void {

    this.name = this.route.snapshot.params['database'];
    this.route.data.subscribe(data => {
      this.dataSource = new MatTableDataSource(data.tables)
    })
    this.columns = [
      { name: 'name' }
    ]
    this.extractColumnNames();
    this.actions = [
      { icon: 'list', id: 'list' },
      buttonDelete
    ]
  }

  onAction(actionId: any, row: any) {
    switch (actionId) {
      case 'list':
        this.router.navigate(['table', row.name],{relativeTo: this.route})
        break;

      default:
        break;
    }
  }
}
