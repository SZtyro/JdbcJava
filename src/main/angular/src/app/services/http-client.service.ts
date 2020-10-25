import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(
    private httpClient: HttpClient,

  ) { }


  //url = "https://nwtaback.herokuapp.com";
  url = 'https://localhost:8080';
  //urlAllowed = "https://nwtafront.herokuapp.com"
  urlAllowed = "https://localhost:4200";

  loginUser(data) {
    return this.httpClient.post(this.url + '/databaseLogin', data,
      {
        headers: {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  getConnectedBase() {
    return this.httpClient.get(this.url + '/currentDatabase',
      {
        headers: {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  getTable(tableName) {
    return this.httpClient.post<Map<String, Object>[]>(this.url + '/getTable', tableName,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  getTableNames() {

    return this.httpClient.get<String[]>(this.url + '/getTableNames',
      {
        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  getForeignKeyColumns(table) {
    return this.httpClient.post<String>(this.url + '/getForeignKeyColumns', table,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  getIds(table) {
    return this.httpClient.post<String>(this.url + '/getIdList', table,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  getType(table) {
    return this.httpClient.post<String>(this.url + '/getDataType', table,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  getPrimaryKey(tableName) {
    return this.httpClient.post<String>(this.url + '/getPrimaryKey', tableName,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  postRow(elem: String) {
    return this.httpClient.post<String>(this.url + "/execute", elem,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  deleteRow(id: String[]) {
    return this.httpClient.post(this.url + "/delete", id,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }

  setDashboard(dashboard: String) {
    return this.httpClient.post(this.url + "/saveDashboard", dashboard,
      {

        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
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
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }

  tryLogin() {

    return this.httpClient.get(this.url + "/loginUser",
      {
        headers:
        {
          "Authorization": sessionStorage.getItem('token'),
          'Access-Control-Allow-Origin': this.urlAllowed
        }
      });
  }
  checkToken(token) {
    return this.httpClient.get(this.url + "/token", { responseType: "text", headers: { "Authorization": token, 'Access-Control-Allow-Origin': this.urlAllowed } });
  }

  getRandom() {
    return this.httpClient.get(this.url + "/randomNumber");
  }


}

