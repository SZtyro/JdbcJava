import { MatButtonModule, MatDividerModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PlanRoutingModule } from './plan-routing.module';
import { PlanComponent } from './plan.component';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [PlanComponent],
  imports: [
    CommonModule,
    PlanRoutingModule,
    TranslateModule,
    MatButtonModule,
    MatDividerModule
  ]
})
export class PlanModule { }
