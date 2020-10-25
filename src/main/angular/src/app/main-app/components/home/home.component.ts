import { Component, OnInit, ElementRef, Injector, Inject, ViewContainerRef, ViewChild, ComponentFactoryResolver, AfterViewInit, Type, ComponentRef } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClientService } from '../../../services/http-client.service';
import { GridsterConfig, GridsterItemComponent, GridsterItemComponentInterface } from 'angular-gridster2';
import { GmailWidgetComponent } from '../../widgets/gmail-widget/gmail-widget.component';
import { ChartWidgetComponent } from '../../widgets/chart-widget/chart-widget.component';
import { ScriptLoaderService } from 'angular-google-charts';
import { HomeWidget } from '../../interfaces/homeWidget';
import { SharedService } from '../../../services/Shared/shared.service';
import { trigger, state, transition, animate, style } from '@angular/animations';
import { AuthService } from 'src/app/services/Auth/auth.service';

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

  public options: GridsterConfig = {
    pushItems: true,
    displayGrid: 'none',
    minCols: 14,
    maxCols: 14,
    minRows: 7,
    fixedRowHeight: 100,
    fixedColWidth: 100,
    itemResizeCallback: this.resize.bind(this),
    itemChangeCallback: this.changed.bind(this),
    setGridSize: true,
    mobileBreakpoint: 0,
    gridType: 'scrollVertical',
    resizable: {
      enabled: false
    },
    draggable: {
      enabled: false
    }
  }
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


  appWidgets = {
    'GmailWidgetComponent': GmailWidgetComponent,
    'ChartWidgetComponent': ChartWidgetComponent
  }


  constructor(
    private httpClientService: HttpClientService,
    private router: Router,
    private shared: SharedService,
    private auth: AuthService
  ) {
    // try{
    //   if(!auth.isSignedIn())
    //   this.auth.signIn();
    // }catch(Excepiton){
    //   router.navigate([''])
    // }

    // this.auth.getUserData().subscribe((data)=>{
    //   //auth.getAuthInstance().current;
    //   //console.log(auth.getAuthInstance().isSignedIn.ie);


    // })
    shared.homeRef = this;
    //this.items = [GmailWidgetComponent]
    this.items = [];
    this.loadWidgets();

    this.shared.getEditGrid().subscribe(isEditing => {
      if (!isEditing)
        this.save();
    })


  }

  deleteWidget(i) {
    //this.loaderRef.remove(this.widgetNumber);
    //this.shared.homeRef.items.splice(this.widgetNumber, 1);
    //this.subscription.unsubscribe();
    this.shared.homeRef.items.splice(i, 1);
    //localStorage.removeItem('ChartWidget' + this.widgetNumber);
    this.shared.homeRef.items.forEach((elem, index) => {
      elem.index = index;
      elem.componentRef.instance["widgetNumber"] = index;
      //ref.instance["widgetNumber"] = index
      //elem.data.widgetNumber = index;
      //console.log(index)
    })
    //console.log("Usunieto: ChartWidget" + this.widgetNumber);
    //console.log(this.shared.homeRef.items);

    //this.shared.homeRef.save();
    //this.shared.homeRef.loadWidgets();
  }

  loadWidgets() {
    this.items = [];
    //let acc = [];
    //this.items = JSON.parse(localStorage.getItem('desktopWidgets'));
    console.log()
    var userMail;
    this.auth.getCurrentUser().then(user => {
      userMail = user.getBasicProfile().getEmail();
      this.httpClientService.getDashboard().subscribe((dashboard) => {
        try {
          this.items = JSON.parse(dashboard);
        } catch{

        }


      })
    })

  }
  save() {
    this.items.forEach(elem => {
      try{
        console.log(this.items)
        elem.componentRef.instance.toSave();
      }catch{

      }

      elem.componentRef = null;
    })
    let jsonStorage = JSON.stringify(this.items);
    let mail;
    this.auth.getCurrentUser().then(user => {
      mail = user.getBasicProfile().getEmail();
      console.log(mail)
      this.httpClientService.setDashboard(jsonStorage).subscribe((d) => { console.log(d) });
    });

    console.log("Storage save")

  }


  ngOnInit() {
    // this.httpClientService.getTableNames().subscribe(
    //   data => {

    //     this.setTableNames(data);
    //     console.log("Table names fetched! ", data);


    //   },

    //   error => {

    //     console.log("Error", error);

    //   }
    // )


  }

  setTableNames(data) {
    this.tableNames = data;

  }

  openTable(name) {
    this.router.navigate(['/home']);
    //this.router.navigate(['/table',name]);

  }
}
