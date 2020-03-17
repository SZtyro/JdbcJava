package iouno.nwtaspringboot;

import org.springframework.web.bind.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Controller{

    @RequestMapping("/login")
    public String login(@RequestBody String[] loginData) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        // loginData[0] - url
        // loginData[1] - user
        // loginData[2] - password

        return "acces";
    }

    @RequestMapping ("/getForeignKeyColumns")
    public List<String> getForeignKeysColumns(@RequestBody String table){
        // Zwraca nazwy kolumn, ktore sa kluczamim obcymi
        return new ArrayList<String>();
    }

    @RequestMapping("/getIdList")
    public List<String> getListOfIds(@RequestBody String[] table){
        // table[0] - nazwa tebelki glownej
        // table[1] - nazwa kolumny zawierajaca klucze obce w tabeli glownej
        // Zwraca wszystkie klucze glowne kolumny do ktorej nawiazuje kolumna w tabeli glownej
        return new ArrayList<String>();
    }


    @RequestMapping("/getTable")
    public List<Map<String,Object>> getTable(@RequestBody String tableName){
        // Zwraca cala tabelke
        return new ArrayList<Map<String,Object>>();
    }



    @RequestMapping("/execute")
    void addUser(@RequestBody String sql) {
        // Wykonuje sql-owe zapytanie
    }

    @RequestMapping("/getDataType")
    public List<String> getDataType(@RequestBody String table){
        // Zwraca liste typow kolumn w danej tabeli np. "VARCHAR2","NUMBER","DATE"
        return new ArrayList<String>();
    }

    @RequestMapping("/getPrimaryKey")
    public List<String> getPrimaryKey(@RequestBody String tableName){
        // Zwraca klucze glowne w danej tabelce
        return new ArrayList<String>();
    }

    @RequestMapping("/delete")
    public void deleteRow(@RequestBody String[] deleteData){
        // deleteData[0] - nazwa tabeli
        // deleteData[1] - nazwa kolmuny
        // deleteData[2] - wartosc kolumny
        // Usuwa wiersz
    }

    @RequestMapping("/getTableNames")
    public List<String> getTableNames() {
        // Zwraca liste nazw wszystkich tebel w bazie dla zalogowanego uzytkownika
        return new ArrayList<String>();
    }
}
