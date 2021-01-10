import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {

  institutions;
  user;

  constructor(
    private http: HttpClientService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.institutions = data.institutions;
      this.user = data.employee;

      console.log(this.user)
    })

    console.log(this.route.url)
  }


  inviteUser() {
    //if(this.route.fragment)
    this.http.updateUser(this.user).subscribe(success => {
      console.log(success);
      this.router.navigate(['employees','list'])
    }, err => console.log(err))

  }

  compareId(obj1, obj2) {
    console.log(obj1)
    console.log(obj2)
    return obj1 === obj2;
  }
}
