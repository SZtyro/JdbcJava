import { BasicForm } from 'src/app/ts/basicForm';
import { Component, OnInit } from '@angular/core';
import { FieldType } from 'src/app/ts/enums/fieldType';
import { ToastType } from 'src/app/ts/enums/toastType';

@Component({
  selector: 'app-company',
  templateUrl: './../../html/basicForm.html'
})
export class CompanyComponent extends BasicForm implements OnInit {

  company = {};


  ngOnInit(): void {
    this.name = "company";
    this.fields = [
      { name: 'name', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-4', value: this.company['name'], required: true },
      { name: 'nip', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-4', value: this.company['nip'], required: true },
      { name: 'address', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-4', value: this.company['address'] },
      { name: 'city', type: FieldType.INPUT, class: 'col-12 col-md-6 col-lg-4', value: this.company['city'] },
    ]

    this.actions = [
      { name: 'save', id: 'save' }
    ]

    this.route.data.subscribe(data => {
      this.company = data.company;
      this.fields.forEach(el => {
        el.value = this.company[el.name];
      })
    })
  }

  onAction(actionId: any) {
    switch (actionId) {
      case 'save':
        if (this.route.snapshot.params['id'] != 0)
          this.http.updateCompany(this.createBodyFromFields(this.company)).subscribe(
            () => {
              this.shared.newToast({
                message: 'toasts.company.saved'
              })
              window.location.href = '/home'
            },
            err => {
              this.shared.newToast({
                message: err.error.message,
                type: ToastType.ERROR
              })
            }
          )
        else
          this.http.createCompany(this.createBodyFromFields(this.company)).subscribe(
            () => {
              this.shared.newToast({
                message: 'toasts.company.saved'
              })
              window.location.href = '/home'
            },
            err => {
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
}
