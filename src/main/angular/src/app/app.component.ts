import { openClose, fadeIn } from './main-app/ts/animations';
import { Component, OnInit, AfterViewChecked, AfterContentChecked, AfterContentInit } from '@angular/core';
import { Router, RouterEvent, NavigationEnd, ActivatedRoute } from '@angular/router';
import { HttpClientService } from './services/http-client.service';
//import { AuthService, SocialUser } from "angularx-social-login";
import { GoogleLoginProvider } from "angularx-social-login";
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material';
import { SharedService } from './services/shared.service';
import { Observable } from 'rxjs';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { FunctionBase } from './main-app/ts/interfaces/functionBase';
import { ToastType } from './main-app/ts/enums/toastType';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [
    openClose,
    fadeIn
  ]
})
export class AppComponent implements OnInit, AfterContentInit {

  menuItems: FunctionBase[] = [
    { icon: 'home', name: 'NAVBAR.HOME', routerLink: 'home' },
    {
      icon: 'storage', name: 'Database', childs: [
        { icon: 'add', name: 'add', routerLink: 'databases/0/settings', childs: [] }
      ]
    },
    {
      icon: 'email', name: 'E-mail', childs: []
    },
    {
      icon: 'business', name: 'Company', childs: [
        {
          icon: 'account_balance', name: 'Structures', childs: [
            { icon: 'store', name: 'institutions', routerLink: 'institutions/list' },
            { icon: 'add_business', name: 'Add', routerLink: 'institutions/0' }
          ]
        },
        {
          icon: 'person', name: 'Employees', childs: [
            { icon: 'list', name: 'employee_list', routerLink: 'employees/list' },
            { icon: 'person_add', name: 'employee_add', routerLink: 'employees/invite' },
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
    },
    { icon: 'monetization_on', name: 'finances' },
    { icon: 'view_module', name: 'modules' },
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
  opened: boolean = true;
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


    this.httpClientService.getCompany().subscribe(
      companies => {
        companies.forEach(element => {
          this.companies[0].childs.push(element)
        });

      },
      err => {
        console.error(err)
        this.shared.newToast({
          message: err.error.message, type: ToastType.ERROR
        })
      }
    )
    this.httpClientService.getCurrentCompany().subscribe(
      current => {
        console.log(current)
        this.companies[0].name = current.name;
      },
      err => {
        console.error(err)
        this.shared.newToast({
          message: err.error.message, type: ToastType.ERROR
        })
      })

    this.httpClientService.database.getDatabases().subscribe(
      databases => {
        console.log(databases)
        databases.forEach(element => {
          this.menuItems[1].childs.push({
            icon: 'table_rows', name: element['database'], childs: [
              {
                icon: 'settings', name: 'settings', routerLink: 'databases/' + (element['id'] ? element['id'] : element['databaseId']) + '/settings', childs: []
              },
              { icon: 'list', name: 'tables', routerLink: 'databases/' + (element['id'] ? element['id'] : element['databaseId']), childs: [] }
            ]
          })
        });

      },
      err => {
        console.error(err)
        this.shared.newToast({
          message: err.error.message, type: ToastType.ERROR
        })
      }
    )

  }

  signOut() {
    this.httpClientService.logout().subscribe(() => {
      this.router.navigate([''])
    }, err => {
      console.error(err)
      this.shared.newToast({
        message: err.error.message, type: ToastType.ERROR
      })
    })

  }


  ngAfterContentInit(): void {

  }

  ngAfterContentChecked(): void {
    //Called after every check of the component's or directive's content.
    //Add 'implements AfterContentChecked' to the class.

  }

  ngOnInit() {

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
    if (language.extras.code)
      this.languages[0].icon = '<span class="text-icon">' + language.extras.code.toUpperCase() + '</span>';
    this.translate.use(language.extras.code)
  }


  openTable(name) {
    //this.router.navigate(['/home']);
    this.router.navigate(['/table', name]);

    this.router.onSameUrlNavigation = 'reload';
  }


}
