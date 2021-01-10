import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Component, OnInit } from '@angular/core';
import { BasicForm } from '../ts/basicForm';
import { FieldType } from '../components/enums/fieldType';
import { ToastType } from '../components/enums/toastType';

@Component({
  selector: 'app-institution-register',
  templateUrl: './../html/basicForm.html'
})
export class InstitutionRegisterComponent extends BasicForm implements OnInit {


  institution;

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.institution = data.institution;
    })
    this.name = "institution"
    this.fields = [
      { name: 'name', type: FieldType.INPUT, class: 'col-12 col-md-6', value: this.institution.name }
    ]

    this.actions = [
      { icon: 'save', id: 'save' }
    ]
  }

  saveInstitution() {
    this.institution.name = this.fields[0].value;
    this.http.updateInstitution(this.institution).subscribe(
      () => {
        this.shared.newToast({
          message: "notification.institution.updated",
          params: { name: this.institution.name }
        })
        this.router.navigate(['institutions', 'list'])
      },
      err => {
        this.shared.newToast({
          message: err.error.message,
          type: ToastType.ERROR
        })
        console.log(err)
      })
  }

  onAction(actionId: any) {
    switch (actionId) {
      case 'save':
        this.saveInstitution();
        break;

      default:
        break;
    }
  }
}
