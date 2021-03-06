import { MenuService } from './services/menu.service';
import { openClose, fadeIn } from './ts/animations';
import { Component, OnInit, AfterViewChecked, AfterContentChecked, AfterContentInit, ViewChild } from '@angular/core';
import { Router, RouterEvent, NavigationEnd, ActivatedRoute, Event, NavigationStart, NavigationCancel, NavigationError } from '@angular/router';
import { HttpClientService } from './services/http-client.service';
//import { AuthService, SocialUser } from "angularx-social-login";
import { GoogleLoginProvider } from "angularx-social-login";
import { TranslateService } from '@ngx-translate/core';
import { MatDialog, MatSidenav } from '@angular/material';
import { SharedService } from './services/shared.service';
import { Observable } from 'rxjs';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { FunctionBase } from './ts/interfaces/functionBase';
import { ToastType } from './ts/enums/toastType';
import { HostListener } from '@angular/core';

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


  //sideNavExtension: boolean = false;

  isLoading: boolean = false;

  @ViewChild('sidenav') sidenav: MatSidenav;

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

    this.shared.screenWidth = window.innerWidth;


  }


  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.shared.screenWidth = window.innerWidth;
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

  hasMenu() {
    return window.location.pathname != "/"
  }

  toggleSideNav(){
    this.sidenav.toggle();
  }

}
