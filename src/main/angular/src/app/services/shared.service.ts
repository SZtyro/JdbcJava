import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Toast } from 'src/app/main-app/interfaces/toast';
import { DialogComponent, DialogData } from '../main-app/components/dialog/dialog.component';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  toasts: Toast[] = [];

  constructor(
    protected dialog: MatDialog
  ) { }

  newToast(toast: Toast) {
    this.toasts.push(toast)

    setTimeout(() => {
      this.toasts.splice(this.toasts.indexOf(toast), 1)
    }, toast.duration ? toast.duration : 8000);
  }

  newDialog(data: DialogData) {
    return this.dialog.open(DialogComponent, {
      data: data
    })
  }
}
