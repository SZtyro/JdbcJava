import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HttpClient, HttpClientJsonpModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatPaginatorModule, MatDialogModule, MatNativeDateModule } from '@angular/material';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MainAppModule } from './main-app/main-app.module';
import { DataBaseModule } from './data-base/data-base.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';

import { GridsterModule } from 'angular-gridster2';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { WelcomeModule } from './welcome/welcome.module';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { GoogleChartsModule } from 'angular-google-charts';
import { MenuItemComponent } from './main-app/components/custom/menu-item/menu-item.component';
import { MatRippleModule } from '@angular/material/core';
import { AuthInterceptor } from './services/interceptors/auth-interceptor.service';


export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}
export function provideConfig() {

}


@NgModule({
  declarations: [
    AppComponent,
    MenuItemComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    NgbModule,
    MainAppModule,
    DataBaseModule,
    MatSidenavModule,
    MatCardModule,
    MatListModule,
    MatExpansionModule,
    //SocialLoginModule,
    GridsterModule,
    MatMenuModule,
    MatTooltipModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    MatCheckboxModule,
    DragDropModule,
    WelcomeModule,
    HttpClientJsonpModule,
    MatProgressSpinnerModule,
    GoogleChartsModule,
    MatRippleModule


  ],
  exports: [

  ],
  entryComponents: [
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
