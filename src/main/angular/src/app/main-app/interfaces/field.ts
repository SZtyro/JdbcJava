import { FieldType } from "../components/enums/fieldType";

export interface Field {
    /**Nazwa pola */
    name: String;
    /**Typ pola */
    type: FieldType;
    /**Klasy pola */
    class?: String;
    /**Wartość pola */
    value?;
    /**Opcje w przypadku selecta */
    options?: { name?: String, value }[]
    /**Funckja porównywania w przypadku selecta */
    compareWith?;
    disabled?: boolean;
    required?: boolean;
}