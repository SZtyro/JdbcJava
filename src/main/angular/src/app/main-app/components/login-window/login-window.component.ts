import { Component, OnInit } from '@angular/core';
import { HttpClientService } from 'src/app/services/http-client.service';
import { Router, ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/services/shared.service';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-login-window',
  templateUrl: './login-window.component.html',
  styleUrls: ['./login-window.component.scss'],
  animations: [
    trigger('openClose', [
      // ...
      state('open', style({
        width: '70px',
        opacity: 1
      })),
      state('closed', style({
        width: '0px',
        opacity: 0
      })),
      transition('open => closed', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
      ]),
      transition('closed => open', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
      ]),
    ]),
    trigger('loginDetails', [
      // ...
      state('open', style({
        height: '560px',
        opacity: 1
      })),
      state('closed', style({
        height: '120px',

      })),
      transition('open => closed', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
      ]),
      transition('closed => open', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
      ]),
    ]),

  ],

})
export class LoginWindowComponent implements OnInit {

  baseConnecting = false;
  tablesLoading = true;
  databaseNames = [];
  databaseTables;

  userName: String = "";
  password: String = "";
  url: String = "";
  dataBase: String = "";
  port: String = "";

  constructor(
    private httpClientService: HttpClientService,
    private router: Router,
    private route: ActivatedRoute,
    private shared: SharedService,
  ) { }


  ngOnInit() {
    this.url = JSON.parse(localStorage.getItem('url'));
    this.userName = JSON.parse(localStorage.getItem('userName'));
    this.dataBase = JSON.parse(localStorage.getItem('dataBase'));
    this.port = JSON.parse(localStorage.getItem('port'));

    // this.route.data.subscribe(data => {
    //   console.log(data)
    //   if(data.database){
    //     this.userName = data.database.user;
    //     this.url = data.database.url;
    //     this.dataBase = data.database.database;
    //     this.port = data.database.port;

    //     this.httpClientService.getTableNames().subscribe(tableNames => {
    //       this.databaseTables = tableNames;
    //       this.tablesLoading = false;
    //     });
    //   }

    // })
  }

  login() {
    this.baseConnecting = true;
    
  }

  setEditing(hover: boolean) {
    //this.editing = hover;
  }

  setEditingDetails() {
    //this.editingDetails = !this.editingDetails;
  }

  fetchTables() {
    console.log('fetching tables')
    
  }

  openTable(tableName) {
    this.router.navigate(['/table', tableName]);
    this.router.onSameUrlNavigation = 'reload';
  }

  saveDB() {

  }

  download() {
    this.httpClientService.database.getTables('test').subscribe(data => console.log(data))
  }
}
