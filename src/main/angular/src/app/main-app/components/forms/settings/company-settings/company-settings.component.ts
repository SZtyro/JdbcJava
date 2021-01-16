import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';

@Component({
  selector: 'app-company-settings',
  templateUrl: './company-settings.component.html',
  styleUrls: ['./company-settings.component.scss']
})
export class CompanySettingsComponent implements OnInit {

  company;

  constructor(
    private http: HttpClientService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      if (data.company) {
        this.company = data.company;
        if(this.company.id == 0)
          this.company.id = null;
      }
    })
  }

  saveCompany() {
    if (this.company.id == null || this.company.id == 0) {
      this.http.saveCompany(this.company).subscribe(
        success => {
          console.log(success)
          alert('Pomyślnie zapisano firmę')
        },
        err => {
          alert(err.error.message);
        })
    } else {
      this.http.updateCompany(this.company).subscribe(
        success => {
          console.log(success)
          alert('Pomyślnie zapisano firmę')
        },
        err => {
          alert(err.error.message);
        })
    }
  }

}
