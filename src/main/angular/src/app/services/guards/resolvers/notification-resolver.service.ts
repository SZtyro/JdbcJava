import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClientService } from '../../http-client.service';

@Injectable({
  providedIn: 'root'
})
export class NotificationResolverService implements Resolve<Object>{

  constructor(private http: HttpClientService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Object | Observable<Object> | Promise<Object> {
    return this.http.getNotification(0, 0, 10)
  }
}
