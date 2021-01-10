import { HttpClientService } from 'src/app/services/http-client.service';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeResolverService implements Resolve<Object> {

  constructor(private http: HttpClientService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Object | Observable<Object> | Promise<Object> {
    return this.http.getUser(route.params.mail)
  }
}
