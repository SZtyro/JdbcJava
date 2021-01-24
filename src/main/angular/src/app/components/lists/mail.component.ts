import { BasicTable } from 'src/app/ts/basicTable';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { ToastType } from 'src/app/ts/enums/toastType';

@Component({
  selector: 'app-mail',
  templateUrl: '../../html/basicTable.html'
})
export class MailComponent extends BasicTable implements OnInit {

  messages;

  ngOnInit(): void {
    this.name = 'mail';
    this.pageSize = 20;
    this.columns = [
      {
        name: 'from', assignValue: (row) => {
          return row.payload.headers.find(header => header.name == 'From').value.split('<')[0]
        }
      },
      {
        name: 'subject', assignValue: (row) => {
          return row.payload.headers.find(header => header.name == 'Subject').value
        }
      }
    ]
    this.rowStyle = 'cursor: pointer;'

    this.route.data.subscribe(data => {
      this.messages = data.mails

      this.dataSource = new MatTableDataSource(this.messages.messages);


      this.dataSource.paginator = this.paginator
      if (!this.dataSource.paginator.hasNextPage())
        this.loadMoreMails(this.pageSize);
    })

    this.extractColumnNames();

    this.paginator.page.subscribe(pg => {
      console.log(pg)
      if (!this.dataSource.paginator.hasNextPage()) {
        console.log('pobieranie maili')

        this.loadMoreMails(this.dataSource.paginator.pageSize)
      }
    })
  }

  loadMoreMails(max) {
    if (this.messages.nextPageToken) {
      this.isLoading = true;

      this.http.getMails(max, this.messages.nextPageToken).subscribe(
        mails => {
          mails['messages'].forEach(element => {
            this.messages.messages.push(element)
          });
          this.messages.nextPageToken = mails['nextPageToken']
          this.dataSource.data = this.messages.messages;

        },
        err => {
          this.shared.newToast({
            message: err.error.message,
            type: ToastType.ERROR
          })
        },
        () => {
          this.isLoading = false;
        }
      )
    }
  }

  onRowClick(row) {
    window.open(
      'https://mail.google.com/mail/u/0/#inbox/' + row.threadId,
      '_blank'
    )
  }

  onRowAction(actionId: any, row: any) {
    throw new Error('Method not implemented.');
  }
  onUnderAction(actionId: any) {
    throw new Error('Method not implemented.');
  }

}