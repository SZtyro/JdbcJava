import { detailExpand } from '../../../ts/animations';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { BasicTable } from 'src/app/ts/basicTable';
import { buttonDelete } from 'src/app/ts/buttons';

@Component({
  selector: 'app-table-list',
  templateUrl: './../../../html/basicTable.html',
  animations: [detailExpand]
})
export class TableListComponent extends BasicTable implements OnInit {
  


  ngOnInit(): void {

    
    this.route.data.subscribe(data => {
      this.dataSource = new MatTableDataSource(data.tables)
      this.name = data.database['database'];
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

  onRowAction(actionId: any, row: any) {
    switch (actionId) {
      case 'list':
        this.router.navigate(['table', row.name],{relativeTo: this.route})
        break;

      default:
        break;
    }
  }

  onUnderAction(actionId: any) {
    throw new Error('Method not implemented.');
  }
}
