import { Component, OnInit,Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { TableComponent } from '../table/table.component';


@Component({
  selector: 'app-delete-modal',
  templateUrl: './delete-modal.component.html',
  styleUrls: ['./delete-modal.component.scss']
})
export class DeleteModalComponent implements OnInit {

  father:  TableComponent;
  messageToUser:String;

  constructor(
    public dialogRef: MatDialogRef<DeleteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
   ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  

  ngOnInit() {
    this.father = this.data.fatherRef;
  }

  deleteRow(id){
    console.log(id);
    console.log(typeof(id)=='string');
    if(typeof(id)=='string'){
      this.data.service.deleteRow([this.data.tableName,this.data.primaryKeyColumn.toString(),"'"+id.toString()+"'"]).subscribe(
        data => {  
          console.log("PUT Request is successful ", data);
          this.data.fatherRef.ngOnInit();
          this.closeDialog();
        },
  
        error => {
          this.messageToUser = error.error.message;
          let failMsg: String[] = [];
          failMsg = this.messageToUser.split("Exception:");
          this.messageToUser = failMsg[failMsg.length - 1];
          console.log("Error", error);
        }
  
      );
    }else{
      this.data.service.deleteRow([this.data.tableName,this.data.primaryKeyColumn.toString(),id.toString()]).subscribe(
        data => {  
          console.log("PUT Request is successful ", data);
          this.data.fatherRef.ngOnInit();
          this.closeDialog();
        },
  
        error => {
          this.messageToUser = error.error.message;
          let failMsg: String[] = [];
          failMsg = this.messageToUser.split("Exception:");
          this.messageToUser = failMsg[failMsg.length - 1];
          console.log("Error", error);
        }
  
      );;
    }
    
    
   }

  closeDialog(){
    this.dialogRef.close();
  }
}
