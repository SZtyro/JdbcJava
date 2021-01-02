import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TableComponent } from './data-base/table/table.component';
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
import { DeleteModalComponent } from './data-base/delete-modal/delete-modal.component';
import { EditModalComponent } from './data-base/edit-modal/edit-modal.component';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { AddModalComponent } from './data-base/add-modal/add-modal.component';
import { MainAppModule } from './main-app/main-app.module';
import { DataBaseModule } from './data-base/data-base.module';
import { LoginWindowComponent } from './main-app/components/login-window/login-window.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';

import { GridsterModule } from 'angular-gridster2';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ChartSettingsModalComponent } from './main-app/modals/chart-settings-modal/chart-settings-modal.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { WelcomeModule } from './welcome/welcome.module';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ChartComponentComponent } from './chart-component/chart-component.component';
import { GoogleChartsModule } from 'angular-google-charts';
import { MenuItemComponent } from './menu-item/menu-item.component';
import { MatRippleModule } from '@angular/material/core';
import { AuthInterceptor } from './services/interceptors/auth-interceptor.service';





// const config = new AuthServiceConfig([
//   {
//     id: GoogleLoginProvider.PROVIDER_ID,
//     provider: new GoogleLoginProvider("36592518046-vna364b979556f9d5o8g2bav1vnms4pi.apps.googleusercontent.com")
//   }

// ]);

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}
export function provideConfig() {

  //return config;
}


@NgModule({
  declarations: [
    AppComponent,
    TableComponent,
    DeleteModalComponent,
    EditModalComponent,
    AddModalComponent,
    LoginWindowComponent,
    ChartSettingsModalComponent,
    ChartComponentComponent,
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
    DeleteModalComponent,
    EditModalComponent,
    AddModalComponent,
    ChartSettingsModalComponent
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
