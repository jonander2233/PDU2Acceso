package org.practica.managers;

import org.practica.DAO.DAO;
import org.practica.DAO.MongoPoiDAO;
import org.practica.DAO.SQLPoiDAO;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

public class DAOManager implements CRUDInterfacePoi {
    private DAO currentDAO;
    private DAOManager instance;
    private DAOManager() {
        currentDAO = MongoPoiDAO.getInstance();
    }
    public DAOManager getInstance() {
        if (instance == null) {
            instance = new DAOManager();
        }
        return instance;
    }
    public void changedb() throws Exception {
        if(currentDAO instanceof MongoPoiDAO){
            currentDAO = SQLPoiDAO.getInstance();
        }else{
            currentDAO = MongoPoiDAO.getInstance();
        }
    }
    public void syncFromCurrentdb() {
//        if(currentDAO instanceof MongoPoiDAO){
//            currentDAO = SQLPoiDAO.getInstance();
//        }else{
//            currentDAO = MongoPoiDAO.getInstance();
//        }
    }
    @Override
    public boolean createPoi() {
        return currentDAO.createPoi();
    }

    @Override
    public Poi readPoi() {
        return currentDAO.readPoi();
    }

    @Override
    public boolean updatePoi() {
        return currentDAO.updatePoi();
    }

    @Override
    public boolean deletePoi() {
        return currentDAO.deletePoi();
    }
}
