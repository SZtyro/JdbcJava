import { HttpCoreService } from './../../services/http-core.service';
import { HttpClient } from '@angular/common/http';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Injectable } from '@angular/core';

/**
 * Serwis http modułu bazy danych
 */
@Injectable({
  providedIn: 'root'
})
export class HttpDatabaseService extends HttpCoreService {

  deleteRow(tableName, columnName, rowId) {

    return this.http.delete(this.prefix + '/database/table/row', {
      params: {
        id: "0",
        tableName: tableName,
        columnName: columnName,
        rowId: rowId
      }
    })
  }

  getTables(tableName?: string) {
    let params = {
      id: "0",
    }
    if (tableName)
      params['tableName'] = tableName

    return this.http.get(this.prefix + "/database/table", { params: params })
  }

  getTableContent(tableName?: string) {
    let params = {
      id: "0",
    }
    if (tableName)
      params['tableName'] = tableName

    return this.http.get(this.prefix + "/database/content", { params: params })
  }

  saveRow(body, tableName) {

    return this.http.post(this.prefix + '/database/table/row', body, {
      params: {
        id: "0",
        tableName: tableName
      }
    })
  }

  updateRow(body, tableName) {

    return this.http.put(this.prefix + '/database/table/row', body, {
      params: {
        id: "0",
        tableName: tableName
      }
    })
  }


  getDatabases() {
    return this.http.get<Object[]>(this.prefix + "/database/list");
  }

  getConstraints(tableName) {
    return this.http.get(this.prefix + "/database/table/reference", {
      params: {
        id: "0",
        tableName: tableName
      }
    });
  }

  getIds(tableName, column) {
    return this.http.get<Object[]>(this.prefix + "/database/table/columnKeys", {
      params: {
        id: "0",
        tableName: tableName,
        column: column
      }
    });
  }
}
