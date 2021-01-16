import { DatabaseResolverService } from './data-base/services/guards/resolvers/database-resolver.service';
import { TableConstraintResolverService } from './data-base/services/guards/resolvers/table-constraint-resolver.service';
import { TableContentResolverService } from './data-base/services/guards/resolvers/table-content-resolver.service';

import { TablesResolverService } from './data-base/services/guards/resolvers/tables-resolver.service';
import { TableListComponent } from './data-base/lists/table-list.component';
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
import { ChartComponentComponent } from './chart-component/chart-component.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './main-app/components/custom/home/home.component';
import { WelcomePageComponent } from './welcome/welcome-page/welcome-page.component';
import { UserSettingsComponent } from './main-app/components/forms/settings/user-settings/user-settings.component';
import { TableComponent } from './data-base/lists/table.component';
import { DatabaseSettingsComponent } from './data-base/forms/database-settings.component';

const routes: Routes = [
  { path: 'table/:tableName', component: TableComponent },
  {
    path: 'home', component: HomeComponent, resolve: {
      companies: CompaniesResolverService,
      notifications: NotificationResolverService
    },
    //canActivate: [IsLoggedService]
  },
  { path: 'login', redirectTo: '' },
  { path: 'chart', component: ChartComponentComponent },
  {
    path: 'databases', children: [
      
      {
        path: ':id/table/:tableName', component: TableComponent, pathMatch: 'full', runGuardsAndResolvers: "always",
        resolve: {
          columns: TablesResolverService,
          content: TableContentResolverService,
          constraints: TableConstraintResolverService
        }
      },
      {
        path: ':id/settings', component: DatabaseSettingsComponent,
        resolve: {
          database: DatabaseResolverService
        },
      },
      {
        path: ':id', component: TableListComponent,
        resolve: {
          tables: TablesResolverService,
          database: DatabaseResolverService
        },
      }


    ]
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
