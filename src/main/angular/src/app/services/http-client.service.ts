import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(
    private httpClient: HttpClient,

  ) { }

  url = '';


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

  setDashboard(dashboard: String) {
    return this.httpClient.post(this.url + "/saveDashboard", dashboard,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }

  getDashboard() {
    return this.httpClient.get(this.url + "/loadDashboard",
      {
        responseType: 'text',
        headers:
        {
          "Authorization": sessionStorage.getItem('token'),

        }
      });
  }

  tryLogin() {

    return this.httpClient.get(this.url + "/loginUser",
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
  }

  saveCompany(body) {
    return this.httpClient.post(this.url + "/api/company", body)
  }

}

