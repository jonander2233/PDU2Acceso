package org.practica.DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.practica.drivers.MongoSingle;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

import java.util.ArrayList;

public class MongoPoiDAO implements CRUDInterfacePoi {
    private MongoSingle ms;
    private MongoDatabase md;
    private static MongoPoiDAO instance;
    private MongoClient mc;
    MongoCollection<Document> col;

    private MongoPoiDAO() {
        ms = MongoSingle.getInstance();
        md = ms.getDb();
        col = md.getCollection("jaap");
    }
    public static MongoPoiDAO getInstance(){
        if(instance == null){
            instance = new MongoPoiDAO();
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
