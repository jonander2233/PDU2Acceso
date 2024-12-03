package org.practica.DAO;

import org.practica.drivers.JDBCSingle;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLPoiDAO extends DAO {
    private static Connection connection;
    private static SQLPoiDAO instance;
    private SQLPoiDAO() throws Exception {
        connection = JDBCSingle.getConnection();
    }
    public static SQLPoiDAO getInstance() throws Exception {
        if(instance == null){
            return new SQLPoiDAO();
        }
        return instance;
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
