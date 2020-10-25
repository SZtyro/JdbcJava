import { HttpClientService } from 'src/app/services/http-client.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CurrentDatabaseResolverService implements Resolve<object> {

  constructor( private http: HttpClientService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): object | Observable<object> | Promise<object> {
    return this.http.getConnectedBase();
  }


}
