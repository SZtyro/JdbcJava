import { Directive, HostListener, ElementRef, Renderer2 } from '@angular/core';

@Directive({
  selector: '[Widget]'
})
export class WidgetDirective {
  div;
  editButton;
  
  @HostListener('mouseover') onMouseOver() {
    
    
  }

  @HostListener('mouseout') onMouseOut() {
    
  }

  constructor(
    private element: ElementRef,
    private renderer: Renderer2,
  ) {
    this.div = renderer.createElement('div');
    this.editButton = renderer.createElement('button')
    renderer.setValue(this.editButton,"mat-raised-button");
    //renderer.setValue(this.editButton,"mat-raised-button")
    this.editButton.innerHTML = 'sdsdsdsds';
    renderer.appendChild(this.div,this.editButton);
    renderer.appendChild(element.nativeElement,this.div);
    renderer.setStyle(element.nativeElement,"background-color","red");
   }

}
