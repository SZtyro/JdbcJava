import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {  TableComponent } from '../table/table.component';
import { FormBuilder, FormGroup,FormControl,Validators } from '@angular/forms';
import { MatDatepickerInputEvent, MatSnackBar } from '@angular/material';


@Component({
  selector: 'app-edit-modal',
  templateUrl: './edit-modal.component.html',
  styleUrls: ['./edit-modal.component.scss']
})
export class EditModalComponent implements OnInit {

  father: TableComponent ;
  //public a:String = 'AC_MGR';
  index;
  
  date = [];
  messageToUser: String;
 
  

  constructor(
    public dialogRef: MatDialogRef<EditModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    
    this.father = this.data.father;
    this.index = this.data.index;
    this.father.type.forEach((element,ind) => {
      
        if(element == "DATE")
          this.date[ind]= new FormControl(new Date(this.father.newRowContainer[ind].toString()));
    });
    this.father.allertHidden = true;
  }


  saveDate(i,event: MatDatepickerInputEvent<Date>){
    
    event.value.getMonth()
    let x:Number = event.value.getMonth() + 1;
    this.father.newRowContainer[i] = event.value.getFullYear()+"-"+x.toString()+"-"+event.value.getDate();
    
  }

  closeDialog(){
    this.dialogRef.close();
  }

  updateElem(entityIndex) {

    this.father.newRowContainer.forEach((element,index) => {
      this.father.saveToContainer(index,element);
    });  

    let querry: String = "Update " + this.father.tableName + " set ";
    let last = this.father.keys.length;
    this.father.keys.forEach((key, index) => {
      if (key != this.father.primaryKeyColumn)
        if (index != last - 1)
          querry += key + " = " + this.father.newRowContainer[index] + ", ";
        else
          querry += key + " = " + this.father.newRowContainer[index];
    });
    if(typeof(entityIndex)=='string'){
      querry += " where " + this.father.primaryKeyColumn + " = " + "'" +entityIndex + "'";
    }else
    querry += " where " + this.father.primaryKeyColumn + " = " + entityIndex;

    this.father.httpClientService.postRow(querry).subscribe(

      data => {
        this.father.messageToUser = "Row successfully updated!";
        console.log("Row successfully updated!", data);
        this.father.allertColor = 'success';
        this.data.father.ngOnInit();
        this.openSnackBar("Row succesfully updated!", "")
        this.closeDialog();
      },

      fail => {

        this.father.messageToUser = fail.error.message;
        let failMsg: String[] = [];
        failMsg = this.father.messageToUser.split("Exception:");
        this.messageToUser = failMsg[failMsg.length - 1];
        console.log("Error", fail);
        this.father.allertColor = 'danger';
        this.father.keys.forEach((key,i) => {
          this.father.newRowContainer[i] = this.data.element[key];
        });
        this.father.allertHidden = false;
      }


    );
    
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 3000,
    });
  }
}

