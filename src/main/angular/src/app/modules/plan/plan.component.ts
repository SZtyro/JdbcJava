import { HttpClientService } from 'src/app/services/http-client.service';
import { SharedService } from './../../services/shared.service';
import { HttpExtensionsService } from './services/http-extensions.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ToastType } from 'src/app/ts/enums/toastType';

@Component({
  selector: 'app-plan',
  templateUrl: './plan.component.html',
  styleUrls: ['./plan.component.scss']
})
export class PlanComponent implements OnInit {


  employeesCount = 20;

  extensions = [

  ]

  plan = [

  ]

  constructor(
    private route: ActivatedRoute,
    private http: HttpExtensionsService,
    private shared: SharedService,
    private httpShared: HttpClientService
  ) { }

  ngOnInit(): void {
    this.httpShared.getCompanyExtensions().subscribe(data => {
      this.plan = data;
    })

    this.route.data.subscribe(d => {
      this.extensions = d.extensions;
    })


  }



  cancelSubscription(extension) {
    this.plan.splice(this.plan.findIndex(el => el === extension), 1);
  }

  buy(extension) {
    this.plan.push(extension)
  }

  save() {
    console.log('asd')
    this.http.savePlan(this.plan).subscribe(
      () => {
        this.shared.newToast({
          message:"toasts.modules.updated"
        })
        window.location.href = '/home';
      }, err => {
        this.shared.newToast({
          message: err.error.message,
          type: ToastType.ERROR
        })
      }
    )
  }

  summaryCost() {
    let p = 0;
    this.plan.forEach(element => {
      p += element.price;
    });

    return p;
  }

  isHidden(elem) {
    if (this.plan.find(e => e.permission == elem.permission))
      return true;
    else
      return false;
  }
}
