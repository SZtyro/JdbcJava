import { HttpClientService } from 'src/app/services/http-client.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IsLoggedService implements CanActivate {

  constructor(
    private http: HttpClientService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return new Promise((resolve, reject) => {
      this.http.getUser().subscribe(
        user => {
          console.log(user)
          resolve(true);
        },
        err => {
          console.log(err)
          window.location.href = '/api/google/auth'
          resolve(false);
        }
      )
    })
  }
}
