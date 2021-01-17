import { HttpCoreService } from './../../../services/http-core.service';
import { Injectable } from '@angular/core';

/**
 * Serwis http modułu bazy danych
 */
@Injectable({
  providedIn: 'root'
})
export class HttpDatabaseService extends HttpCoreService {

  deleteRow(id, tableName, columnName, rowId) {

    return this.http.delete(this.prefix + '/database/table/row', {
      params: {
        id: id,
        tableName: tableName,
        columnName: columnName,
        rowId: rowId
      }
    })
  }

  getTables(id: number, tableName?: string) {
    let params = {}

    if (tableName)
      params['tableName'] = tableName;
    if (id)
      params['id'] = id;

    return this.http.get(this.prefix + "/database/table", { params: params })
  }

  getTableContent(id, tableName?: string) {
    let params = {
      id: id,
    }
    if (tableName)
      params['tableName'] = tableName

    return this.http.get(this.prefix + "/database/content", { params: params })
  }

  saveRow(body, tableName, id) {

    return this.http.post(this.prefix + '/database/table/row', body, {
      params: {
        id: id,
        tableName: tableName
      }
    })
  }

  updateRow(body, tableName, id) {

    return this.http.put(this.prefix + '/database/table/row', body, {
      params: {
        id: id,
        tableName: tableName
      }
    })
  }

  createDatabase(body) {
    return this.http.post(this.prefix + '/database', body)
  }

  updateDatabase(body) {

    return this.http.put(this.prefix + '/database', body)
  }

  getDatabases() {
    return this.http.get<Object[]>(this.prefix + "/database/list");
  }

  getDatabase(id) {
    return this.http.get(this.prefix + '/database', {
      params: { id: id }
    })
  }

  getConstraints(id, tableName) {
    return this.http.get(this.prefix + "/database/table/reference", {
      params: {
        id: id,
        tableName: tableName
      }
    });
  }

  getIds(id, tableName, column) {
    return this.http.get<Object[]>(this.prefix + "/database/table/columnKeys", {
      params: {
        id: id,
        tableName: tableName,
        column: column
      }
    });
  }
}
