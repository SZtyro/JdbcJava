import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TableListComponent } from './lists/table-list.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule, MatDialogModule, MatNativeDateModule, MatInputModule, MatButtonModule, MatOptionModule } from '@angular/material';
import { MatTableModule } from '@angular/material/table';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TableComponent } from './lists/table.component';
import { TableContentComponent } from './forms/table-content.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { DatabaseSettingsComponent } from './forms/database-settings.component';
import { DatabaseResolverService } from './services/guards/resolvers/database-resolver.service';
import { TableConstraintResolverService } from './services/guards/resolvers/table-constraint-resolver.service';
import { TableContentResolverService } from './services/guards/resolvers/table-content-resolver.service';
import { TablesResolverService } from './services/guards/resolvers/tables-resolver.service';

const routes: Routes = [

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

@NgModule({
  declarations: [
    TableListComponent,
    TableComponent,
    TableContentComponent,
    DatabaseSettingsComponent
  ],
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatDialogModule,
    MatNativeDateModule,
    MatTableModule,
    TranslateModule,
    MatInputModule,
    MatButtonModule,
    MatOptionModule,
    MatSelectModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    RouterModule.forChild(routes)
  ]
})
export class DataBaseModule { }
