import { Component, OnInit, Input, ViewContainerRef, ViewChild, ComponentFactoryResolver, Output, EventEmitter, ComponentRef, Type } from '@angular/core';
import { GridsterItem } from 'angular-gridster2';
import { item } from '../home/home.component';
import { SharedService } from 'src/app/services/Shared/shared.service';
import { HomeWidget } from '../../interfaces/homeWidget';

@Component({
  selector: 'app-widget-loader',
  templateUrl: './widget-loader.component.html',
  styleUrls: ['./widget-loader.component.scss']
})
export class WidgetLoaderComponent implements OnInit {

  @Input()
  type: item;

  @Input()
  index: number;

  constructor(public viewContainerRef: ViewContainerRef,
    private componentFactoryResolver: ComponentFactoryResolver,
    private shared: SharedService
  ) { }

  ngOnInit() {
    if (this.type != null) {
      //console.log('nastepny komponent bedzie mial nr: ' + this.index)
      let childComponent = this.componentFactoryResolver.resolveComponentFactory<HomeWidget>(this.shared.homeRef.appWidgets[this.type.typeName]);

      let ref = this.viewContainerRef.createComponent<HomeWidget>(childComponent);
      this.shared.homeRef.items[this.index].componentRef = ref;
      ref.instance["widgetData"] = this.type.data;
      ref.instance["widgetNumber"] = this.index;
      //this.shared.homeRef.items[this.index].index = this.index;
      //console.log(this.shared.homeRef.items)
      // if (this.type.index == null) {
      //   //this.type.index = this.index;
      //   //this.shared.homeRef.items[this.index] = this.type;
      //   ref.instance["widgetNumber"] = this.index;
      // }
      // else {
      //   //ref.instance["widgetNumber"] = this.type.index
      //   ref.instance["widgetNumber"] = this.index
      // }
      this.shared.homeRef.items.forEach((elem,index)=>{
        elem.index = index;
        try{
          elem.componentRef.instance.widgetNumber = index;
        }catch(e){

        }
        
        //ref.instance["widgetNumber"] = index
        //elem.data.widgetNumber = index;
        //console.log(index)
      })
      // setTimeout(()=>{
      //   this.shared.homeRef.items[this.index].index = this.index;

      // },1000)
      //console.log("//////////////////////////////////////////////////////////////////////////")
      //
      //console.log(ref.instance["widgetNumber"])
      //this.shared.homeRef.save();


      ref.instance["loaderRef"] = this.viewContainerRef;
      //console.log(this.childs)
    }



  }



}
