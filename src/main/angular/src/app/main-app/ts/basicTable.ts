import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material';
import { Directive } from '@angular/core';

@Directive()
export abstract class BasicTable {

    name: String;
    dataSource: MatTableDataSource<Object>;
    columns;

    constructor(
        protected route: ActivatedRoute
    ) { }
}