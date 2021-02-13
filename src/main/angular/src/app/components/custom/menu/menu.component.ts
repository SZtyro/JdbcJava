import { MenuService } from './../../../services/menu.service';
import { fadeIn } from './../../../ts/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from './../../../services/shared.service';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { HttpClientService } from 'src/app/services/http-client.service';
import { ToastType } from 'src/app/ts/enums/toastType';
import { FunctionBase } from 'src/app/ts/interfaces/functionBase';
import { openClose } from 'src/app/ts/animations';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
  animations: [
    openClose,
    fadeIn
  ]
})
export class MenuComponent implements OnInit {

  
  screenWidth: number;

  

  constructor(
    private httpClientService: HttpClientService,
    private shared: SharedService,
    public translate: TranslateService,
    private router: Router,
    private route: ActivatedRoute,
    public menu: MenuService
  ) {

    this.screenWidth = window.innerWidth;
    window.onresize = () => {
      this.screenWidth = window.innerWidth;
    };
  }

  ngOnInit(): void {

    this.httpClientService.getCompanyExtensions().subscribe(
      extensions => {


        extensions.forEach(extension => {
          if (extension['menu'])
            this.menu.menuItems.push(extension['menu'])
        })
        console.log(this.menu.menuItems)


      },
      err => {
        this.shared.newToast({
          message: err.error.message,
          type: ToastType.ERROR
        })
        this.router.navigate(['company', 'list'])
      },
      () => {


        this.httpClientService.getCompany().subscribe(
          companies => {
            console.log(companies)
            companies.forEach(element => {
              this.menu.companies[0].childs.push(element)
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
            this.menu.companies[0].name = current.name;
          },
          err => {
            console.error(err)
            this.shared.newToast({
              message: err.error.message, type: ToastType.ERROR
            })
          })


        if (this.menu.menuItems.find(el => el.name == 'database'))
          this.httpClientService.database.getDatabases().subscribe(
            databases => {

              databases.forEach(element => {

                let i = this.menu.menuItems.findIndex(el => el.name == 'database');

                this.menu.menuItems[i].childs.push({
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






  selectCompany(item) {
    if (item.id)
      this.httpClientService.setCurrentCompany(item.id).subscribe(current => {

        console.log(window.location.href)
        if (window.location.href != '/home')
          window.location.href = '/home'
        else
          window.location.reload()
      })
  }

  selectLanguage(language: FunctionBase) {
    this.menu.languages[0].name = language.name;
    if (language.extras.code)
      this.menu.languages[0].icon = '<span class="text-icon">' + language.extras.code.toUpperCase() + '</span>';
    this.translate.use(language.extras.code)
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
}
