import { Injectable } from '@angular/core';
import { Toast } from 'src/app/main-app/interfaces/toast';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  toasts: Toast[] = [];

  constructor() { }

  newToast(toast: Toast) {
    this.toasts.push(toast)

    setTimeout(() => {
      this.toasts.splice(this.toasts.indexOf(toast), 1)
    }, toast.duration ? toast.duration : 8000);
  }

}
