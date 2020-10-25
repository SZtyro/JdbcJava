import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar, MatDatepickerInputEvent } from '@angular/material';
import { FormBuilder, FormControl } from '@angular/forms';
import {  TableComponent } from '../table/table.component';

@Component({
  selector: 'app-add-modal',
  templateUrl: './add-modal.component.html',
  styleUrls: ['./add-modal.component.scss']
})
export class AddModalComponent implements OnInit {

  father:  TableComponent;
  date = [];
  messageToUser: String;


  constructor(
    public dialogRef: MatDialogRef<AddModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private _snackBar: MatSnackBar) { }

  ngOnInit() {
    this.father = this.data.father;
    //this.index = this.data.index;
    this.father.type.forEach((element, ind) => {

      if (element == "DATE")
        this.date[ind] = new FormControl(new Date(this.father.newRowContainer[ind].toString()));
    });
    this.father.allertHidden = true;

    this.father.prepareNewContainer();
  }

  saveDate(i, event: MatDatepickerInputEvent<Date>) {

    event.value.getMonth()
    let x: Number = event.value.getMonth() + 1;
    this.father.newRowContainer[i] = event.value.getFullYear() + "-" + x.toString() + "-" + event.value.getDate();

  }

  closeDialog(){
    this.dialogRef.close();
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 3000,
    });
  }

  sendNewElem() {
    console.log(this.father.newRowContainer);
    console.log(this.father.newRowContainer[0])
    this.father.newRowContainer.forEach((element, index) => {
      if (this.father.newRowContainer[index] == "")
        this.father.newRowContainer[index] = "null";
      else
        this.father.saveToContainer(index, element);
    });


    let querry: String = "Insert into " + this.father.tableName + " values(" + this.father.newRowContainer + ")";
    console.log(querry);
    this.father.httpClientService.postRow(querry).subscribe(

      data => {
        this.messageToUser = "New row successfully inserted!";
        console.log("New row successfully inserted! ", data);
        this.data.father.ngOnInit();
        this.openSnackBar("Row succesfully inserted!", "")
        this.closeDialog();
      },

      fail => {

        this.messageToUser = fail.error.message;
        let failMsg: String[] = [];
        failMsg = this.messageToUser.split("Exception:");
        this.messageToUser = failMsg[failMsg.length - 1];
        console.log("Error", fail);
        this.father.newRowContainer.forEach((element, index) => {
          if (this.father.newRowContainer[index] == "null")
            this.father.newRowContainer[index] = "";
          else if (this.father.type[index] == "DATE" || this.father.type[index] == "VARCHAR2") {
            this.father.newRowContainer[index] = this.father.newRowContainer[index].slice(1, this.father.newRowContainer[index].length - 1);
          }

        });

      }


    );
    this.father.allertHidden = false;
  }

}
