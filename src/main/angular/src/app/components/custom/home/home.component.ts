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

  num: number = 0;




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

      if(data.companies.length == 0){
        this.router.navigate(['/settings/company']);
      }

      this.notifications = data.notifications;
      this.notificationsDataSource = new MatTableDataSource(this.notifications);
      console.log(this.notificationsDataSource)
    })
  }

  setTableNames(data) {
    this.tableNames = data;

  }

  openTable(name) {
    this.router.navigate(['/home']);
    //this.router.navigate(['/table',name]);

  }
}
