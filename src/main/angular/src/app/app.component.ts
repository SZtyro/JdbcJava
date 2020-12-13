import { Component, OnInit, AfterViewChecked, AfterContentChecked, AfterContentInit } from '@angular/core';
import { Router, RouterEvent, NavigationEnd } from '@angular/router';
import { HttpClientService } from './services/http-client.service';
//import { AuthService, SocialUser } from "angularx-social-login";
import { GoogleLoginProvider } from "angularx-social-login";
import { TranslateService } from '@ngx-translate/core';
import { WidgetListModalComponent } from './main-app/modals/widget-list-modal/widget-list-modal.component';
import { MatDialog } from '@angular/material';
import { SharedService } from './services/Shared/shared.service';
import { Observable } from 'rxjs';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { FunctionBase } from './modules/functionModules/functionBase';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [
    trigger('openClose', [
      // ...
      state('open', style({
        width: '200px',
      })),
      state('closed', style({
        width: '50px',
      })),
      transition('open => closed', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
      ]),
      transition('closed => open', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
      ]),
    ]),
  ]
})
export class AppComponent implements OnInit, AfterContentInit {

  menuItems: FunctionBase[] = [
    { icon: 'home', menuTitle: 'NAVBAR.HOME', routerLink: 'home' },
    {
      icon: 'storage', menuTitle: 'Database', routerLink: 'databases', childs: [
        {
          icon: 'settings', menuTitle: 'Settings', routerLink: 'databases', childs: []
        },
        { icon: 'table_rows', menuTitle: 'Tables', routerLink: 'databases', childs: [] }
      ]
    },
    {
      icon: 'email', menuTitle: 'E-mail', routerLink: 'mail', childs: []
    },
    {
      icon: 'business', menuTitle: 'Company', routerLink: 'mail', childs: [
        { icon: 'store', menuTitle: 'Stores' },
        {
          icon: 'person', menuTitle: 'Employees', childs: [
            { icon: 'groups', menuTitle: 'Groups' },
            { icon: 'event_note', menuTitle: 'Shifts' },
            { icon: 'fact_check', menuTitle: 'Works' }
          ]
        },
        {
          icon: 'local_shipping', menuTitle: 'Vehicles', childs: []
        }
      ]
    },
    {
      icon: 'settings', menuTitle: 'NAVBAR.SETTINGS', childs: [

      ]
    }
  ];
  tableNames: String[] = [];
  dbConnection: boolean = false;
  opened: boolean = false;
  sideNavExtension: boolean = false;
  //photoUrl: String;
  //currentNavigation;


  isSignedIn$: Observable<boolean>;
  isSignedIn: boolean;
  constructor(
    private httpClientService: HttpClientService,
    private router: Router,
    //private authService: AuthService,
    public translate: TranslateService,
    public dialog: MatDialog,
    public shared: SharedService
  ) {
    translate.addLangs(['en', 'pl']);
    translate.setDefaultLang('en');
    translate.use('en')
    //this.auth.getUserData().subscribe(userData => {this.photoUrl = userData.imageUrl})
    //console.log(this.auth.auth2.currentUser)


    console.log(this.menuItems);

  }

  signOut() {
    //this.auth.getUserData().subscribe().unsubscribe();
    this.router.navigate([''])
    //this.auth.signOut();
  }


  ngAfterContentInit(): void {
    //Called after ngOnInit when the component's or directive's content has been initialized.
    //Add 'implements AfterContentInit' to the class.
    this.subscribeDBConnection();
    this.subscribeLoggedUser();
    this.shared.getShowNavBar().subscribe((data) => {
      setTimeout(() => { this.opened = data });
    })
  }

  ngAfterContentChecked(): void {
    //Called after every check of the component's or directive's content.
    //Add 'implements AfterContentChecked' to the class.

  }

  async ngOnInit() {

  }

  subscribeLoggedUser() {
    this.shared.getIsUserLogged().subscribe(data => {
      this.isSignedIn = data;
      this.opened = data;
      console.log("opened: " + data)
    });
  }

  subscribeDBConnection() {
    this.shared.getdbConnnection().subscribe(data => {
      if (data) {
        //Fetching table names
        this.httpClientService.getTableNames().subscribe(
          data => {
            this.setTableNames(data);
            console.log("Home Table names fetched! ", data);
          },
          error => {
            console.log("Error", error);
          }
        )
      }
      this.dbConnection = data
    });
  }

  sideNavExtend() {
    this.sideNavExtension = !this.sideNavExtension;

    if (!this.sideNavExtension) {
      this.menuItems.forEach(item => {
        setTimeout(() => {
          item.isOpen = false;
        }, 10);
      });
    }
  }

  setTableNames(data) {
    this.tableNames = data;

  }

  editGrid() {
    this.shared.setEditGrid();
    this.shared.homeRef.options.draggable.enabled = !this.shared.homeRef.options.draggable.enabled;
    this.shared.homeRef.options.resizable.enabled = !this.shared.homeRef.options.resizable.enabled;
    this.shared.homeRef.options.api.optionsChanged();

  }

  openTable(name) {
    //this.router.navigate(['/home']);
    this.router.navigate(['/table', name]);

    this.router.onSameUrlNavigation = 'reload';
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(WidgetListModalComponent, {
      width: '80%',
      height: '80%',
      data: { father: this }

    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }
}
