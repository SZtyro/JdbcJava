import { Component, OnInit, Inject, Type } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { HomeComponent } from '../../components/home/home.component';
import { SharedService } from 'src/app/services/Shared/shared.service';
import { GmailWidgetComponent } from '../../widgets/gmail-widget/gmail-widget.component';
import { ChartWidgetComponent } from '../../widgets/chart-widget/chart-widget.component';
import { item } from 'src/app/main-app/components/home/home.component';

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

  constructor(
    public dialogRef: MatDialogRef<WidgetListModalComponent>,
    private shared: SharedService,
    @Inject(MAT_DIALOG_DATA) public data
  ) { }

  widgets: widgetData[] = [
    { class: GmailWidgetComponent, icon: "mail_outline", description: "Manage your mailbox." },
    { class: ChartWidgetComponent, icon: "bar_chart", description: "Visualise data from connected databse." },
    { icon: "wb_sunny", description: "All about weather." },
    { icon: "shopping_cart", description: "Manage your orders." },
    { icon: "fas fa-tasks", description: "Manage your tasks." }
  ];


  ngOnInit() {

  }


  addWidget(widgetName:string) {
    if(this.shared.homeRef.items != null){
      let num = this.shared.homeRef.items.push({typeName: widgetName});

    }
    else{
      this.shared.homeRef.items = []
      let num = this.shared.homeRef.items.push({typeName: widgetName});

    }

    this.shared.homeRef.save();

  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
