package org.practica;

import org.jonander2233.lib_personal.Eys;
import org.jonander2233.lib_personal.Menu;

import org.practica.DAO.MongoPoiDAO;
import org.practica.managers.DAOManager;
import org.practica.models.Poi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    private static DAOManager dm;
    public static void main(String[] args) {
        System.out.println("Connecting to database, please wait...");
        dm = DAOManager.getInstance();
        boolean exit = false;
        Menu.cambiarIdioma("en","EN");
        Eys.cambiarIdioma("en","EN");
        int options;
        do{
            options = Menu.mostrar("POI",new String[]{"Change database:Using -> " + dm.getUsingDB(),"Add Poi","List","Update Poi","Delete"},"exit");
            switch (options){
                case 0:
                    exit = true;
                    break;
                case 1:
                    System.out.println("Trying to connect to: " + dm.getNotUsingDB());
                    dm.changedb();
                    break;
                case 2:
                    addPoi();
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
                    try {
                        pois = dm.listAll();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
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

    private static void addPoi(){
        boolean exit=false;
        do{
            int id = Eys.imprimirYLeerInt("Poi id: type 0 to exit",0 , Integer.MAX_VALUE);
            if(id != 0 ){
                Double latitude = Eys.imprimirYLeerDouble("Poi latitude");
                Double longitude = Eys.imprimirYLeerDouble("Poi longitude");
                String country = Eys.imprimirYLeer("Poi Country",0,50);
                String city = Eys.imprimirYLeer("Poi city",0,50);

                Date date = Eys.imprimirYLeerDate("Poi last updated:");
                String description = Eys.imprimirYLeer("Poi description:",0,Integer.MAX_VALUE);
                Poi poi = new Poi(id,latitude,longitude,country,city,description,date);
                System.out.println(poi.toString());

                boolean add = Eys.ImprimirYleerCharSN("Confirm insertion?");
                if(add){
                    boolean success = false;
                    while (!success){
                        if(!dm.addPoi(poi)){
                            System.out.println("Error inserting Poi, duplicate ID found:" + poi.getPoiId());
                            int newId = Eys.imprimirYLeerInt("Enter new ID: or enter 0 to cancel", 0, Integer.MAX_VALUE);
                            if(newId == 0)
                                return;
                            poi.setPoiId(newId);
                        } else {
                            System.out.println("Publication successfully added.");
                            continuetext();
                            success = true;
                        }
                    }
                }
            }else {
                exit = true;
            }
        }while (!exit);
    }

    private static void continuetext() {
        Eys.imprimirYLeer("Press enter to continue",0,0);
    }
}