import { Component, OnInit } from '@angular/core';
import { FieldType } from 'src/app/main-app/components/enums/fieldType';
import { ToastType } from 'src/app/main-app/components/enums/toastType';
import { BasicFormDialog } from 'src/app/main-app/ts/basicFormDialog';

@Component({
  selector: 'app-table-content',
  templateUrl: './../../main-app/html/BasicForm.html'
})
export class TableContentComponent extends BasicFormDialog implements OnInit {

  ngOnInit(): void {
    this.fields = []
    this.name = this.data.name;
    this.data.metadata.forEach(element => {

      this.fields.push(
        
        { name: element.name, type: this.mapType(element.dataType), class: 'col-12 col-lg-6 col-xl-4', required: !element.nullable, disabled: element.autoIncrement }
      )
    });

    this.actions = [
      { name: 'save', id: 'save', icon: 'save' }
    ]
  }

  wrapBody() {

    this.data.metadata.forEach(element => {
      let e = this.fields.find(field => field.name == element.name)
      if (e) {
        element['value'] = e.value || e.value != "" ? (e.type == FieldType.DATE ? new Date(e.value).getTime() : e.value) : null;
      } else {
        element['value'] = null;
      }
    });

    return this.data.metadata;
  }

  onAction(actionId: any) {
    switch (actionId) {
      case 'save':
        if (!this.data.exist)
          this.http.saveRow(this.wrapBody(), this.data.tableName).subscribe(
            () => {
              this.shared.newToast({
                message: "notification.database.inserted"
              })
              this.dialogRef.close();
              this.router.navigateByUrl(this.router.url);
            }, err => {
              this.shared.newToast({
                message: err.error.message,
                type: ToastType.ERROR
              })
            }
          )
        break;

      default:
        break;
    }
  }

  mapType(type) {
    switch (type) {
      case 'int':
        return FieldType.INPUT;

      case 'varchar':
        return FieldType.INPUT

      case 'text':
        return FieldType.TEXTAREA

      case 'date':
        return FieldType.DATE

      default:
        break;
    }
  }
}
