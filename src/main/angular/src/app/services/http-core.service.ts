import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HttpCoreService {

  prefix = "/api"

  constructor(protected http: HttpClient) { }
}
