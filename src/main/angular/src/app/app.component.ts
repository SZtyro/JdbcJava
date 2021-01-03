import { Component, OnInit, AfterViewChecked, AfterContentChecked, AfterContentInit } from '@angular/core';
import { Router, RouterEvent, NavigationEnd, ActivatedRoute } from '@angular/router';
import { HttpClientService } from './services/http-client.service';
//import { AuthService, SocialUser } from "angularx-social-login";
import { GoogleLoginProvider } from "angularx-social-login";
import { TranslateService } from '@ngx-translate/core';
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
    { icon: 'home', name: 'NAVBAR.HOME', routerLink: 'home' },
    {
      icon: 'storage', name: 'Database', routerLink: 'databases', childs: [
        {
          icon: 'settings', name: 'Settings', routerLink: 'databases', childs: []
        },
        { icon: 'table_rows', name: 'Tables', routerLink: 'databases', childs: [] }
      ]
    },
    {
      icon: 'email', name: 'E-mail', childs: []
    },
    {
      icon: 'business', name: 'Company', routerLink: 'mail', childs: [
        {
          icon: 'account_balance', name: 'Structures', childs: [
            { icon: 'store', name: 'Stores' },
            { icon: 'corporate_fare', name: 'Offices' },
            { icon: 'add_business', name: 'Add', routerLink: 'structures/0' }
          ]
        },
        {
          icon: 'person', name: 'Employees', childs: [
            { icon: 'person_add', name: 'employee_add', routerLink: 'employees/0'},
            { icon: 'groups', name: 'Groups' },
            { icon: 'event_note', name: 'Shifts' },
            { icon: 'fact_check', name: 'Tasks' }
          ]
        },
        {
          icon: 'local_shipping', name: 'Vehicles', childs: []
        }
      ]
    },
    {
      icon: 'settings', name: 'NAVBAR.SETTINGS', routerLink: 'settings/company', childs: [

      ]
    }
  ];
  companies: FunctionBase[] = [
    {
      icon: 'business', name: 'pick_company', childs: [
        {
          icon: 'add', name: 'add_company', childs: []
        }
      ]
    },

  ]
  languages: FunctionBase[] = [
    {
      icon: '<span class="text-icon">EN</span>', name: 'English', childs: [
        {
          icon: '<span class="text-icon">EN</span>', name: 'English', extras: { code: 'en' }
        },
        {
          icon: '<span class="text-icon">PL</span>', name: 'Polski', extras: { code: 'pl' }
        },
      ]
    }
  ]
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
    private route: ActivatedRoute,
    public translate: TranslateService,
    public dialog: MatDialog,
    public shared: SharedService
  ) {
    translate.addLangs(['en', 'pl']);
    translate.setDefaultLang('en');
    translate.use('en')
    //this.auth.getUserData().subscribe(userData => {this.photoUrl = userData.imageUrl})
    //console.log(this.auth.auth2.currentUser)


    this.httpClientService.getCompany().subscribe(companies => {
      companies.forEach(element => {
        this.companies[0].childs.push(element)
      });

    })
    this.httpClientService.getCurrentCompany().subscribe(current => {
      console.log(current)
      this.companies[0].name = current.name;
    })

  }

  signOut() {
    this.httpClientService.logout().subscribe(() => {
      this.router.navigate([''])
    }, err => console.log(err))

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
      this.companies.forEach(item => {
        setTimeout(() => {
          item.isOpen = false;
        }, 10);
      });
      this.languages.forEach(item => {
        setTimeout(() => {
          item.isOpen = false;
        }, 10);
      });
    }
  }

  setTableNames(data) {
    this.tableNames = data;

  }

  selectCompany(item) {
    if (item.id)
      this.httpClientService.setCurrentCompany(item.id).subscribe(current => {
        this.companies[0].name = current['name'];
      })
  }

  selectLanguage(language: FunctionBase) {
    this.languages[0].name = language.name;
    this.languages[0].icon = '<span class="text-icon">' + language.extras.code.toUpperCase() + '</span>';
    this.translate.use(language.extras.code)
  }


  openTable(name) {
    //this.router.navigate(['/home']);
    this.router.navigate(['/table', name]);

    this.router.onSameUrlNavigation = 'reload';
  }

  
}
