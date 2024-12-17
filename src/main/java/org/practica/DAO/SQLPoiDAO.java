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
        if (instance == null) {
            instance = new SQLPoiDAO();
        }
        return instance;
    }

    @Override
    public int countElements() throws SQLException {
        return listAll().size();
    }

    @Override
    public boolean addPoi(Poi poi) throws SQLException {
        Poi poi1 = listOneById(poi.getPoiId());
        if (poi1 == null) {
            String sql = "INSERT INTO jaap (PoiID, latitude, longitude, city, country, description, updateDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, poi.getPoiId());
            if (poi.getLatitude() == null) {
                statement.setNull(2, java.sql.Types.DOUBLE);
            } else {
                statement.setDouble(2, poi.getLatitude());
            }
            if (poi.getLongitude() == null) {
                statement.setNull(3, java.sql.Types.DOUBLE);
            } else {
                statement.setDouble(3, poi.getLongitude());
            }
            if (poi.getCity() == null || poi.getCity().isEmpty()) {
                statement.setNull(4, java.sql.Types.VARCHAR);
            } else {
                statement.setString(4, poi.getCity());
            }
            if (poi.getCountry() == null || poi.getCountry().isEmpty()) {
                statement.setNull(5, java.sql.Types.VARCHAR);
            } else {
                statement.setString(5, poi.getCountry());
            }
            if (poi.getDescription() == null || poi.getDescription().isEmpty()) {
                statement.setNull(6, java.sql.Types.VARCHAR);
            } else {
                statement.setString(6, poi.getDescription());
            }
            if (poi.getUpdateDate() == null) {
                statement.setNull(7, java.sql.Types.DATE);
            } else {
                statement.setDate(7, Transformer.javaDateToSQLDate(poi.getUpdateDate()));
            }
            statement.execute();
            statement.close();
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Poi> listAll() throws SQLException {
        String sql = "SELECT * FROM jaap ORDER BY PoiID";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        ArrayList<Poi> pois = getResult(res);
        statement.close();
        return pois;
    }

    @Override
    public Poi listOneById(int id) throws SQLException {
        String sql = "SELECT * FROM jaap WHERE PoiID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        ArrayList<Poi> poi = getResult(res);
        statement.close();
        if (poi.isEmpty()) {
            return null;
        }
        return poi.getFirst();
    }

    @Override
    public ArrayList<Poi> listByIDRange(int start, int end) throws SQLException {
        String sql = "SELECT * FROM jaap WHERE PoiID BETWEEN ? AND ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, start);
        statement.setInt(2, end);
        ResultSet res = statement.executeQuery();
        ArrayList<Poi> pois = getResult(res);
        statement.close();
        return pois;
    }

    @Override
    public ArrayList<Poi> listByMonthModification(int month) throws SQLException {
        String sql = "SELECT * FROM jaap WHERE MONTH(updateDate) = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, month);
        ResultSet res = statement.executeQuery();
        ArrayList<Poi> pois = getResult(res);
        statement.close();
        return pois;
    }

    @Override
    public ArrayList<Poi> listByCity(String city) throws SQLException {
        String sql = "SELECT * FROM jaap WHERE city LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + city + "%");
        ResultSet res = statement.executeQuery();
        ArrayList<Poi> pois = getResult(res);
        statement.close();
        return pois;
    }

    @Override
    public ArrayList<Poi> listByCountry(String country) throws SQLException {
        String sql = "SELECT * FROM jaap WHERE country LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + country + "%");
        ResultSet res = statement.executeQuery();
        ArrayList<Poi> pois = getResult(res);
        statement.close();
        return pois;
    }

    @Override
    public boolean updatePoiByID(Poi poi) throws SQLException {
        Poi existingPoi = listOneById(poi.getPoiId());
        if (existingPoi != null) {
            String sql = "UPDATE jaap SET latitude = ?, longitude = ?, city = ?, country = ?, description = ?, updateDate = ? WHERE PoiID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            if (poi.getLatitude() == null) {
                statement.setNull(1, java.sql.Types.DOUBLE);
            } else {
                statement.setDouble(1, poi.getLatitude());
            }
            if (poi.getLongitude() == null) {
                statement.setNull(2, java.sql.Types.DOUBLE);
            } else {
                statement.setDouble(2, poi.getLongitude());
            }
            if (poi.getCity() == null || poi.getCity().isEmpty()) {
                statement.setNull(3, java.sql.Types.VARCHAR);
            } else {
                statement.setString(3, poi.getCity());
            }
            if (poi.getCountry() == null || poi.getCountry().isEmpty()) {
                statement.setNull(4, java.sql.Types.VARCHAR);
            } else {
                statement.setString(4, poi.getCountry());
            }
            if (poi.getDescription() == null || poi.getDescription().isEmpty()) {
                statement.setNull(5, java.sql.Types.VARCHAR);
            } else {
                statement.setString(5, poi.getDescription());
            }
            if (poi.getUpdateDate() == null) {
                statement.setNull(6, java.sql.Types.DATE);
            } else {
                statement.setDate(6, Transformer.javaDateToSQLDate(poi.getUpdateDate()));
            }
            statement.setInt(7, poi.getPoiId());
            statement.executeUpdate();
            statement.close();
            return true;
        }
        return false;
    }

    @Override
    public int deleteAll(boolean confirm) throws SQLException {
        int rowsDeleted;
        if (confirm) {
            String sql = "DELETE FROM jaap";
            PreparedStatement statement = connection.prepareStatement(sql);
            rowsDeleted = statement.executeUpdate();
            statement.close();
        } else {
            rowsDeleted = listAll().size();
        }
        return rowsDeleted;
    }


    @Override
    public Poi deletePoiByID(int id, boolean confirm) throws SQLException {
        Poi poi = listOneById(id);
        if (poi != null && confirm) {
            String sql = "DELETE FROM jaap WHERE PoiID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        }
        return poi;
    }

    @Override
    public int deleteByIDRange(int start, int end, boolean confirm) throws SQLException {
        if (confirm) {
            String sql = "DELETE FROM jaap WHERE PoiID BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, start);
            statement.setInt(2, end);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted;
        }
        return 0;
    }

    @Override
    public int deleteByMonthModification(int month, boolean confirm) throws SQLException {
        if (confirm) {
            String sql = "DELETE FROM jaap WHERE MONTH(updateDate) = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, month);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted;
        }
        return 0;
    }

    @Override
    public int deleteByCity(String city, boolean confirm) throws SQLException {
        if (confirm) {
            String sql = "DELETE FROM jaap WHERE city LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + city + "%");
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted;
        }
        return listByCity(city).size();
    }

    @Override
    public int deleteByCountry(String country, boolean confirm) throws SQLException {
        if (confirm) {
            String sql = "DELETE FROM jaap WHERE country LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + country + "%");
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted;
        }
        return 0;
    }

    private static ArrayList<Poi> getResult(ResultSet res) throws SQLException {
        ArrayList<Poi> pois = new ArrayList<>();
        while (res.next()) {
            Poi poi = new Poi(
                    res.getInt("PoiID"),
                    res.getDouble("latitude"),
                    res.getDouble("longitude"),
                    res.getString("country"),
                    res.getString("city"),
                    res.getString("description"),
                    res.getDate("updateDate")
            );
            pois.add(poi);
        }
        res.close();
        return pois;
    }
}
