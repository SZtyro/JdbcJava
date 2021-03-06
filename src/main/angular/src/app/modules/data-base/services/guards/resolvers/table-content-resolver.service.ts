import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClientService } from 'src/app/services/http-client.service';

@Injectable({
  providedIn: 'root'
})
export class TableContentResolverService implements Resolve<Object>{

  constructor(private http: HttpClientService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Object | Observable<Object> | Promise<Object> {

    if (route.params['tableName'])
      return this.http.database.getTableContent(route.params['id'], route.params['tableName'])
    else
      return this.http.database.getTableContent(route.params['id'])

  }
}
