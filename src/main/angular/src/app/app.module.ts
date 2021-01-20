
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HttpClient, HttpClientJsonpModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatPaginatorModule, MatDialogModule, MatNativeDateModule, MatBadgeModule, MatGridListModule, MatProgressBarModule, MatTabsModule } from '@angular/material';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { DataBaseModule } from './modules/data-base/data-base.module';
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
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { GoogleChartsModule } from 'angular-google-charts';
import { MenuItemComponent } from './components/custom/menu-item/menu-item.component';
import { MatRippleModule } from '@angular/material/core';
import { AuthInterceptor } from './services/interceptors/auth-interceptor.service';
import { DialogComponent } from './components/custom/dialog/dialog.component';
import { HomeComponent } from './components/custom/home/home.component';
import { EmployeeComponent } from './components/forms/employee/employee.component';
import { RegisterCompanyComponent } from './components/forms/register-company/register-company.component';
import { CompanySettingsComponent } from './components/forms/settings/company-settings/company-settings.component';
import { InstitutionRegisterComponent } from './components/forms/settings/institution-register.component';
import { InviteUserComponent } from './components/forms/settings/invite-user.component';
import { UserSettingsComponent } from './components/forms/settings/user-settings/user-settings.component';
import { EmployeesListComponent } from './components/lists/employees-list.component';
import { InstitutionsListComponent } from './components/lists/institutions-list.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { FileUploadModule } from 'ng2-file-upload';
import { WelcomePageComponent } from './components/custom/welcome-page/welcome-page.component';
import { CompanyComponent } from './components/forms/company.component';


export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}
export function provideConfig() {

}


@NgModule({
  declarations: [
    AppComponent,
    MenuItemComponent,
    HomeComponent,
    RegisterCompanyComponent,
    CompanySettingsComponent,
    UserSettingsComponent,
    InstitutionRegisterComponent,
    EmployeeComponent,
    EmployeesListComponent,
    InstitutionsListComponent,
    InviteUserComponent,
    DialogComponent,
    WelcomePageComponent,
    CompanyComponent
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
    DataBaseModule,
    MatSidenavModule,
    MatCardModule,
    MatListModule,
    MatExpansionModule,
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
    HttpClientJsonpModule,
    MatProgressSpinnerModule,
    GoogleChartsModule,
    MatRippleModule,


    //RouterModule,
    MatGridListModule,
    ScrollingModule,
    FileUploadModule,
    MatProgressBarModule,
    MatBadgeModule,
    MatTabsModule,



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
