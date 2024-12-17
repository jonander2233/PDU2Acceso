package org.practica.intefaces;

import org.practica.models.Poi;
import org.practica.models.Tpoi;

import java.util.ArrayList;

public interface CRUDInterfaceTpoi {
    int countElements() throws Exception;
    boolean addTPoi(Tpoi tpoi) throws Exception;

    ArrayList<Tpoi> listAll() throws Exception;
    Tpoi listOneById(int id) throws Exception;
    ArrayList<Tpoi> listByIDRange(int start, int end) throws Exception;

    boolean updateTPoiByID(Tpoi tpoi) throws Exception;

    int deleteAll(boolean confirm) throws Exception;
    Tpoi deleteTpoiByID(int id,boolean confirm) throws Exception;
    int deleteByIDRange(int start, int end,boolean confirm) throws Exception;
}

