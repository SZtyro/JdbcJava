import { Component, OnInit, HostListener, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClientService } from 'src/app/services/http-client.service';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { SharedService } from 'src/app/services/shared.service';


@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.scss']
})
export class WelcomePageComponent implements OnInit, OnDestroy {

  user;

  constructor(
    public translate: TranslateService,
    public shared: SharedService,
    private http: HttpClientService
  ) {


  }

  ngOnDestroy(): void {

  }


  ngOnInit() {
    this.http.getGoogleUser().subscribe(user => {
      this.user = user
    })
  }

  ngAfterViewInit() {
    //sthis.googleInit();
    //setTimeout(() => this.isSignedIn$ = this.auth.isSignedIn(), 1000);
  }

  token;

  scrollToElement($element): void {
    //console.log($element);
    $element.scrollIntoView({ behavior: "smooth", block: "start", inline: "nearest" });
  }

  scrollFunction() {
    //console.log(document.body.scrollTop)
    // if (window.pageYOffset > 20 || window.pageYOffset > 20) {
    //   document.getElementById("carouselExampleIndicators").style.top = "0";
    // } else {
    //   document.getElementById("carouselExampleIndicators").style.top = "-50px";
    // }
  }


}
