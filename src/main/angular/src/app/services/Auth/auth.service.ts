import { Injectable, HostListener, AfterViewInit } from '@angular/core';
import { HttpClientService } from '../http-client.service';
import { Router } from '@angular/router';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { SharedService } from '../Shared/shared.service';


declare const gapi: any;

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public auth2: any;

  isSigned = new BehaviorSubject<boolean>(false);
  authInstance = null;
  googleInitialized: boolean = false;
  user;

  constructor(
    private http: HttpClientService,
    private router: Router,
    private shared: SharedService
  ) {
    this.googleInit().then(() => {

      console.log('Pobranie użytkownika')
      this.user = this.authInstance.currentUser.get();
      console.log(this.user)

      // if (!this.user['Ca']) {
      //   this.signIn();
      // }
    })

  }

  ngOnInit() {

  }



  async googleInit(): Promise<void> {
    return new Promise((res) => {
      new Promise((resolve) => {
        gapi.load('auth2', resolve);
      }).then(async () => {
        gapi.auth2.init({
          client_id: '36592518046-43kubsqj6gut5165dugs9u0cha4e0hah.apps.googleusercontent.com',
          cookiepolicy: 'none',
          scope: 'profile email'
        }).then((gapiData) => {

          this.googleInitialized = true;
          console.log("Google initialized")
          //this.isSigned.next(gapi.auth2.getAuthInstance().isSignedIn.get());
          this.authInstance = gapiData;
          res();

        })
      })
    })
  }

  async signIn(): Promise<any> {

    // Resolve or reject signin Promise
    return new Promise(async (resolve, reject) => {
      await this.authInstance.signIn().then(
        user => {

          //Pobranie tokena Google
          let token = user.getAuthResponse().id_token;
          sessionStorage.setItem('token', token);
          console.log(token)

          //Proba kontaktu z backendem
          this.http.tryLogin().subscribe(d => {
            console.log('Odpowiedz serwera na zalogowanie')
            console.log(d)

            this.isSigned.next(true);
            console.log('Uzytkownik');
            console.log(user);
            this.user = user;

            this.router.navigate(['/home'])
            resolve();
          }, err => {

            console.log(err);
            reject(err);

          })


        },
        error => {
          console.log(error);
          reject(error);
        });
    });
  }

  async signOut(): Promise<any> {
    if (!this.googleInitialized) {
      await this.googleInit();
    }

    return new Promise(() => {
      this.authInstance.disconnect();
      this.isSigned.next(false);
      this.router.navigate([''])
    });
  }


  async checkIfUserLogged(): Promise<boolean> {
    if (!this.googleInitialized) {
      await this.googleInit();
    }
    await this.getCurrentUser();
    return new Promise(isLogged => {
      let loginStatus = this.authInstance.isSignedIn.get();
      this.isSigned.next(loginStatus)
      isLogged(loginStatus);
    })

  }
  async getCurrentUser(): Promise<any> {
    if (!this.googleInitialized) {
      await this.googleInit();
    }

    return new Promise(user => {
      this.user = this.authInstance.currentUser.get();
      user(this.user);
    })

  }

};
