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
        try {
            System.out.println("Connecting to database, please wait...");
            dm = DAOManager.getInstance();
            Menu.cambiarIdioma("en", "EN");
            Eys.cambiarIdioma("en", "EN");
            runMenu();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runMenu() throws SQLException {
        boolean exit = false;
        do {
            int options = Menu.mostrar("POI---nElements(" + dm.countElements() + ")", new String[]{
                    "Change database: Using -> " + dm.getUsingDB(),
                    "Add Poi",
                    "List",
                    "Update Poi",
                    "Delete"
            }, "exit");

            switch (options) {
                case 0 -> exit = true;
                case 1 -> changeDatabase();
                case 2 -> addPoi();
                case 3 -> listPoi();
                case 4 -> updatePoi();
                case 5 -> deletePoi();
            }
        } while (!exit);
    }

    private static void changeDatabase() {
        System.out.println("Trying to connect to: " + dm.getNotUsingDB());
        dm.changedb();
    }

    private static void listPoi() throws SQLException {
        boolean back = false;
        do {
            int options = Menu.mostrar("List Poi", new String[]{
                    "List All",
                    "List one by id",
                    "List by id range",
                    "List by month modification",
                    "List by city",
                    "List by Country"
            }, "back");

            switch (options) {
                case 0 -> back = true;
                case 1 -> listAllPoi();
                case 2 -> listById();
                case 3 -> listByIdRange();
                case 4 -> listByMonth();
                case 5 -> listByCity();
                case 6 -> listByCountry();
            }
        } while (!back);
    }

    private static void deletePoi() throws SQLException {
        boolean back = false;
        do {
            int options = Menu.mostrar("Delete Poi", new String[]{
                    "Delete All",
                    "Delete one by id",
                    "Delete by id range",
                    "Delete by month modification",
                    "Delete by city",
                    "Delete by Country"
            }, "back");

            switch (options) {
                case 0 -> back = true;
                case 1 -> deleteAllPois();
                case 2 -> deletePoiById();
                case 3 -> deletePoiByIdRange();
                case 4 -> deletePoiByMonthModification();
                case 5 -> deletePoiByCity();
                case 6 -> deletePoiByCountry();
            }
        } while (!back);
    }

    private static void deleteAllPois() throws SQLException {
        int affectedElements = dm.deleteAll(false);
        if (Eys.ImprimirYleerCharSN("Do you want to confirm the deletion of " + affectedElements + " elements?"))
            dm.deleteAll(true);
        continuetext();
    }

    private static void listAllPoi() throws SQLException {
        ArrayList<Poi> pois = dm.listAll();
        listPoiIO(pois);
        continuetext();
    }

    private static void listById() throws SQLException {
        int id = Eys.imprimirYLeerInt("Poi id: type 0 to exit", 0, Integer.MAX_VALUE);
        if (id != 0) {
            Poi poi = dm.listOneById(id);
            if (poi != null) {
                System.out.println("Poi: " + poi);
            } else {
                System.out.println("No results found");
            }
            continuetext();
        }
    }

    private static void listByIdRange() throws SQLException {
        int min = Eys.imprimirYLeerInt("id min", 1, Integer.MAX_VALUE);
        int max = Eys.imprimirYLeerInt("id max", min, Integer.MAX_VALUE);
        listPoiIO(dm.listByIDRange(min,max));
        continuetext();
    }

    private static void listByMonth() throws SQLException {
        int month = Eys.imprimirYLeerInt("Month 1 to 12", 1, 12);
        listPoiIO(dm.listByMonthModification(month));
        continuetext();
    }

    private static void listByCity() throws SQLException {
        String city = Eys.imprimirYLeer("Enter city", 1, 50);
        listPoiIO(dm.listByCity(city));
        continuetext();
    }

    private static void listByCountry() throws SQLException {
        String country = Eys.imprimirYLeer("Enter Country", 1, Integer.MAX_VALUE);
        listPoiIO(dm.listByCountry(country));
        continuetext();
    }

    private static void deletePoiById() throws SQLException {
        int id = Eys.imprimirYLeerInt("Poi id: type 0 to exit", 0, Integer.MAX_VALUE);
        if (id != 0) {
            Poi poi = dm.listOneById(id);
            if (poi != null && Eys.ImprimirYleerCharSN("Confirm deletion of: " + poi + "?")) {
                dm.deletePoiByID(id, true);
            }
        }
    }

    private static void deletePoiByIdRange() throws SQLException {
        int idMin = Eys.imprimirYLeerInt("id min", 1, Integer.MAX_VALUE);
        int idMax = Eys.imprimirYLeerInt("id max", idMin, Integer.MAX_VALUE);
        ArrayList<Poi> pois = dm.listByIDRange(idMin, idMax);
        if (pois != null && !pois.isEmpty() && Eys.ImprimirYleerCharSN("Are you sure you want to delete these Pois?")) {
            dm.deleteByIDRange(idMin, idMax, true);
        }
    }

    private static void deletePoiByCity() throws SQLException {
        String city = Eys.imprimirYLeer("Enter city", 1, 50);
        if (Eys.ImprimirYleerCharSN("Are you sure you want to delete " + dm.deleteByCity(city, false) + " items?")) {
            dm.deleteByCity(city, true);
        }
        continuetext();
    }

    private static void deletePoiByCountry() throws SQLException {
        String country = Eys.imprimirYLeer("Enter Country", 1, 50);
        if (Eys.ImprimirYleerCharSN("Are you sure you want to delete " + dm.deleteByCountry(country, false) + " items?")) {
            dm.deleteByCountry(country, true);
        }
        continuetext();
    }

    private static void deletePoiByMonthModification() throws SQLException {
        int month = Eys.imprimirYLeerInt("Month 1 to 12", 1, 12);
        if (Eys.ImprimirYleerCharSN("Are you sure you want to delete " + dm.deleteByMonthModification(month, false) + " items?")) {
            dm.deleteByMonthModification(month, true);
        }
    }

    private static void updatePoi() throws SQLException {
        int id = Eys.imprimirYLeerInt("Poi id: type 0 to exit", 0, Integer.MAX_VALUE);
        if (id != 0) {
            Poi poi = dm.listOneById(id);
            if (poi != null) {
                System.out.println(poi);
                if (Eys.ImprimirYleerCharSN("Do you want to update this Poi?")) {
                    Double latitude = Eys.imprimirYLeerDouble("Poi latitude");
                    Double longitude = Eys.imprimirYLeerDouble("Poi longitude");
                    String country = Eys.imprimirYLeer("Poi Country", 0, 50);
                    String city = Eys.imprimirYLeer("Poi city", 0, 50);
                    Date updateDate = Eys.imprimirYLeerDate("Poi last updated:");
                    String description = Eys.imprimirYLeer("Poi description:", 0, Integer.MAX_VALUE);
                    if (Eys.ImprimirYleerCharSN("Apply changes?")) {
                        dm.updatePoiByID(new Poi(id, latitude, longitude, country, city, description, updateDate));
                    }
                }
            } else {
                System.out.println("id: " + id + " doesn't contain any Poi");
            }
        }
    }

    private static void addPoi() throws SQLException {
        boolean exit = false;
        do {
            int id = Eys.imprimirYLeerInt("Poi id: type 0 to exit", 0, Integer.MAX_VALUE);
            if (id == 0) {
                exit = true;
            } else {
                Double latitude = Eys.imprimirYLeerDouble("Poi latitude");
                Double longitude = Eys.imprimirYLeerDouble("Poi longitude");
                String country = Eys.imprimirYLeer("Poi Country", 0, 50);
                String city = Eys.imprimirYLeer("Poi city", 0, 50);
                Date date = Eys.imprimirYLeerDate("Poi last updated:");
                String description = Eys.imprimirYLeer("Poi description:", 0, Integer.MAX_VALUE);
                Poi poi = new Poi(id, latitude, longitude, country, city, description, date);

                boolean success = false;
                while (!success) {
                    if (!dm.addPoi(poi)) {
                        System.out.println("Error inserting Poi, duplicate ID found: " + poi.getPoiId());
                        id = Eys.imprimirYLeerInt("Enter new ID: or enter 0 to cancel", 0, Integer.MAX_VALUE);
                        if (id == 0) return;
                        poi.setPoiId(id);
                    } else {
                        System.out.println("Poi successfully added.");
                        success = true;
                    }
                }
            }
        } while (!exit);
    }

    private static void continuetext() {
        Eys.imprimirYLeer("Press enter to continue", 0, 0);
    }

    private static void listPoiIO(ArrayList<Poi> pois) {
        if (pois != null && !pois.isEmpty()) {
            for (int i = 0; i < pois.size(); i++) {
                System.out.println(pois.get(i).toString() + "\n");
            }
        } else {
            System.out.println("No results found");
        }
    }
}
