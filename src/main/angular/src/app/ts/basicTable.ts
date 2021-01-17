import { HttpClientService } from '../services/http-client.service';
import { TableActionButton } from './interfaces/tableActionButton';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog, MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Component, Directive, ViewChild } from '@angular/core';
import { Column } from './interfaces/column';
import { SharedService } from 'src/app/services/shared.service';

@Directive()
export abstract class BasicTable {

    @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
    @ViewChild(MatSort, { static: true }) sort: MatSort;

    /**Referencja do otwartego elementu */
    expandedElement;
    /**Nazwa strony */
    name: String;
    /**Dane do tabeli */
    dataSource: MatTableDataSource<Object>;
    /**Wyświetlane kolumny */
    columns: Column[];
    /**Kolumny do wyświetlenia */
    displayColumns: String[];
    /**Tabela przycisków akcji*/
    actions: TableActionButton[];
    /**Przyciski akcji pod tabelą */
    underActions: TableActionButton[];
    /**Ilośc wierszy na jednej stronie*/
    pageSize: number = 10;

    constructor(
        protected route: ActivatedRoute,
        protected router: Router,
        protected http: HttpClientService,
        protected shared: SharedService,
        protected dialog: MatDialog
    ) { }

    //abstract onRowClick();

    /**Funkcja wywoływana przy naciśnięciu przycisku akcji
     * @param actionId id przycisku wywołanego
     * @param row obiekt przypisany do rzędu
    */
    abstract onRowAction(actionId, row);

    /**Funkcja wywoływana przy naciśnięciu przycisku akcji pod tabelą
     * @param actionId id przycisku wywołanego
    */
    abstract onUnderAction(actionId);

    applyFilter(filterValue: string) {
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    extractColumnNames() {
        this.displayColumns = this.columns.map(x => x.name);
    }
}