import { InstitutionResolverService } from './services/guards/resolvers/institution-resolver.service';
import { InstitutionRegisterComponent } from './main-app/forms/institution-register/institution-register.component';
import { CompanyResolverService } from './services/guards/resolvers/company-resolver.service';
import { CompanySettingsComponent } from './main-app/forms/settings/company-settings/company-settings.component';
import { RegisterCompanyComponent } from './main-app/forms/register-company/register-company.component';
import { CompaniesResolverService } from './services/guards/resolvers/companies-resolver.service';
import { IsLoggedService } from './services/guards/canActivate/is-logged.service';
import { ChartComponentComponent } from './chart-component/chart-component.component';

import { CurrentDatabaseResolverService } from './services/guards/resolvers/current-database-resolver.service';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TableComponent } from './data-base/table/table.component';
import { HomeComponent } from './main-app/components/home/home.component';
import { LoginWindowComponent } from './main-app/components/login-window/login-window.component';
import { WelcomePageComponent } from './welcome/welcome-page/welcome-page.component';
import { UserSettingsComponent } from './main-app/forms/settings/user-settings/user-settings.component';

const routes: Routes = [
  { path: 'table/:tableName', component: TableComponent },
  {
    path: 'home', component: HomeComponent, resolve: {
      companies: CompaniesResolverService
    },
    //canActivate: [IsLoggedService]
  },
  { path: 'login', redirectTo: '' },
  { path: 'chart', component: ChartComponentComponent },
  {
    path: 'databases', component: LoginWindowComponent,
    // resolve: {
    //   database: CurrentDatabaseResolverService
    // }
  },
  {
    path: 'structures/:id', component: InstitutionRegisterComponent, resolve: {
      institution: InstitutionResolverService
    }
  },
  {
    path: 'settings', component: RegisterCompanyComponent, children: [
      { path: 'user', component: UserSettingsComponent },
      {
        path: 'company', component: CompanySettingsComponent, resolve: {
          company: CompanyResolverService
        }
      }
    ]
  },
  { path: '', component: WelcomePageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes,
    {
      onSameUrlNavigation: 'reload'
    })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
