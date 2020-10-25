import { Component, OnInit, HostListener, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClientService } from 'src/app/services/http-client.service';
import { AuthService } from 'src/app/services/Auth/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { SharedService } from 'src/app/services/Shared/shared.service';


@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.scss']
})
export class WelcomePageComponent implements OnInit, OnDestroy {

  //isSignedIn: boolean = false;
  isSignedIn: boolean;
  userPhoto;

  constructor(
    private router: Router,
    private http: HttpClientService,
    public auth: AuthService,
    public translate: TranslateService,
    public shared: SharedService
  ) {


  }

  ngOnDestroy(): void {
    this.shared.setShowNavBar(true);

  }


  async ngOnInit() {
    //window.addEventListener('scroll', this.scrollFunction, true);
    this.shared.setShowNavBar(false);
    await this.auth.checkIfUserLogged().then(isLogged => {
        this.auth.isSigned.subscribe((isSigned)=> {
          this.isSignedIn = isSigned;
          if (isSigned) {
            this.userPhoto = this.auth.user.getBasicProfile().getImageUrl();  
          }
        })
      
      
    })

    console.log('welcome')
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
