import { ActivatedRoute } from '@angular/router';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {

  institutions;
  form = {
    mail: null,
    institution_id: null
  }

  constructor(
    private http: HttpClientService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.institutions = data.institutions;
    })
  }


  inviteUser() {
    this.http.inviteUser(this.form).subscribe(success => {
      console.log(success);
    }, err => console.log(err))
  }
}
