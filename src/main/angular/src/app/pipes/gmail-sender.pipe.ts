import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'gmailSender'
})
export class GmailSenderPipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    return value.split("<")[0];
  }

}
