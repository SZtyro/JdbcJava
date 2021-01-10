import { Component, OnInit } from '@angular/core';
import { FieldType } from '../components/enums/fieldType';
import { BasicForm } from '../ts/basicForm';

@Component({
  selector: 'app-invite-user',
  templateUrl: './../html/basicForm.html'
})
export class InviteUserComponent extends BasicForm implements OnInit {

  institutions;

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.institutions = data.institutions;
    })

    this.name = "invite_user";
    this.fields = [
      { name: 'mail', type: FieldType.INPUT, class: 'col-12 col-md-6' },
      {
        name: 'institution', type: FieldType.SELECT, class: 'col-12 col-md-6',
        options: this.institutions.map(el => {
          return {
            name: el.name,
            value: el.id
          }
        })
      },
    ]

    this.actions = [
      { id: 'invite', icon: 'invite' }
    ]
  }

  onAction(actionId: any) {
    switch (actionId) {
      case 'invite':
        console.log(this.fields)
        this.http.inviteUser({
          mail: this.fields[0].value,
          institutionId: this.fields[1].value
        }).subscribe(
          () => { this.router.navigate(['employees', 'list']) }),
          err => { console.log(err) }
        break;

      default:
        break;
    }
  }

}
