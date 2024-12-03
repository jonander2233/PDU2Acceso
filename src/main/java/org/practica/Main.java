package org.practica;

import org.jonander2233.lib_personal.Eys;
import org.jonander2233.lib_personal.Menu;
import org.practica.DAO.MongoPoiDAO;
import org.practica.managers.DAOManager;
import org.practica.models.Poi;

import java.sql.Connection;
import java.util.ArrayList;

public class Main {
    private static DAOManager dm = DAOManager.getInstance();
    public static void main(String[] args) {
        boolean exit = false;
        Menu.cambiarIdioma("en","EN");
        int options;
        do{
            options = Menu.mostrar("POI",new String[]{"Change database:Using -> " + dm.getUsingDB(),"Add Poi","List","Update Poi","Delete"},"exit");
            switch (options){
                case 0:
                    exit = true;
                    break;
                case 1:
                    dm.changedb();
                    break;
                case 2:
                    break;
                case 3:
                    listPoi();
                    break;
                case 4:
                    break;
                case 5:
                    deletePoi();
                    break;
                default:
                    break;
            }
        }while (!exit);
    }
    public static void listPoi(){
        ArrayList<Poi> pois = new ArrayList<>();
        boolean back = false;
        int options;
        do {
            options = Menu.mostrar("List Poi",new String[]{"List All","List one by id","List by id range","List by month modification","List by city"},"back");
            switch (options){
                case 0:
                    back = true;
                    break;
                case 1:
                    pois = dm.listAll();
                    for (Poi poi:pois) {
                        System.out.println(poi);
                        continuetext();
                        pois.clear();
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        }while (!back);
    }
    public static void deletePoi(){
        ArrayList<Poi> pois = new ArrayList<>();
        int affectedElements;
        boolean back = false;
        int options;
        do {
            options = Menu.mostrar("Delete Poi",new String[]{"Delete All","Delete one by id","Delete by id range","Delete by month modification","Delete by city"},"back");
            switch (options){
                case 0:
                    back = true;
                    break;
                case 1:
                    affectedElements = dm.deleteAll(false);
                    if(Eys.ImprimirYleerCharSN("Do you want to confirm the deletion of " + affectedElements + " elements?"))
                        dm.deleteAll(true);
                    continuetext();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        }while (!back);
    }
    private static void continuetext() {
        Eys.imprimirYLeer("Press enter to continue",0,0);
    }
}