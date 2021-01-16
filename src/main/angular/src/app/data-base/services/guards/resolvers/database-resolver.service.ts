import { HttpDatabaseService } from './../../http-database.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DatabaseResolverService implements Resolve<Object>{

  constructor(private http: HttpDatabaseService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Object | Observable<Object> | Promise<Object> {
    return this.http.getDatabase(route.paramMap.get('id'))
  }
}
