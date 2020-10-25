import { Injectable } from '@angular/core';
import { HomeComponent } from 'src/app/main-app/components/home/home.component';
import { Subject, Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  constructor() { }

  homeRef:HomeComponent;
  editGrid: boolean = false;
  private editGridSubject = new Subject<boolean>();

  dbConnnection: boolean = false;
  private dbConnnectionSubject = new Subject<boolean>();

  isUserLogged: boolean = false;
  private isUserLoggedSubject = new Subject<boolean>();

  showNavBar: boolean = true;
  private showNavBarSubject = new BehaviorSubject<boolean>(this.showNavBar);
  
  setShowNavBar(state:boolean){
    this.showNavBar = state;
    this.showNavBarSubject.next(this.showNavBar);
  }

  getShowNavBar():Observable<boolean>{
    return this.showNavBarSubject.asObservable();
  }

  setIsUserLogged(isLogged){
    this.isUserLogged = isLogged;
    this.isUserLoggedSubject.next(this.isUserLogged);
  }

  getIsUserLogged():Observable<boolean>{
    return this.isUserLoggedSubject.asObservable();
  }

  setdbConnnection(){
    this.dbConnnection = !this.dbConnnection;
    this.dbConnnectionSubject.next(this.dbConnnection);
  }

  getdbConnnection():Observable<boolean>{
    return this.dbConnnectionSubject.asObservable();
  }

  setEditGrid(){
    this.editGrid = !this.editGrid;
    this.editGridSubject.next(this.editGrid);
  }

  getEditGrid():Observable<boolean>{
    return this.editGridSubject.asObservable();
  }
}
