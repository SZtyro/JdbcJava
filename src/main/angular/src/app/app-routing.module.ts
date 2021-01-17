import { InviteUserComponent } from './main-app/components/forms/settings/invite-user.component';
import { EmployeeResolverService } from './services/guards/resolvers/employee-resolver.service';
import { InstitutionsListComponent } from './main-app/components/lists/institutions-list.component';
import { EmployeesResolverService } from './services/guards/resolvers/employees-resolver.service';
import { EmployeesListComponent } from './main-app/components/lists/employees-list.component';
import { InstitutionsResolverService } from './services/guards/resolvers/institutions-resolver.service';
import { EmployeeComponent } from './main-app/components/forms/employee/employee.component';
import { NotificationResolverService } from './services/guards/resolvers/notification-resolver.service';
import { InstitutionResolverService } from './services/guards/resolvers/institution-resolver.service';
import { InstitutionRegisterComponent } from './main-app/components/forms/settings/institution-register.component';
import { CompanyResolverService } from './services/guards/resolvers/company-resolver.service';
import { CompanySettingsComponent } from './main-app/components/forms/settings/company-settings/company-settings.component';
import { RegisterCompanyComponent } from './main-app/components/forms/register-company/register-company.component';
import { CompaniesResolverService } from './services/guards/resolvers/companies-resolver.service';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './main-app/components/custom/home/home.component';
import { WelcomePageComponent } from './welcome/welcome-page/welcome-page.component';
import { UserSettingsComponent } from './main-app/components/forms/settings/user-settings/user-settings.component';

const routes: Routes = [
  {
    path: 'home', component: HomeComponent, resolve: {
      companies: CompaniesResolverService,
      notifications: NotificationResolverService
    },
  },
  { path: 'login', redirectTo: '' },
  {
    path: 'databases', loadChildren: () => import('./data-base/data-base.module').then(m => m.DataBaseModule)
  },
  {
    path: 'employees', children: [
      {
        path: 'list', component: EmployeesListComponent, resolve: {
          employees: EmployeesResolverService
        }
      },
      {
        path: 'invite', component: InviteUserComponent, resolve: {
          institutions: InstitutionsResolverService,
        }
      },
      {
        path: ':mail', component: EmployeeComponent, resolve: {
          institutions: InstitutionsResolverService,
          employee: EmployeeResolverService
        }
      }
    ],
  },
  {
    path: 'institutions', children: [
      {
        path: 'list', component: InstitutionsListComponent, resolve: {
          institutions: InstitutionsResolverService
        }
      },
      {
        path: ':id', component: InstitutionRegisterComponent, resolve: {
          institution: InstitutionResolverService
        }
      }
    ],
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
