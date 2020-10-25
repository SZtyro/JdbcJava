import { HttpClientService } from 'src/app/services/http-client.service';
import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-chart-component',
  templateUrl: './chart-component.component.html',
  styleUrls: ['./chart-component.component.scss']
})
export class ChartComponentComponent implements OnInit {

  chart;

  constructor(private http: HttpClientService) { }

  ngOnInit(): void {

    var ctx = document.getElementById('myChart');
    this.chart = new Chart('myChart', {
      type: 'line',
      data: {
        labels: [],
        datasets: [
          {
            label: "Przyklad",
            data: []
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    });

    setInterval(() => {
      this.http.getRandom().subscribe(a => {

        this.chart.data.labels.push(new Date(a['time']).toLocaleTimeString())
        this.chart.data.datasets[0].data.push(a['value'])

        if(this.chart.data.labels.length >= 10){

          this.chart.data.labels.shift();
          this.chart.data.datasets[0].data.shift();
        }

        this.chart.update();
      })
    }, 1000);
  }


}
