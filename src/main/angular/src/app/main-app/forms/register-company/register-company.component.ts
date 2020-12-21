import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientService } from './../../../services/http-client.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register-company',
  templateUrl: './register-company.component.html',
  styleUrls: ['./register-company.component.scss']
})
export class RegisterCompanyComponent implements OnInit {


  activeLink;
  content;

  links = [
    'company', 'user'
  ]

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.url.subscribe(params => {
      this.activeLink = window.location.pathname.split('/settings/')[1]
    })
  }



  setActiveType(type) {
    this.router.navigate(['settings/' + type])
  }
}
