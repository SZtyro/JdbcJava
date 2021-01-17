import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, Input, OnInit, ElementRef, Output, EventEmitter } from '@angular/core';
import { FunctionBase } from '../../../ts/interfaces/functionBase';

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.scss'],
  animations: [
    trigger('openClose', [
      // ...
      state('open', style({
        height: '*',
        overflow: 'hidden',
        opacity: '1'
      })),
      state('closed', style({
        height: '0px',
        opacity: '0',
        overflow: 'hidden'
      })),
      transition('open => closed', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
      ]),
      transition('closed => open', [
        animate('500ms cubic-bezier(0.35, 0, 0.25, 1)')
      ]),
    ]),
  ]
})
export class MenuItemComponent implements OnInit {

  @Input()
  direction: 'up' | 'down'

  @Input()
  menuItems: FunctionBase[];

  @Input()
  parent: MenuItemComponent;

  @Input()
  offset: number = 0;

  @Input()
  isTitleVisible: boolean;

  @Input()
  opacity: number = 0.0;

  @Output()
  itemClicked = new EventEmitter<FunctionBase>();

  isMouseOver: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  openList(item: FunctionBase) {
    item.isOpen = true;
    item.isMouseOver = true;
  }

  closeList(item: FunctionBase) {



    if (this.parent.isMouseOver) {
      item.isMouseOver = false
      item.isOpen = true;
    } else {
      item.isMouseOver = true;
    }

  }

  mouseLeave(item: FunctionBase) {
    this.isMouseOver = false;
    if (!item.childs) {
      item.isOpen = false;
    }
  }

  mouseEnter(item: FunctionBase) {
    if (this.parent)
      this.getMaster(this.parent).itemClicked.emit(item);
    else
      this.itemClicked.emit(item);

    item.isOpen = !item.isOpen;
  }

  getMaster(parent: MenuItemComponent) {
    if (parent.parent)
      this.getMaster(parent.parent)
    else
      return parent;
  }

}
