import { BasicTable } from 'src/app/ts/basicTable';
import { Component, OnInit } from '@angular/core';
import { detailExpand } from 'src/app/ts/animations';
import { MatTableDataSource } from '@angular/material';
import { ToastType } from 'src/app/ts/enums/toastType';

@Component({
  selector: 'app-compay-list',
  templateUrl: '../../html/basicTable.html',
  animations: [
    detailExpand
  ]
})
export class CompanyListComponent extends BasicTable implements OnInit {

  ngOnInit(): void {
    this.name = 'select_company';
    this.columns = [
      {name: 'name'},
      {name: 'city'},
      {name: 'address'}
    ]
    this.extractColumnNames();
    this.rowStyle = 'cursor: pointer;'

    this.route.data.subscribe(data => {
      this.dataSource = new MatTableDataSource(data.companies);
      this.dataSource.paginator = this.paginator;

      console.log(this.dataSource)
    })
  }

  onRowClick(row){
    this.http.setCurrentCompany(row.id).subscribe(
      () => {
        window.location.href = '/home';
      },
      err => {
        this.shared.newToast({
          message: err.error.message,
          type: ToastType.ERROR
        })
      }
    )
  }

  onRowAction(actionId: any, row: any) {
    throw new Error('Method not implemented.');
  }
  onUnderAction(actionId: any) {
    throw new Error('Method not implemented.');
  }
}
