package org.practica;

import org.jonander2233.lib_personal.Eys;
import org.jonander2233.lib_personal.Menu;
import org.practica.DAO.MongoPoiDAO;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        Menu.cambiarIdioma("en","EN");
        MongoPoiDAO mpd = MongoPoiDAO.getInstance();
        String currentDB = "MongoDB";
        int options;
        do{
            options = Menu.mostrar("POI",new String[]{"Change database:Using -> " + currentDB,""},"exit");
            switch (options){
                case 0:
                    exit = true;
                    break;
                case 1:
                    if(currentDB.equals("MongoDB")){
                        currentDB = "SQL";
                    }else{
                        currentDB = "MongoDB";
                    }
                    break;
                case 2:
                    exit = true;
                    break;
            }
        }while (!exit);
    }
}