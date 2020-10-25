import { Component, OnInit, Inject, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ChartWidgetComponent } from '../../widgets/chart-widget/chart-widget.component';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { SharedService } from '../../../services/Shared/shared.service';

@Component({
  selector: 'app-chart-settings-modal',
  templateUrl: './chart-settings-modal.component.html',
  styleUrls: ['./chart-settings-modal.component.scss']
})
export class ChartSettingsModalComponent implements OnInit, AfterViewInit {

  @ViewChild('chart', { read: ElementRef }) chartElem: ElementRef;

  father: ChartWidgetComponent;
  shared: SharedService;
  constructor(

    public dialogRef: MatDialogRef<ChartSettingsModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data
  ) {
    this.father = data.father;
    this.shared = this.father.shared;
  }
  ngAfterViewInit(): void {
    this.drawChart(this.chartElem.nativeElement);

  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {

  }

  saveChart(){
    this.father.drawChart(this.father.chartElem.nativeElement);
    this.father.toSave();
  }

  drawChart(ref) {
    if (this.father.apiLoaded && this.father.chartData!=null) {
      this.father.chartData.chartWrapper = new google.visualization.ChartWrapper();
      this.father.chartData.chartTable = new google.visualization.DataTable();

      this.father.chartData.chartColumns.forEach((element, i) => {
        if (this.father.chartData.chartColumnsTypes[i].toUpperCase() == "VARCHAR2" || this.father.chartData.chartColumnsTypes[i].toUpperCase() == "VARCHAR")
          this.father.chartData.chartTable.addColumn("string", element.toString());
        else if (this.father.chartData.chartColumnsTypes[i].toUpperCase() == "NUMBER" || this.father.chartData.chartColumnsTypes[i].toUpperCase() == "INT")
          this.father.chartData.chartTable.addColumn("number", element.toString());
        else if (this.father.chartData.chartColumnsTypes[i] == "DATE")
          this.father.chartData.chartTable.addColumn("date", element.toString());

      });

      this.father.rawTable.forEach((row, index) => {
        let rowContainer = [];
        this.father.chartData.chartColumns.forEach((column, i) => {
          if (this.father.chartData.chartTable.getColumnType(i) == "date")
            rowContainer.push(new Date(row[column]));
          else
            rowContainer.push(row[column]);
        });
        this.father.chartData.chartTable.addRow(rowContainer);
      });

      var options = {
        width: '100%',
        height: '100%',
        chartArea: {
          height: '90%',
          width: '90%'
        },
        legend: { position: this.father.chartData.chartLegendPosition }
      };

      this.father.chartData.chartWrapper.setOptions(options);
      this.father.chartData.chartWrapper.setDataTable(this.father.chartData.chartTable);
      this.father.chartData.chartWrapper.setChartType(this.father.chartData.chartType);

      this.father.chartData.chartWrapper.draw(ref);

    }

  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.father.chartData.chartColumns, event.previousIndex, event.currentIndex);
    //this.father.myColumnNames.next(this.father.colss);
    moveItemInArray(this.father.chartData.chartColumnsTypes, event.previousIndex, event.currentIndex)
    //console.log(this.father.chartData.chartColumns);
    //console.log(this.father.chartData.chartColumnsTypes);
    //this.father.drawChart(this.father.chartElem.nativeElement);
    this.drawChart(this.chartElem.nativeElement);
  }


  reset() {
    this.father.chartData = {
      chartWrapper: null,
      chartTable: null,
      chartColumns: [],
      chartColumnsTypes: [],
      chartType: "Bar",
      chartLegendPosition: "none",
      selectedTable: null,
      chartTitle: "Chart",
      showTitle: false
    }
    this.father.rawTable = [];
    this.father.toSave();


  }

  getForeignColumns() {
    this.father.dataBaseService.getForeignKeyColumns("'" + this.father.chartData.selectedTable + "'").subscribe(foreignColumns => { this.father.rawForeignColumns = foreignColumns })
    this.father.rawForeignColumns.forEach(element => {
      this.father.dataBaseService.getIds([this.father.chartData.selectedTable, element]).subscribe(id => { console.log(id) })
    });
  }

  selectLegendPosition(legendPosition) {
    this.father.chartData.chartLegendPosition = legendPosition;
    //this.father.drawChart(this.father.chartElem.nativeElement);
    this.drawChart(this.chartElem.nativeElement);
  }

  setChartType(chartType) {
    this.father.chartData.chartType = chartType;
    //this.father.drawChart(this.father.chartElem.nativeElement);
    this.drawChart(this.chartElem.nativeElement);
  }

  getTables() {
    this.father.dataBaseService.getTableNames().subscribe(tableNames => { this.father.rawTableNames = tableNames });
  }

  getRawTable() {
    this.father.dataBaseService.getTable(this.father.chartData.selectedTable).subscribe(rawTable => { this.father.rawTable = rawTable })
  }

  getColumns() {
    this.father.rawColumns = Object.keys(this.father.rawTable[0])
  }

  getColumnTypes() {
    this.father.dataBaseService.getType(this.father.chartData.selectedTable).subscribe(columnTypes => { this.father.rawColumnTypes = columnTypes })
  }

  selectTable(selectedTableName) {
    this.father.chartData.selectedTable = selectedTableName;
    this.getRawTable();
    this.getColumnTypes();

  }

  addChartColumn(columnName, i) {
    this.father.chartData.chartColumns.push(columnName);
    this.father.chartData.chartColumnsTypes.push(this.father.rawColumnTypes[i]);
    //console.log(this.father.data.chartColumnsTypes);
    //this.father.drawChart(this.father.chartElem.nativeElement);
    this.drawChart(this.chartElem.nativeElement);
  }

  removeChartColumn(columnName, i) {

    //console.log (this.father.chartData.chartColumns.indexOf(columnName));
    this.father.chartData.chartColumns.splice(this.father.chartData.chartColumns.indexOf(columnName), 1);
    this.father.chartData.chartColumnsTypes.splice(i, 1);
    //this.father.drawChart(this.father.chartElem.nativeElement);
    this.drawChart(this.chartElem.nativeElement);
  }

}
