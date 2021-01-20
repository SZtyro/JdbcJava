import { HttpDatabaseService } from './../modules/data-base/services/http-database.service';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(
    protected httpClient: HttpClient,
    public database: HttpDatabaseService
  ) { }

  url = '';

  errorHandler(err) {
    console.log('handler')
    if (err.status == 401)
      window.location.href = '/login'
    console.log(err)
    return of(err)
  }


  getRandom() {
    return this.httpClient.get(this.url + "/randomNumber");
  }


  getUser(mail?) {
    //return this.httpClient.get(this.url + "/api/google/user", { responseType: 'text' })

    let params = {
      mail: mail
    }
    return this.httpClient.get(this.url + "/api/user", { params: mail != 0 ? params : null })
  }



  deleteUser(mail) {
    return this.httpClient.delete(this.url + '/api/user', { params: { mail: mail } })
  }

  getCompany(id?) {
    let params = {};
    if (id)
      params['id'] = id;

    return this.httpClient.get(this.url + "/api/company", { params: params })
      .pipe(catchError(err => this.errorHandler(err)))
  }

  getCurrentCompany() {
    return this.httpClient.get(this.url + "/api/company/current")
      .pipe(catchError(err => this.errorHandler(err)))
  }

  setCurrentCompany(id) {
    return this.httpClient.put(this.url + '/api/company/current', null, { params: { id: id } })
  }



  saveCompany(body) {
    return this.httpClient.post(this.url + "/api/company", body)
  }

  updateCompany(company) {
    return this.httpClient.put(this.url + "/api/company", company)
  }

  getInstitution(id?) {
    let params;
    if (id) {
      params = { id: id }
    }

    return this.httpClient.get(this.url + "/api/institution", {
      params: params,
      headers: {
        "Content-Type": "application/json"
      }
    })
  }

  updateInstitution(body) {
    return this.httpClient.put(this.url + "/api/institution", body)
  }

  deleteInstitution(id) {
    return this.httpClient.delete(this.url + '/api/institution', { params: { id: id } })
  }


  logout() {
    return this.httpClient.get(this.url + "/api/google/logout")
  }


  getNotification(id, offset, limit) {
    return this.httpClient.get(this.url + '/api/notification', {
      params: {
        id: id,
        offset: offset,
        limit: limit
      }
    })
  }

  updateUser(body) {
    return this.httpClient.put(this.url + "/api/user", body)
  }

  inviteUser(body) {
    return this.httpClient.post(this.url + "/api/user", body)
  }

  getCompanyUsers() {
    return this.httpClient.get(this.url + "/api/institution/employee")
  }



  getCompanyExtensions() {
    return this.httpClient.get<Object[]>(this.url + '/api/extensions/company')
  }



}

