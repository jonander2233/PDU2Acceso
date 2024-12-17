package org.practica.intefaces;

import org.practica.models.Poi;

import java.lang.Exception;
import java.util.ArrayList;

public interface CRUDInterfacePoi {
    int countElements() throws Exception;
    boolean addPoi(Poi poi) throws Exception;

    ArrayList<Poi> listAll() throws Exception;
    Poi listOneById(int id) throws Exception;
    ArrayList<Poi> listByIDRange(int start, int end) throws Exception;
    ArrayList<Poi> listByMonthModification(int month) throws Exception;
    ArrayList<Poi> listByCity(String city) throws Exception;
    ArrayList<Poi> listByCountry(String country) throws Exception;


    boolean updatePoiByID(Poi poi) throws Exception;

    int deleteAll(boolean confirm) throws Exception;
    Poi deletePoiByID(int id,boolean confirm) throws Exception;
    int deleteByIDRange(int start, int end,boolean confirm) throws Exception;
    int deleteByMonthModification(int month,boolean confirm) throws Exception;
    int deleteByCity(String city,boolean confirm) throws Exception;
    int deleteByCountry(String country,boolean confirm) throws Exception;



}
