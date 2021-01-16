export interface HomeWidget {
    widgetNumber:number;
    widgetData;
    onChange();
    onResize();
    toSave();
    load(index);
    delete();
}
