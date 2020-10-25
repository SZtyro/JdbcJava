import { Component, OnInit, Inject, Type } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { SharedService } from '../../services/Shared/shared.service';
import { GmailWidgetComponent } from '../widgets/gmail-widget/gmail-widget.component';
import { ChartWidgetComponent } from '../widgets/chart-widget/chart-widget.component';
import { PhotoWidgetComponent } from '../widgets/photo-widget/photo-widget.component';
import { HomeWidget } from '../interfaces/homeWidget';

interface widgetData {
  class?:Type<unknown>;
  icon;
  description: String;
}

@Component({
  selector: 'app-widget-list-modal',
  templateUrl: './widget-list-modal.component.html',
  styleUrls: ['./widget-list-modal.component.scss']
})
export class WidgetListModalComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<WidgetListModalComponent>,
    private shared: SharedService,
    @Inject(MAT_DIALOG_DATA) public data
  ) { }

  widgets: widgetData[] = [
    { class: GmailWidgetComponent, icon: "mail_outline", description: "Manage your mailbox." },
    { class: ChartWidgetComponent, icon: "bar_chart", description: "Visualise data from connected databse." },
    { class: PhotoWidgetComponent, icon: "folder_open", description: "Container for your data." },
    { icon: "wb_sunny", description: "All about weather." },
    { icon: "shopping_cart", description: "Manage your orders." },
    { icon: "fas fa-tasks", description: "Manage your tasks." }
  ];


  ngOnInit() { 

  }


  addWidget(widgetName:string) {
    
    this.shared.homeRef.items.push({typeName: widgetName});
    //this.shared.homeRef.items.push(GmailWidgetComponent);
    this.shared.homeRef.save();
    
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
