import { TableActionButton } from '../../../ts/interfaces/tableActionButton';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent implements OnInit {

  message: string;
  title: string;
  buttons: TableActionButton[];
  params;

  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) { }

  ngOnInit(): void {
    this.params = this.data.params;
    this.message = this.data.message;
    this.buttons = this.data.buttons;
    this.title = this.data.title;
  }

  close() {
    this.dialogRef.close();
  }

}

export interface DialogData {
  message: string;
  title: string;
  buttons: TableActionButton[];
  params;
}