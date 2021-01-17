export interface FunctionBase {
  icon;
  name;
  childs?:FunctionBase[];
  routerLink?:String;
  isOpen?:boolean;
  isMouseOver?:boolean;
  extras?;
}
