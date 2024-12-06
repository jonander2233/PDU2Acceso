package org.practica;

import org.jonander2233.lib_personal.Eys;
import org.jonander2233.lib_personal.Menu;
import org.practica.managers.DAOManager;
import org.practica.models.Poi;

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
            try {
                options = Menu.mostrar("POI---nElements(" + dm.countElements() + ")",new String[]{"Change database:Using -> " + dm.getUsingDB(),"Add Poi","List","Update Poi","Delete"},"exit");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
                    updatePoi();
                    break;
                case 5:
                    try {
                        deletePoi();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    break;
            }
        }while (!exit);
    }
    public static void listPoi(){
        boolean back = false;
        int options;
        do {
            options = Menu.mostrar("List Poi",new String[]{"List All","List one by id","List by id range","List by month modification","List by city","List by Country"},"back");
            switch (options){
                case 0:
                    back = true;
                    break;
                case 1:
                    listAllPoi();
                    break;
                case 2:
                    listById();
                    break;
                case 3:
                    listByIdRange();
                    break;
                case 4:
                    listByMonth();
                    break;
                case 5:
                    try {
                        listByCity();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 6:
                    listByCountry();
                    break;
                default:
                    break;
            }
        }while (!back);
    }
    public static void deletePoi() throws SQLException {
        ArrayList<Poi> pois = new ArrayList<>();
        int affectedElements;
        boolean back = false;
        int options;
        do {
            options = Menu.mostrar("Delete Poi",new String[]{"Delete All","Delete one by id","Delete by id range","Delete by month modification","Delete by city","Delete by Country"},"back");
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
                    deletePoiByid();
                    break;
                case 3:
                    deletePoiByidRange();
                    break;
                case 4:
                    deletePoiByMonthModification();
                    break;
                case 5:
                    deletePoiByCity();
                    break;
                case 6:
                    deletePoiByCountry();
                    break;
                default:
                    break;
            }
        }while (!back);
    }



    //list

    private static void listByCountry(){
        String country = Eys.imprimirYLeer("Enter Country",1,Integer.MAX_VALUE);
        System.out.println(dm.listByCountry(country));
        continuetext();
    }
    private static void listByCity() throws SQLException {
        String city = Eys.imprimirYLeer("Enter city",1,50);
        System.out.println(dm.listByCity(city));
        continuetext();
    }
    private static void listByMonth(){
        int month = Eys.imprimirYLeerInt("Month 1 to 12",1,12);
        if(dm.listByMonthModification(month) != null){
            System.out.println(dm.listByMonthModification(month).toString());
        }else {
            System.out.println("No Results founded");
        }
    }
    private static void listByIdRange(){
        int min = Eys.imprimirYLeerInt("id min",1,Integer.MAX_VALUE);
        int max = Eys.imprimirYLeerInt("id max", min,Integer.MAX_VALUE);
        try {
            if(dm.listByIDRange(min,max) != null){
                System.out.println(dm.listByIDRange(min,max).toString());
            } else {
                System.out.println("No Results founded");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listById() throws SQLException {
        int id = Eys.imprimirYLeerInt("Poi id: type 0 to exit",0,Integer.MAX_VALUE);
        if(id == 0) return;
        if(dm.listOneById(id) == null){
            System.out.println("No results found");
        } else {
            System.out.println("Poi: " + dm.listOneById(id));
        }
        continuetext();
    }
    private static void listAllPoi(){
        ArrayList<Poi> pois = new ArrayList<>();
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
    }



    //delete





    private static void deletePoiByCity() throws SQLException {
        String city = Eys.imprimirYLeer("Enter city",1,50);
        boolean confirm = Eys.ImprimirYleerCharSN("Are you sure you want to delete " + dm.deleteByCity(city,false)+ " items?");
        if(confirm){
            dm.deleteByCity(city,true);
        }
        continuetext();
    }

    private static void deletePoiByCountry() throws SQLException {
        String country = Eys.imprimirYLeer("Enter Country",1,50);
        boolean confirm = Eys.ImprimirYleerCharSN("Are you sure you want to delete " + dm.deleteByCountry(country,false)+ " items?");
        if(confirm){
            dm.deleteByCountry(country,true);
        }
        continuetext();
    }

    private static void deletePoiByMonthModification() throws SQLException {
        int month = Eys.imprimirYLeerInt("Month 1 to 12",1,12);
        if(dm.listByMonthModification(month) != null){
            boolean confirm = Eys.ImprimirYleerCharSN("Are you sure you want to delete "+ dm.deleteByMonthModification(month,false) +" items ?");
            if(confirm){
                dm.deleteByMonthModification(month,true);
            }
        }else {
            System.out.println("No Results founded");
        }
        continuetext();
    }
    private static void deletePoiByidRange() throws SQLException {
        int idMin = Eys.imprimirYLeerInt("id min",1,Integer.MAX_VALUE);
        int idMax = Eys.imprimirYLeerInt("id max", idMin,Integer.MAX_VALUE);
        if(dm.listByIDRange(idMin,idMax) == null){
            System.out.println("No results found");
        } else {
            System.out.println(dm.listByIDRange(idMin,idMax));
            boolean confirm = Eys.ImprimirYleerCharSN("Are you sure you want to delete them?");
            if(confirm){
                dm.deleteByIDRange(idMin,idMax,true);
            }
        }
        continuetext();
    }

    private static void deletePoiByid() throws SQLException {
        int id = Eys.imprimirYLeerInt("Poi id: type 0 to exit",0,Integer.MAX_VALUE);
        if(id == 0) return;
        boolean confirm = Eys.ImprimirYleerCharSN("Confirm deletion of: " + dm.listOneById(id).toString() + "?");
        if(confirm)
            dm.deletePoiByID(id,true);
    }



    //update



    private static void updatePoi(){
        int id = Eys.imprimirYLeerInt("Poi id: type 0 to exit", 0, Integer.MAX_VALUE);
        if (id != 0) {
            Poi poi = null;
            try {
                poi = dm.listOneById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (poi != null) {
                System.out.println(poi);
                boolean update = Eys.ImprimirYleerCharSN("Do you want to update this Poi?");
                if (update) {
                    Double latitude = Eys.imprimirYLeerDouble("Poi latitude");
                    Double longitude = Eys.imprimirYLeerDouble("Poi longitude");
                    String country = Eys.imprimirYLeer("Poi Country", 0, 50);
                    String city = Eys.imprimirYLeer("Poi city", 0, 50);
                    Date updateDate = Eys.imprimirYLeerDate("Poi last updated:");
                    String description = Eys.imprimirYLeer("Poi description:", 0, Integer.MAX_VALUE);
                    if(Eys.ImprimirYleerCharSN("Apply changes?")){
                        dm.deletePoiByID(id,true);
                        dm.addPoi(new Poi(id,latitude,longitude,country,city,description,updateDate));
                    }
                }
            } else {
                System.out.println("id: " + id + " doesn't contain any Poi");
            }
        }
    }



    //create



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
                            System.out.println("Poi successfully added.");
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