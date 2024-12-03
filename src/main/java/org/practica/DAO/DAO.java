package org.practica.DAO;

import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

public abstract class DAO implements CRUDInterfacePoi {
    public abstract boolean createPoi();
    public abstract Poi readPoi();
    public abstract boolean updatePoi();
    public abstract boolean deletePoi();
}
