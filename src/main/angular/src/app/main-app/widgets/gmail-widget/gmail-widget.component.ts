import { Component, OnInit, ElementRef } from '@angular/core';
import { GridsterItem } from 'angular-gridster2';
import { GmailService, GMailContent } from 'src/app/services/Gmail/gmail.service';
//import { AuthService, GoogleLoginProvider, SocialUser } from 'angularx-social-login';
import { MatTableDataSource } from '@angular/material';
import { HomeWidget } from '../../interfaces/homeWidget';
import { TranslateService } from '@ngx-translate/core';
import { SharedService } from 'src/app/services/shared.service';


interface GThread {
  id: number;
  threadId: number;
}

@Component({
  selector: 'app-gmail-widget',
  templateUrl: './gmail-widget.component.html',
  styleUrls: ['./gmail-widget.component.scss']
})
export class GmailWidgetComponent implements OnInit, GridsterItem, HomeWidget {

  onResize() {
    
  }
  
  tagName = "app-gmail-widget";

  //GRIDSTER
  x: number = 0;
  y: number = 0;
  cols: number = 8;
  rows: number = 6;

  //USER
  //private user: SocialUser;

  //TABLE
  dataSource;
  displayedColumns: string[] = ['from', 'snippet'];

  //GMAIL CONTENT
  threads: GThread[];
  messages: GMailContent[] = [];


  constructor(
    private service: GmailService,
    //private authService: AuthService,
    private elem:ElementRef,
    public translate: TranslateService,
    private shared:SharedService
    ) {
      
  }
  widgetData: any;
  load() {
    throw new Error("Method not implemented.");
  }
  delete() {
    throw new Error("Method not implemented.");
  }
  onChange() {
    
  }
  widgetNumber: number;
  toSave() {
    throw new Error("Method not implemented.");
  }

  ngOnInit() {
    //this.signInWithGoogle();
    console.log(this.widgetNumber + " Gmail");
  }

  // fetchMails() {
  //   this.service.getMessages(this.user.id, this.user.authToken).subscribe(messages => {

  //     this.threads = messages["messages"];

  //     //console.log(this.threads);
  //     this.threads.forEach((element, i) => {
  //       this.service.getMessage(this.user.id, this.user.authToken, this.threads[i].id).subscribe(d => {

  //         this.messages[i] = (d);

  //       });
  //     });
  //   },
  //     error => { console.log(error) },
  //     () => {
  //       this.messages.forEach((element, i) => {
  //         element.payload.headers.forEach(el => {
  //           if (el.name === "From")

  //             this.messages[i].from = el.value;
  //         })
  //       });
  //       this.dataSource = new MatTableDataSource(this.messages);
  //     }
  //   )
  // }

  // signInWithGoogle(): void {

  //   this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)
  //   this.authService.authState.subscribe((user) => {
  //     console.log(user);
  //     this.user = user;
  //     this.fetchMails();

  //   });
  // }
}
