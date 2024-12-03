package org.practica.managers;

import org.practica.DAO.MongoPoiDAO;
import org.practica.DAO.SQLPoiDAO;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

public class DAOManager implements CRUDInterfacePoi {
    private MongoPoiDAO mpd;
    private SQLPoiDAO spd;
    private DAOManager instance;
    private DAOManager() {

    }

    @Override
    public boolean createPoi() {
        return false;
    }

    @Override
    public Poi readPoi() {
        return null;
    }

    @Override
    public boolean updatePoi() {
        return false;
    }

    @Override
    public boolean deletePoi() {
        return false;
    }
}
