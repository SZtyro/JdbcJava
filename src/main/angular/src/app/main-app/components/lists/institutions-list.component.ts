import { detailExpand } from '../../ts/animations';
import { MatTableDataSource } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { BasicTable } from 'src/app/main-app/ts/basicTable';


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
      { id: "edit", icon: "create" },
      { id: "delete", icon: "delete", backgroundColor: "var(--deleteColor)" }
    ]
    this.route.data.subscribe(data => {
      this.dataSource = new MatTableDataSource(data.institutions);
    })
  }

  onRowClick() {
    throw new Error('Method not implemented.');
  }

  onAction(actionId, row) {
    switch (actionId) {
      case 'edit':
        this.router.navigate(['institutions', row.id])
        break;
      case 'delete':
        this.http.deleteInstitution(row.id).subscribe(
          () => {
            let i = this.dataSource.data.indexOf(row);
            this.dataSource.data.splice(i,1)
            this.dataSource._updateChangeSubscription();
          },
          err => console.log(err)
        )
        break;
      default:
        break;
    }
  }
}
