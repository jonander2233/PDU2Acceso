package org.practica.DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.practica.drivers.MongoSingle;
import org.practica.intefaces.CRUDInterfacePoi;
import org.practica.models.Poi;

import java.util.ArrayList;
import java.util.Date;

public class MongoPoiDAO implements CRUDInterfacePoi {
    private MongoSingle ms;
    private MongoDatabase md;
    private static MongoPoiDAO instance;
    private MongoClient mc;
    MongoCollection<Document> col;

    private MongoPoiDAO() throws Exception {
        ms = MongoSingle.getInstance();
        md = ms.getDb();
        col = md.getCollection("jaap");
    }
    public static MongoPoiDAO getInstance() throws Exception {
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
        Poi doc = listOneById(poi.getPoiId());
        if(doc == null){
//            col.insertOne(poi.getDocument());
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Poi> listAll() {
        return null;
    }

    @Override
    public Poi listOneById(int id) {
        Document query = new Document("PoiID", id);
        Document poiDoc = col.find(query).first();
        if(poiDoc == null){
            return null;
        }
        int id1 = poiDoc.getInteger("poiDocId");
        double latitude = poiDoc.getDouble("latitude");
        double longitude = poiDoc.getDouble("longitude");
        String city = poiDoc.getString("city");
        String country = poiDoc.getString("country");
        String description = poiDoc.getString("description");
        Date updateDate = poiDoc.getDate("updateDate");
        return new Poi(id1,latitude,longitude,country,city,description,updateDate);
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
    private void poiToDocument(Poi poi){
        Document poiDoc = new Document("poiID",poi.getPoiId());
        if(poi.getCity() != "")
            poiDoc.append("latitude",poi.getLatitude());
//        if(poi.getLongitude() != "")





//        return new Document("poiID",this.poiId).append("latitude",this.latitude).append("longitude",this.longitude).append("city",this.city).append("description",this.description).append("updateDate",this.updateDate);
    }
}
