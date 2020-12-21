import { Component, OnInit, ElementRef, Injector, Renderer2, ViewChild, AfterContentChecked, AfterViewChecked, OnChanges, AfterViewInit, ComponentRef, ViewContainerRef } from '@angular/core';
import { GridsterItem } from 'angular-gridster2';
import { HomeWidget } from '../../interfaces/homeWidget';
import { HttpClientService } from 'src/app/services/http-client.service';
import { MatDialog } from '@angular/material';
import { ChartSettingsModalComponent } from '../../modals/chart-settings-modal/chart-settings-modal.component';
import { SharedService } from '../../../services/Shared/shared.service';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-chart-widget',
  templateUrl: './chart-widget.component.html',
  styleUrls: ['./chart-widget.component.scss'],

})
export class ChartWidgetComponent implements OnInit, GridsterItem, AfterViewInit {

  @ViewChild('mainScreen', { read: ElementRef }) elementView: ElementRef;
  @ViewChild('chart', { read: ElementRef }) chartElem: ElementRef;
  apiLoaded: boolean = false;
  widgetData = null;
  public chartData: {
    chartWrapper: google.visualization.ChartWrapper,
    chartTable: google.visualization.DataTable,
    chartColumns: string[],
    chartColumnsTypes: string[],
    chartType: string,
    chartLegendPosition: string,
    selectedTable: string,
    chartTitle: string,
    showTitle: boolean
  } = {
      chartWrapper: null,
      chartTable: null,
      chartColumns: [],
      chartColumnsTypes: [],
      chartType: "Bar",
      chartLegendPosition: "none",
      selectedTable: null,
      chartTitle: "Chart",
      showTitle: false
    };
  rawBase: Map<String, Object>[];
  rawTableNames = [];
  rawColumns;
  rawColumnTypes;
  rawTable = [];
  rawForeignColumns;
  chartLegendPositions = [
    'bottom',
    'lebeled',
    'left',
    'none',
    'right',
    'top'
  ];
  chartTypes = [
    'AnnotationChart',
    'AreaChart',
    'Bar',
    'BarChart',
    'Calendar',
    'ColumnChart',
    'ComboChart',
    'PieChart',
    'DonutChart',

  ];

  //GRIDSTER
  x: number = 0;
  y: number = 0;
  cols: number = 6;
  rows: number = 4;
  height;
  width;

  loaderRef: ViewContainerRef;

  onResize() {

    this.height = this.elementView.nativeElement.offsetHeight - 52;
    //this.height = this.height - this.height % 100;
    this.width = this.elementView.nativeElement.offsetWidth - 20;
    //console.log(this.elementView.nativeElement.offsetHeight)

    this.renderer.setAttribute(this.chartElem.nativeElement, "height", this.height);
    this.drawChart(this.chartElem.nativeElement);
    //this.toSave();
  }



  ngOnInit(): void {
    //console.log(this.widgetNumber)
    this.load(this.widgetNumber);
    //console.log("widgetData:");
    //console.log(this.widgetData);
    // console.log("Numer indexu: " + this.widgetNumber)

    google.charts.load('current', { packages: ['corechart', 'controls'] });

    google.charts.setOnLoadCallback(() => {
      this.apiLoaded = true;
      if (this.chartData != null)
        this.drawChart(this.chartElem.nativeElement);
    });
  }

  drawChart(ref) {
    if (this.apiLoaded && this.chartData != null) {
      this.chartData.chartWrapper = new google.visualization.ChartWrapper();
      this.chartData.chartTable = new google.visualization.DataTable();

      this.chartData.chartColumns.forEach((element, i) => {
        if (this.chartData.chartColumnsTypes[i].toUpperCase() == "VARCHAR2" || this.chartData.chartColumnsTypes[i].toUpperCase() == "VARCHAR")
          this.chartData.chartTable.addColumn("string", element.toString());
        else if (this.chartData.chartColumnsTypes[i].toUpperCase() == "NUMBER" || this.chartData.chartColumnsTypes[i].toUpperCase() == "INT")
          this.chartData.chartTable.addColumn("number", element.toString());
        else if (this.chartData.chartColumnsTypes[i] == "DATE")
          this.chartData.chartTable.addColumn("date", element.toString());

      });

      this.rawTable.forEach((row, index) => {
        let rowContainer = [];
        this.chartData.chartColumns.forEach((column, i) => {
          if (this.chartData.chartTable.getColumnType(i) == "date")
            rowContainer.push(new Date(row[column]));
          else
            rowContainer.push(row[column]);
        });
        this.chartData.chartTable.addRow(rowContainer);
      });

      var options = {
        width: this.width,
        height: this.height,
        chartArea: {
          height: '90%',
          width: '90%'
        },
        legend: { position: this.chartData.chartLegendPosition }
      };

      this.chartData.chartWrapper.setOptions(options);
      this.chartData.chartWrapper.setDataTable(this.chartData.chartTable);
      this.chartData.chartWrapper.setChartType(this.chartData.chartType);

      this.chartData.chartWrapper.draw(ref);
      //this.toSave();
    }

  }




  subscription: Subscription;



  constructor(
    public dataBaseService: HttpClientService,
    public dialog: MatDialog,
    private renderer: Renderer2,
    public shared: SharedService,
  ) {
    this.chartData = {
      chartWrapper: null,
      chartTable: null,
      chartColumns: [],
      chartColumnsTypes: [],
      chartType: "Bar",
      chartLegendPosition: "none",
      selectedTable: null,
      chartTitle: "Chart",
      showTitle: false
    };

    this.subscription = this.shared.getEditGrid().subscribe(isEditing => {
      //if(!isEditing)
      //this.toSave();
    })



  }
  onChange() {
    //this.toSave();
  }
  ngAfterViewInit(): void {


    //this.toSave();
  }



  widgetNumber: number = null;
  toSave() {
    console.log('sejw charta')
    let saveData = {
      chartData: this.chartData,
      widgetNumber: this.widgetNumber,
      x: this.x,
      y: this.y,
      cols: this.cols,
      rows: this.rows,
      rawTable: this.rawTable
    }

    this.shared.homeRef.items[this.widgetNumber].data = saveData;

  }

  load(index) {
    //let acc = JSON.parse(localStorage.getItem('ChartWidget' + this.widgetNumber));
    try {
      //let acc = this.shared.homeRef.items[index].data;
      let acc = this.widgetData;

      if (acc != null) {
        this.chartData = acc.chartData;
        this.widgetNumber = acc.widgetNumber,
          this.x = acc.x;
        this.y = acc.y;
        this.cols = acc.cols;
        this.rows = acc.rows;
        this.rawTable = acc.rawTable;

        this.widgetData = acc;
      } else {
        let saveData = {
          chartData: this.chartData,
          widgetNumber: this.widgetNumber,
          x: this.x,
          y: this.y,
          cols: this.cols,
          rows: this.rows,
          rawTable: this.rawTable
        }

        this.widgetData = saveData;
      }
    } catch (e) {

      let saveData = {
        chartData: this.chartData,
        widgetNumber: this.widgetNumber,
        x: this.x,
        y: this.y,
        cols: this.cols,
        rows: this.rows,
        rawTable: this.rawTable
      }
      this.shared.homeRef.items[index].data = saveData;
      this.widgetData = saveData;
    }

  }




  openDialog(): void {
    const dialogRef = this.dialog.open(ChartSettingsModalComponent, {
      //width: '250px',
      data: { father: this }
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }


}
