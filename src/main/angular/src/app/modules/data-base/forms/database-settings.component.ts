import { Component, OnInit } from '@angular/core';
import { FieldType } from 'src/app/main-app/ts/enums/fieldType';
import { ToastType } from 'src/app/main-app/ts/enums/toastType';
import { BasicForm } from 'src/app/main-app/ts/basicForm';

@Component({
  selector: 'app-database-settings',
  templateUrl: './../../../main-app/html/BasicForm.html'
})
export class DatabaseSettingsComponent extends BasicForm implements OnInit {

  database;

  ngOnInit(): void {

    this.name = "settings"

    this.route.data.subscribe(data => {
      this.database = data.database;
    })

    this.fields = [
      { name: 'host', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-3', value: this.database.url },
      { name: 'port', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-3', value: this.database.port },
      { name: 'database', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-3', value: this.database.database },
      { name: 'user', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-3', value: this.database.login },
      { name: 'password', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-3', value: this.database.password },
    ]
    this.actions = [
      { name: 'save', id: 'save' }
    ]


  }

  onAction(actionId: any) {
    if (this.route.snapshot.params['id'] != 0) {
      this.http.database.updateDatabase(this.createBodyFromFields(this.database)).subscribe(
        () => {
          this.shared.newToast({
            message: 'toasts.database.updated'
          })
        },
        err => {
          this.shared.newToast({
            message: err.error.message,
            type: ToastType.ERROR
          })
        }
      )

    } else {
      this.http.database.createDatabase(this.createBodyFromFields(this.database)).subscribe(
        () => {
          this.shared.newToast({
            message: 'toasts.database.created'
          })
        },
        err => {
          this.shared.newToast({
            message: err.error.message,
            type: ToastType.ERROR
          })
        }
      )
    }
  }
}
