package org.practica.DAO;

import org.practica.drivers.JDBCSingle;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLPoiDAO implements CRUDInterfacePoi {
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
