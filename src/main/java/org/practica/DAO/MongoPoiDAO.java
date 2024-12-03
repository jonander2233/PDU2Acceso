package org.practica.DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.practica.drivers.MongoSingle;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

public class MongoPoiDAO extends DAO {
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
    public boolean createPoi() {
        return false;
    }

    @Override
    public Poi readPoi() {
        return null;
    }

    @Override
    public boolean updatePoi() {
        return false;
    }

    @Override
    public boolean deletePoi() {
        return false;
    }
}
