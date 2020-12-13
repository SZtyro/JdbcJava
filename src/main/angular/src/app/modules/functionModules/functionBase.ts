export abstract class FunctionBase {
  icon;
  menuTitle;
  childs?:FunctionBase[];
  routerLink?:String;
  isOpen?:boolean = false;
  isMouseOver?:boolean = false;
}
