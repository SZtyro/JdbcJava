
import { detailExpand } from '../../ts/animations';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { DataSource } from '@angular/cdk/table';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { BasicTable } from 'src/app/ts/basicTable';
import { buttonEdit, buttonDelete } from '../../ts/buttons';

@Component({
  selector: 'app-employees-list',
  templateUrl: '../../html/basicTable.html',
  animations: [
    detailExpand
  ]
})
export class EmployeesListComponent extends BasicTable implements OnInit {



  ngOnInit(): void {
    //this.columns = ['mail', 'firstname', 'surname', 'institution'];
    this.columns = [
      { name: "mail" },
      { name: "firstname" },
      { name: "surname" },
      { name: "institution", assignValue: (row) => { return row.institution.name } },
    ]
    this.extractColumnNames();
    this.name = "employees";
    this.actions = [
      buttonEdit,
      buttonDelete
    ]
    this.route.data.subscribe(data => {
      console.log(data)
      this.dataSource = new MatTableDataSource(data.employees);

    })
  }


  onRowAction(actionId: any, row) {
    switch (actionId) {
      case buttonEdit.id:
        this.router.navigate(['employees', row.mail])
        break;
      case buttonDelete.id:
        this.http.deleteInstitution(row.institutionId).subscribe(
          () => {
            let i = this.dataSource.data.indexOf(row);
            this.dataSource.data.splice(i, 1)
            this.dataSource._updateChangeSubscription();
          },
          err => console.log(err)
        )
        break;
      default:
        break;
    }
  }

  onUnderAction(actionId: any) {
    throw new Error('Method not implemented.');
  }
}
