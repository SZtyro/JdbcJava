import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(
    private httpClient: HttpClient,

  ) { }

  url = '';

  errorHandler(err) {
    console.log('handler')
    if (err.status == 401)
      window.location.href = '/api/google/auth'
    console.log(err)
    return of(err)
  }

  loginUser(data) {
    return this.httpClient.post(this.url + '/databaseLogin', data,
      {
        headers: {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  getConnectedBase() {
    return this.httpClient.get(this.url + '/currentDatabase',
      {
        headers: {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  getTable(tableName) {
    return this.httpClient.post<Map<String, Object>[]>(this.url + '/getTable', tableName,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  getTableNames() {

    return this.httpClient.get<String[]>(this.url + '/getTableNames',
      {
        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  getForeignKeyColumns(table) {
    return this.httpClient.post<String>(this.url + '/getForeignKeyColumns', table,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  getIds(table) {
    return this.httpClient.post<String>(this.url + '/getIdList', table,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  getType(table) {
    return this.httpClient.post<String>(this.url + '/getDataType', table,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  getPrimaryKey(tableName) {
    return this.httpClient.post<String>(this.url + '/getPrimaryKey', tableName,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  postRow(elem: String) {
    return this.httpClient.post<String>(this.url + "/execute", elem,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }
  deleteRow(id: String[]) {
    return this.httpClient.post(this.url + "/delete", id,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }



  checkToken(token) {
    return this.httpClient.get(this.url + "/token", { responseType: "text", headers: { "Authorization": token, } });
  }

  getRandom() {
    return this.httpClient.get(this.url + "/randomNumber");
  }


  getUser() {
    return this.httpClient.get(this.url + "/api/google/user", { responseType: 'text' })
  }

  getCompany() {
    return this.httpClient.get(this.url + "/api/company")
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

  inviteUser(body) {
    return this.httpClient.post(this.url + "/api/user/invite", body)
  }

  getCompanyUsers() {
    return this.httpClient.get(this.url + "/api/institution/employee")
  }
}

