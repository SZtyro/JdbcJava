import { HttpCoreService } from './../../../services/http-core.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HttpExtensionsService extends HttpCoreService {

  getExtensions() {
    return this.http.get<Object[]>(this.prefix + '/extensions')
  }



  savePlan(extensions) {
    return this.http.post(this.prefix + '/extensions/company', extensions)
  }
}
