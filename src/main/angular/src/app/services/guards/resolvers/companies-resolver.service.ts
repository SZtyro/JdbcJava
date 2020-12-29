import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClientService } from '../../http-client.service';

@Injectable({
  providedIn: 'root'
})
export class CompaniesResolverService implements Resolve<Object>{

  constructor(private http: HttpClientService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): object | Observable<object> | Promise<object> {
    return this.http.getCompany();
  }
}
