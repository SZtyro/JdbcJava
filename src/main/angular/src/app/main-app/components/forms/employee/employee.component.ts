import { SharedService } from 'src/app/services/shared.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Component, OnInit, Type } from '@angular/core';
import { ToastType } from '../../../ts/enums/toastType';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {

  institutions;
  user;

  institution: any;

  constructor(
    private http: HttpClientService,
    private route: ActivatedRoute,
    private router: Router,
    private shared: SharedService
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.institutions = data.institutions;
      this.user = data.employee;
    })

  }


  inviteUser() {
    //if(this.route.fragment)
    this.http.updateUser(this.user).subscribe(success => {
      this.shared.newToast({
        message: "notification.user.updated",
        params: { mail: this.user.mail }
      })
      this.router.navigate(['employees', 'list'])
    }, err => {
      console.log(err)
      this.shared.newToast({
        message: err.error.message,
        type: ToastType.ERROR
      })
    })

  }

  compareId(obj1, obj2) {
    console.log(obj1)
    console.log(obj2)
    return obj1 === obj2;
  }
}
