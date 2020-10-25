import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
//import { HttpLoaderFactory } from '../app.module';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { MatButtonModule } from '@angular/material';
import { HttpLoaderFactory } from '../main-app/main-app.module';
import {MatMenuModule} from '@angular/material/menu';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [WelcomePageComponent],
  imports: [
    CommonModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    MatButtonModule,
    MatMenuModule,
    RouterModule
  ]
})
export class WelcomeModule { }
