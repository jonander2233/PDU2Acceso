package org.practica.intefaces;

import org.practica.models.Poi;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUDInterfacePoi {
    int countElements() throws SQLException;
    boolean addPoi(Poi poi) throws SQLException;

    ArrayList<Poi> listAll() throws SQLException;
    Poi listOneById(int id) throws SQLException;
    ArrayList<Poi> listByIDRange(int start, int end) throws SQLException;
    ArrayList<Poi> listByMonthModification(int month) throws SQLException;
    ArrayList<Poi> listByCity(String city) throws SQLException;
    ArrayList<Poi> listByCountry(String country) throws SQLException;


    boolean updatePoiByID(Poi poi) throws SQLException;

    int deleteAll(boolean confirm) throws SQLException;
    Poi deletePoiByID(int id,boolean confirm) throws SQLException;
    int deleteByIDRange(int start, int end,boolean confirm) throws SQLException;
    int deleteByMonthModification(int month,boolean confirm) throws SQLException;
    int deleteByCity(String city,boolean confirm) throws SQLException;
    int deleteByCountry(String country,boolean confirm) throws SQLException;



}
