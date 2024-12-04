package org.practica.managers;

import org.practica.DAO.MongoPoiDAO;
import org.practica.DAO.SQLPoiDAO;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

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
            try {
                currentDAO = SQLPoiDAO.getInstance();
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
                System.out.println("SQL database not available");
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

    @Override
    public int countElements() {
        return 0;
    }

    @Override
    public boolean addPoi(Poi poi) {
        return false;
    }

    @Override
    public ArrayList<Poi> listAll() {
        return null;
    }

    @Override
    public Poi listOneById(int id) {
        return null;
    }

    @Override
    public ArrayList<Poi> listByIDRange(int start, int end) {
        return null;
    }

    @Override
    public ArrayList<Poi> listByMonthModification(int month) {
        return null;
    }

    @Override
    public ArrayList<Poi> listByCity(String city) {
        return null;
    }

    @Override
    public boolean updatePoiByID(Poi poi) {
        return false;
    }

    @Override
    public int deleteAll(boolean confirm) {
        return 0;
    }

    @Override
    public int deletePoiByID(int id, boolean confirm) {
        return 0;
    }

    @Override
    public int deleteByIDRange(int start, int end, boolean confirm) {
        return 0;
    }

    @Override
    public int deleteByMonthModification(int month, boolean confirm) {
        return 0;
    }

    @Override
    public int deleteByCity(String city, boolean confirm) {
        return 0;
    }
}

