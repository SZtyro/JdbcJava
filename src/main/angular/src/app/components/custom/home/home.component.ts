import { GoogleColor } from './../../../ts/enums/googleColor';
import { Component, OnInit, ElementRef, Injector, Inject, ViewContainerRef, ViewChild, ComponentFactoryResolver, AfterViewInit, Type, ComponentRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientService } from '../../../services/http-client.service';
import { HomeWidget } from '../../../ts/interfaces/homeWidget';
import { SharedService } from '../../../services/shared.service';
import { MatTableDataSource } from '@angular/material';

export interface item {
  typeName: string,
  index?: number,
  data?,
  componentRef?: ComponentRef<HomeWidget>
}

@Component({
  selector: 'main-app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})



export class HomeComponent implements OnInit {

  notifications;
  notificationsDataSource;

  tableNames: String[] = [];
  opened: boolean = false;

  events;

  constructor(
    private httpClientService: HttpClientService,
    private router: Router,
    private shared: SharedService,
    private route: ActivatedRoute
  ) {


  }


  ngOnInit() {
    this.route.data.subscribe(data => {
      console.log(data.companies)

      if (data.companies.length == 0) {
        this.router.navigate(['/settings/company']);
      }


      this.notifications = data.notifications;
      this.notificationsDataSource = new MatTableDataSource(this.notifications);
      console.log(this.notificationsDataSource)
    })

    this.httpClientService.getCalendarEvents().subscribe(data => {
      this.events = data;
    })
  }

  getGoogleColor(colorId) {
    return GoogleColor[colorId];
  }

  extractDate(event) {
    let date: string = "";
    let today: Date = new Date();

    if (event.start.dateTime) {
      let s;
      let d1 = new Date(event.start.dateTime);
      let d2 = new Date(event.end.dateTime);

      if (d1.getDate() != today.getDate())
        s = d1.toLocaleString().slice(0, -3) + " - " + d2.toLocaleString().slice(0, -3);
      else
        s = d1.toLocaleTimeString().slice(0, -3) + " - " + d2.toLocaleTimeString().slice(0, -3);

      date += s;
    }
    else if (event.start.date) {
      let s = 'today';
      let d = new Date(event.start.date);

      if (d.getDate() != today.getDate())
        s = d.toLocaleDateString();


      date += s;
    }

    return date;
  }
}
