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
    { icon: 'view_module', name: 'plan', routerLink: 'plan' },
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
  ) {

    this.httpClientService.getCompanyExtensions().subscribe(
      extensions => {


        extensions.forEach(extension => {
          if (extension['menu'])
            this.menuItems.push(extension['menu'])
        })
        console.log(this.menuItems)


      },
      err => console.error(err),
      () => {
        console.log(this.menuItems)
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


        if (this.menuItems.find(el => el.name == 'database'))
          this.httpClientService.database.getDatabases().subscribe(
            databases => {
              console.log(databases)
              databases.forEach(element => {

                let i = this.menuItems.findIndex(el => el.name == 'database');
                if (i != null)
                  console.log(this.menuItems[i])
                // if (!this.menuItems[i].childs)
                //   this.menuItems[i].childs = [];

                this.menuItems[i].childs.push({
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



}
