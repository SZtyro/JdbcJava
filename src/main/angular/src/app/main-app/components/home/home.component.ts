import { Component, OnInit, ElementRef, Injector, Inject, ViewContainerRef, ViewChild, ComponentFactoryResolver, AfterViewInit, Type, ComponentRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClientService } from '../../../services/http-client.service';
import { GridsterConfig, GridsterItemComponent, GridsterItemComponentInterface } from 'angular-gridster2';
import { GmailWidgetComponent } from '../../widgets/gmail-widget/gmail-widget.component';
import { ChartWidgetComponent } from '../../widgets/chart-widget/chart-widget.component';
import { ScriptLoaderService } from 'angular-google-charts';
import { HomeWidget } from '../../interfaces/homeWidget';
import { SharedService } from '../../../services/Shared/shared.service';

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


  tableNames: String[] = [];
  opened: boolean = false;

  num: number = 0;

  public items: item[] = [];

  resize(x: HomeWidget) {
    x.onResize();
  }

  changed(x: HomeWidget) {
    // console.log(itemComponent);
    x.onChange();
  }

  deleteAllWidgets() {
    this.items = [];
    localStorage.removeItem('desktopWidgets');
  }

  constructor(
    private httpClientService: HttpClientService,
    private router: Router,
    private shared: SharedService,
    private route: ActivatedRoute
  ) {

    shared.homeRef = this;

  }


  ngOnInit() {
    this.route.data.subscribe(data => {
      console.log(data.companies)

      if(data.companies.length == 0){
        this.router.navigate(['/settings/company']);
      }
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
