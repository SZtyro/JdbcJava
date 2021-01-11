import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TableListComponent } from './lists/table-list.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule, MatDialogModule, MatNativeDateModule, MatInputModule, MatButtonModule } from '@angular/material';
import { MatTableModule } from '@angular/material/table';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TableComponent } from './lists/table.component';

@NgModule({
  declarations: [TableListComponent, TableComponent],
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
    BrowserAnimationsModule,
    MatButtonModule
  ]
})
export class DataBaseModule { }
