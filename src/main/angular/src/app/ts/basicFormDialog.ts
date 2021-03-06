import { SharedService } from 'src/app/services/shared.service';
import { Directive, Inject } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { HttpClientService } from "src/app/services/http-client.service";
import { Field } from "./interfaces/field";
import { TableActionButton } from "./interfaces/tableActionButton";
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';


@Directive()
export abstract class BasicFormDialog {


    /**Pola formularza */
    fields: Field[];
    /**Nazwa strony */
    name: String;
    /**Tabela przycisków akcji*/
    actions: TableActionButton[];

    constructor(
        protected route: ActivatedRoute,
        protected router: Router,
        protected http: HttpClientService,
        protected shared: SharedService,
        @Inject(MAT_DIALOG_DATA) public data,
        public dialogRef: MatDialogRef<any>
    ) { }

    //abstract onRowClick();

    /**Funkcja wywoływana przy naciśnięciu przycisku akcji w rzędzie tabeli
     * @param actionId id przycisku wywołanego
     * @param row obiekt przypisany do rzędu
    */
    onAction(actionId){
        throw new Error('Method not implemented.');
    };

}