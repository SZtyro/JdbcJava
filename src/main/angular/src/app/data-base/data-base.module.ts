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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TableComponent } from './lists/table.component';
import { TableContentComponent } from './forms/table-content.component';
import { MatDatepickerModule } from '@angular/material/datepicker';

@NgModule({
  declarations: [TableListComponent, TableComponent, TableContentComponent],
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
    MatButtonModule,
    MatOptionModule,
    MatSelectModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule
  ]
})
export class DataBaseModule { }
