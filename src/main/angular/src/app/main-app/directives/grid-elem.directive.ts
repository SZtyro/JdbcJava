import { Directive, ElementRef, OnInit, Renderer2, Input, ViewContainerRef, ComponentFactoryResolver, ComponentRef, ViewChild, HostListener } from '@angular/core';
import { AnimationBuilder, style, animate, transition, keyframes } from '@angular/animations';
import { SharedService } from 'src/app/services/shared.service';
import { GridsterItemComponent } from 'angular-gridster2';





@Directive({
  selector: '[GridElem]'
})
export class GridElemDirective implements OnInit {
  
  @ViewChild('mainScreen', { read: ElementRef }) elementView: ElementRef;
  ele;

  ngOnInit(): void {
    //console.log("offset: " + this.host.$item.x);
    this.renderer.setStyle(this.element.nativeElement, "border-radius", "5px");
    this.renderer.setStyle(this.element.nativeElement, "padding", "10px");
    this.renderer.addClass(this.element.nativeElement, "mat-elevation-z8");
    
    this.ele = document.getElementById("item");
    
    this.makeAnimation(this.element.nativeElement)
  }

  constructor(
    private element: ElementRef,
    private renderer: Renderer2,
    private _builder: AnimationBuilder,
    private shared: SharedService,
    private host: GridsterItemComponent
  ) {

  }

  makeAnimation(element: any) {
    
    // first define a reusable animation
    const myAnimation = this._builder.build([
      // transition('initialState => finalState', [
      //   animate('1500ms ease-in')
      // ]),
     
      //animate(1000, style({  opacity: '50%'}))
      
    ]);

    // use the returned factory object to create a player
    const player = myAnimation.create(element);

    //if (this.shared.homeRef.editGrid)
      player.play();
    //else
    //  player.pause();
  }

}
