import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { SharedService } from './shared.service';
import { HttpClientService } from './http-client.service';
import { Injectable } from '@angular/core';
import { ToastType } from '../ts/enums/toastType';
import { FunctionBase } from '../ts/interfaces/functionBase';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  sideNavExtension: boolean = false;

  menuItems: FunctionBase[] = [
    { icon: 'home', name: 'home', routerLink: 'home' },

    {
      icon: 'email', name: 'E-mail', routerLink: 'mail', childs: []
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
            // { icon: 'groups', name: 'Groups' },
            // { icon: 'event_note', name: 'Shifts' },
            // { icon: 'fact_check', name: 'Tasks' }
          ]
        },
        // {
        //   icon: 'local_shipping', name: 'Vehicles', childs: []
        // }
      ]
    },
    {
      icon: 'settings', name: 'NAVBAR.SETTINGS', routerLink: 'settings/company', childs: [

      ]
    },
    // { icon: 'monetization_on', name: 'finances' },
    { icon: 'view_module', name: 'plan', routerLink: 'plan' },
  ];

  companies: FunctionBase[] = [
    {
      icon: 'business', name: 'pick_company', childs: [
        {
          icon: 'add', name: 'add_company', routerLink: 'company/0'
        }
      ]
    },

  ]
  languages: FunctionBase[] = [
    {
      icon: '<span class="text-icon">PL</span>', name: 'Polski', childs: [
        {
          icon: '<span class="text-icon">EN</span>', name: 'English', extras: { code: 'en' }
        },
        {
          icon: '<span class="text-icon">PL</span>', name: 'Polski', extras: { code: 'pl' }
        },
      ]
    }
  ]

  constructor(
    private httpClientService: HttpClientService,
    private shared: SharedService,
    public translate: TranslateService,
    private router: Router,
    private route: ActivatedRoute
  ) {
   
    this.route.url.subscribe(url => {
      console.log(window.location.pathname)
      
      if (window.location.pathname != "/") {
        
      }
    })



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



}
