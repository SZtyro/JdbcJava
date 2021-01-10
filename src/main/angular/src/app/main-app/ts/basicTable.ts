import { HttpClientService } from './../../services/http-client.service';
import { TableActionButton } from './../interfaces/tableActionButton';
import { animate, AnimationBuilder, AnimationMetadata, state, style, transition, trigger } from '@angular/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Component, Directive, ViewChild } from '@angular/core';
import { Column } from '../interfaces/column';

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
    /**Ilośc wierszy na jednej stronie*/
    pageSize: number = 10;

    constructor(
        protected route: ActivatedRoute,
        protected router: Router,
        protected http: HttpClientService
    ) { }

    //abstract onRowClick();

    /**Funkcja wywoływana przy naciśnięciu przycisku akcji w rzędzie tabeli
     * @param actionId id przycisku wywołanego
     * @param row obiekt przypisany do rzędu
    */
    abstract onAction(actionId, row);

    applyFilter(filterValue: string) {
        this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    extractColumnNames() {
        this.displayColumns = this.columns.map(x => x.name);
    }
}