import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    console.log(request)

    return next.handle(request).pipe(tap(() => { },
      (err: any) => {
        console.log(err)

        if (err.status !== 401) {
          console.log("zalogowany")
          return;
        } else {
          console.log("niezalogowany")
          window.location.href = "login"
        }

      }));
  }
}
