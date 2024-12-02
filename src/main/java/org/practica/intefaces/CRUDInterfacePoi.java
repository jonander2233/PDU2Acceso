package org.practica.intefaces;

import org.practica.models.Poi;

public interface CRUDInterfacePoi {
    boolean createPoi();
    Poi readPoi();
    boolean updatePoi();
    boolean deletePoi();
}
