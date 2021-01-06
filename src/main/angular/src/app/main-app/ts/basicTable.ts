import { TableActionButton } from './../interfaces/tableActionButton';
import { animate, AnimationBuilder, AnimationMetadata, state, style, transition, trigger } from '@angular/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Component, Directive, ViewChild } from '@angular/core';

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
    columns: String[];
    /**Tabela przycisków akcji*/
    actions: TableActionButton[];
    /**Ilośc wierszy na jednej stronie*/
    pageSize: number = 10;

    constructor(
        protected route: ActivatedRoute,
        private animation: AnimationBuilder,
        protected router: Router
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
}