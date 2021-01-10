export interface Column {
    /**Nazwa kolumny */
    name: String;
    /**Funkcja przypisująca wyświetlaną wartość */
    assignValue?(row);
}