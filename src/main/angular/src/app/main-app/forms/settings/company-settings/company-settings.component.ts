import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';

@Component({
  selector: 'app-company-settings',
  templateUrl: './company-settings.component.html',
  styleUrls: ['./company-settings.component.scss']
})
export class CompanySettingsComponent implements OnInit {
  name;
  nip;

  constructor(
    private http: HttpClientService
  ) { }

  ngOnInit(): void {
  }

  saveCompany() {
    this.http.saveCompany({
      name: this.name,
      nip: this.nip
    }).subscribe(success => {
      console.log(success)
    })
  }
}
