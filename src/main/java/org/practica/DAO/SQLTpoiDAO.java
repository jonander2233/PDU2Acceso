package org.practica.DAO;

import org.practica.drivers.JDBCSingle;
import org.practica.intefaces.CRUDInterfaceTpoi;
import org.practica.intefaces.CRUDInterfaceTpoi;
import org.practica.models.Poi;
import org.practica.models.Tpoi;
import org.practica.models.Tpoi;
import org.practica.utils.Transformer;

import java.sql.*;
import java.util.ArrayList;

public class SQLTpoiDAO implements CRUDInterfaceTpoi {
    private static Connection connection;
    private static SQLTpoiDAO instance;

    private SQLTpoiDAO() throws Exception {
        connection = JDBCSingle.getConnection();
    }
    public static SQLTpoiDAO getInstance() throws Exception {
        if (instance == null) {
            instance = new SQLTpoiDAO();
        }
        return instance;
    }


    @Override
    public int countElements() throws Exception {
        return listAll().size();
    }

    @Override
    public boolean addTPoi(Tpoi tpoi) throws Exception {
        Tpoi tpoi1 = listOneById(tpoi.getId());
        if (tpoi1 == null) {
            String sql = "INSERT INTO pois (id,fk_poi_id, descript, name) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tpoi.getId());
            if (tpoi.getFkPoiId() == null) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, tpoi.getFkPoiId());
            }
            if (tpoi.getName() == null || tpoi.getName().isEmpty()) {
                statement.setNull(4, java.sql.Types.VARCHAR);
            } else {
                statement.setString(4, tpoi.getName());
            }
            if (tpoi.getDescription() == null || tpoi.getDescription().isEmpty()) {
                statement.setNull(5, java.sql.Types.VARCHAR);
            } else {
                statement.setString(5, tpoi.getDescription());
            }
            statement.execute();
            statement.close();
            return true;
        }
        return false;
    }


    @Override
    public ArrayList<Tpoi> listAll() throws Exception {
        String sql = "SELECT * FROM tpoi ORDER BY id";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        ArrayList<Tpoi> tpois = getResult(res);
        statement.close();
        return tpois;
    }

    @Override
    public Tpoi listOneById(int id) throws Exception {
        String sql = "SELECT * FROM tpoi WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        ArrayList<Tpoi> tpoi = getResult(res);
        statement.close();
        if (tpoi.isEmpty()) {
            return null;
        }
        return tpoi.getFirst();
    }

    @Override
    public ArrayList<Tpoi> listByIDRange(int start, int end) throws Exception {
        String sql = "SELECT * FROM tpoi WHERE id BETWEEN ? AND ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, start);
        statement.setInt(2, end);
        ResultSet res = statement.executeQuery();
        ArrayList<Tpoi> tpois = getResult(res);
        statement.close();
        return tpois;
    }

    @Override
    public boolean updateTPoiByID(Tpoi tpoi) throws Exception {
        Tpoi existingPoi = listOneById(tpoi.getId());
        if (existingPoi != null) {
            String sql = "UPDATE tpoi SET name = ?, descript = ?, fk_poi_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            if (tpoi.getName() == null || tpoi.getName().isEmpty()) {
                statement.setNull(1, java.sql.Types.VARCHAR);
            } else {
                statement.setString(1, tpoi.getName());
            }

            if (tpoi.getDescription() == null || tpoi.getDescription().isEmpty()) {
                statement.setNull(2, java.sql.Types.VARCHAR);
            } else {
                statement.setString(2, tpoi.getDescription());
            }
            if (tpoi.getFkPoiId() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, tpoi.getFkPoiId());
            }

            statement.setInt(4, tpoi.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        }
        return false;
    }


    @Override
    public int deleteAll(boolean confirm) throws Exception {
        return 0;
    }

    @Override
    public Tpoi deleteTpoiByID(int id, boolean confirm) throws Exception {
        return null;
    }

    @Override
    public int deleteByIDRange(int start, int end, boolean confirm) throws Exception {
        if (confirm) {
            String sql = "DELETE FROM tpoi WHERE id BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, start);
            statement.setInt(2, end);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            return rowsDeleted;
        }
        return 0;
    }

    private static ArrayList<Tpoi> getResult(ResultSet res) throws SQLException {
        ArrayList<Tpoi> tpois = new ArrayList<>();
        while (res.next()) {
            Tpoi tpoi = new Tpoi(
                res.getInt("id"),
                res.getString("name"),
                res.getString("descript")
            );
            if(res.getObject("fk_poi_id") != null){
                tpoi.setFkPoiId(res.getInt("fk_poi_id"));
            }
            tpois.add(tpoi);
        }
        res.close();
        return tpois;
    }
}

