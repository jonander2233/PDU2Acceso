package org.practica.DAO;

import org.practica.drivers.JDBCSingle;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;
import org.practica.utils.Transformer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            instance =  new SQLPoiDAO();
        }
        return instance;
    }

    @Override
    public int countElements() throws SQLException {
        return listAll().size();
    }

    @Override
    public boolean addPoi(Poi poi) throws SQLException {
        String sql = "INSERT INTO jaap (PoiID,latitude,longitude,city,description,updateDate) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,poi.getPoiId());
        statement.setDouble(2,poi.getLatitude());
        statement.setDouble(3,poi.getLongitude());
        statement.setString(4,poi.getCity());
        statement.setString(5,poi.getDescription());
        statement.setDate(6, Transformer.javaDateToSQLDate(poi.getUpdateDate()));
        return false;
    }

    @Override
    public ArrayList<Poi> listAll() throws SQLException {
        String sql = "SELECT * FROM jaap order by PoiID";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        return getResult(res);
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
    private static ArrayList<Poi> getResult(ResultSet res) throws SQLException {
        ArrayList<Poi> pois = new ArrayList<>();
        while (res.next()){
            Poi poi = new Poi(res.getInt("PoiID"),res.getDouble("latitude"),res.getDouble("longitude"),res.getString("country"),res.getString("city"),res.getString("description"),res.getDate("updateDate"));
            pois.add(poi);
        }
        return pois;
    }
}
