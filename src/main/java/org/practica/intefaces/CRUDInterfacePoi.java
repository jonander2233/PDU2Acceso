package org.practica.intefaces;

import org.practica.models.Poi;

import java.util.ArrayList;

public interface CRUDInterfacePoi {
    int countElements();
    boolean addPoi(Poi poi);

    ArrayList<Poi> listAll();
    Poi listOneById(int id);
    ArrayList<Poi> listByIDRange(int start, int end);
    ArrayList<Poi> listByMonthModification(int month);
    ArrayList<Poi> listByCity(String city);

    boolean updatePoiByID(Poi poi);

    int deleteAll(boolean confirm);
    int deletePoiByID(int id,boolean confirm);
    int deleteByIDRange(int start, int end,boolean confirm);
    int deleteByMonthModification(int month,boolean confirm);
    int deleteByCity(String city,boolean confirm);



}
