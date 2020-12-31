export abstract class FunctionBase {
  icon;
  name;
  childs?:FunctionBase[];
  routerLink?:String;
  isOpen?:boolean = false;
  isMouseOver?:boolean = false;
}
