import { MenuService } from './services/menu.service';
import { openClose, fadeIn } from './ts/animations';
import { Component, OnInit, AfterViewChecked, AfterContentChecked, AfterContentInit } from '@angular/core';
import { Router, RouterEvent, NavigationEnd, ActivatedRoute, Event, NavigationStart, NavigationCancel, NavigationError } from '@angular/router';
import { HttpClientService } from './services/http-client.service';
//import { AuthService, SocialUser } from "angularx-social-login";
import { GoogleLoginProvider } from "angularx-social-login";
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material';
import { SharedService } from './services/shared.service';
import { Observable } from 'rxjs';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { FunctionBase } from './ts/interfaces/functionBase';
import { ToastType } from './ts/enums/toastType';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations: [
    openClose,
    fadeIn
  ]
})
export class AppComponent implements OnInit {



  screenWidth: number;
  isLoading: boolean = false;

  constructor(
    private httpClientService: HttpClientService,
    private router: Router,
    public translate: TranslateService,
    public dialog: MatDialog,
    public shared: SharedService,
    public menu: MenuService,
    private route: ActivatedRoute
  ) {
    translate.addLangs(['en', 'pl']);
    translate.setDefaultLang('pl');
    translate.use('pl')

    this.screenWidth = window.innerWidth;
    window.onresize = () => {
      this.screenWidth = window.innerWidth;
    };



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




  ngOnInit() {
    this.router.events.subscribe((event: Event) => {
      switch (true) {
        case event instanceof NavigationStart:
          this.isLoading = true;
          break;
        case event instanceof NavigationEnd:
        case event instanceof NavigationCancel:
        case event instanceof NavigationError:
          this.isLoading = false;
          break;
        default:
          break;
      }
    })
  }



}
