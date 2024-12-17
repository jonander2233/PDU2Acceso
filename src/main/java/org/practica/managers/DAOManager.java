package org.practica.managers;

import org.practica.DAO.MongoPoiDAO;
import org.practica.DAO.SQLPoiDAO;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;
import org.practica.models.Tpoi;

import java.sql.SQLException;
import java.util.ArrayList;

public class DAOManager implements CRUDInterfacePoi{
    private CRUDInterfacePoi currentDAO;
    private static DAOManager instance;
    private static boolean mongoAviable = true;
    private static boolean sqlAviable = true;

    private DAOManager() {
        try {
            currentDAO = MongoPoiDAO.getInstance();
        } catch (Exception e) {
            mongoAviable = false;
            System.out.println("Mongo database is unaviable trying to connect to SQL database");
            try {
                currentDAO = SQLPoiDAO.getInstance();
                System.out.println("connected to SQL database");
            } catch (Exception ex) {
                sqlAviable = false;
            }
        }
        if(!sqlAviable && !mongoAviable){
            throw new RuntimeException("Could not connect to any database");
        }

    }

    public static DAOManager getInstance() {
        if (instance == null) {
            instance = new DAOManager();
        }
        return instance;
    }

    public void changedb() {
        if (currentDAO instanceof MongoPoiDAO) {
            try {
                currentDAO = SQLPoiDAO.getInstance();
            } catch (Exception e) {
                System.out.println("SQL database is not available");
                try {
                    currentDAO = MongoPoiDAO.getInstance();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            try {
                currentDAO = MongoPoiDAO.getInstance();
            } catch (Exception e) {
                System.out.println("Mongo database not available");
                try {
                    currentDAO = SQLPoiDAO.getInstance();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void syncFromCurrentdb() {
    }

    public String getUsingDB() {
        if (currentDAO instanceof MongoPoiDAO) {
            return "MongoDB";
        }
        return "SQL";
    }
    public String getNotUsingDB(){
        if (currentDAO instanceof SQLPoiDAO) {
            return "MongoDB";
        }
        return "SQL";
    }

    @Override
    public int countElements() throws Exception {
        return currentDAO.countElements();
    }

    @Override
    public boolean addPoi(Poi poi) {
        try {
            return currentDAO.addPoi(poi);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Poi> listAll() throws Exception {
        return currentDAO.listAll();
    }

    @Override
    public Poi listOneById(int id) throws Exception {
        return currentDAO.listOneById(id);
    }

    @Override
    public ArrayList<Poi> listByIDRange(int start, int end) throws Exception {
        return currentDAO.listByIDRange(start,end);
    }

    @Override
    public ArrayList<Poi> listByMonthModification(int month) throws Exception {
        return currentDAO.listByMonthModification(month);
    }

    @Override
    public ArrayList<Poi> listByCity(String city) throws Exception {
        return currentDAO.listByCity(city);
    }

    @Override
    public ArrayList<Poi> listByCountry(String country) throws Exception {
        return currentDAO.listByCountry(country);
    }

    @Override
    public boolean updatePoiByID(Poi poi) throws Exception {
        return currentDAO.updatePoiByID(poi);
    }

    @Override
    public int deleteAll(boolean confirm) throws Exception {
        return currentDAO.deleteAll(confirm);
    }

    @Override
    public Poi deletePoiByID(int id, boolean confirm) {
        return deletePoiByID(id,confirm);
    }

    @Override
    public int deleteByIDRange(int start, int end, boolean confirm) throws Exception {
        return currentDAO.deleteByIDRange(start,end,confirm);
    }

    @Override
    public int deleteByMonthModification(int month, boolean confirm) throws Exception {
        return currentDAO.deleteByMonthModification(month,confirm);
    }

    @Override
    public int deleteByCity(String city, boolean confirm) throws Exception {
        return currentDAO.deleteByCity(city,confirm);
    }

    @Override
    public int deleteByCountry(String country, boolean confirm) throws Exception {
        return currentDAO.deleteByCountry(country,confirm);
    }
}

