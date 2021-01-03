import { Router } from '@angular/router';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-institution-register',
  templateUrl: './institution-register.component.html',
  styleUrls: ['./institution-register.component.scss']
})
export class InstitutionRegisterComponent implements OnInit {

  institution = { name: null };

  constructor(
    private http: HttpClientService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  saveInstitution() {
    this.http.updateInstitution(this.institution).subscribe(
      () => {
        this.router.navigate(['home'])
      },
      err => console.log(err))
  }
}
