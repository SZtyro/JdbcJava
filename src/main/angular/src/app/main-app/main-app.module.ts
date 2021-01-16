import { MatDatepickerModule } from '@angular/material/datepicker';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/custom/home/home.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { RouterModule } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { GridsterModule } from 'angular-gridster2';


import { MatTableModule } from '@angular/material/table';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { GoogleChartsModule } from 'angular-google-charts';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClient } from '@angular/common/http';
import { FileUploadModule } from 'ng2-file-upload';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatInputModule } from '@angular/material/input';
import { MatBadgeModule } from '@angular/material/badge';
import { RegisterCompanyComponent } from './components/forms/register-company/register-company.component';
import { FormsModule } from '@angular/forms';
import { MatTabsModule } from '@angular/material/tabs';
import { CompanySettingsComponent } from './components/forms/settings/company-settings/company-settings.component';
import { UserSettingsComponent } from './components/forms/settings/user-settings/user-settings.component';
import { InstitutionRegisterComponent } from './components/forms/settings/institution-register.component';
import { EmployeeComponent } from './components/forms/employee/employee.component';
import { MatSelectModule } from '@angular/material/select';
import { EmployeesListComponent } from './components/lists/employees-list.component';
import { InstitutionsListComponent } from './components/lists/institutions-list.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { InviteUserComponent } from './components/forms/settings/invite-user.component';
import { DialogComponent } from './components/custom/dialog/dialog.component';

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}
@NgModule({
  entryComponents: [
  ],
  declarations: [
    HomeComponent,
    RegisterCompanyComponent,
    CompanySettingsComponent,
    UserSettingsComponent,
    InstitutionRegisterComponent,
    EmployeeComponent,
    EmployeesListComponent,
    InstitutionsListComponent,
    InviteUserComponent,
    DialogComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    DragDropModule,
    RouterModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    MatExpansionModule,
    MatGridListModule,
    GridsterModule,
    MatTableModule,
    ScrollingModule,
    GoogleChartsModule.forRoot(),
    MatCheckboxModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    FileUploadModule,
    MatProgressBarModule,
    MatInputModule,
    MatBadgeModule,
    FormsModule,
    MatTabsModule,
    MatSelectModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    MatDatepickerModule
  ],
  providers: []
})
export class MainAppModule { }
