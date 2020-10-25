import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class DatabaseService {
  databases:Database[];

  constructor() { }

}
export interface Database{
  id:number;
  name:String;
  entities:String[];
}
