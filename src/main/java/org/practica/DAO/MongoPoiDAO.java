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
        if (instance == null) {
            instance = new MongoPoiDAO();
        }
        return instance;
    }

    @Override
    public int countElements() {
        return (int) col.countDocuments();
    }

    @Override
    public boolean addPoi(Poi poi) {
        Poi doc = listOneById(poi.getPoiId());
        if (doc == null) {
            col.insertOne(poiToDocument(poi));
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Poi> listAll() {
        ArrayList<Poi> pois = new ArrayList<>();
        for (Document doc : col.find()) {
            pois.add(documentToPoi(doc));
        }
        return pois;
    }

    @Override
    public Poi listOneById(int id) {
        Document query = new Document("PoiID", id);
        Document poiDoc = col.find(query).first();
        if (poiDoc == null) {
            return null;
        }
        return documentToPoi(poiDoc);
    }

    @Override
    public ArrayList<Poi> listByIDRange(int start, int end) {
        ArrayList<Poi> pois = new ArrayList<>();
        for (Document doc : col.find(new Document("PoiID", new Document("$gte", start).append("$lte", end)))) {
            pois.add(documentToPoi(doc));
        }
        return pois;
    }

    @Override
    public ArrayList<Poi> listByMonthModification(int month) {
        ArrayList<Poi> pois = new ArrayList<>();
        for (Document doc : col.find()) {
            Date updateDate = doc.getDate("updateDate");
            if (updateDate != null && updateDate.getMonth() + 1 == month) {
                pois.add(documentToPoi(doc));
            }
        }
        return pois;
    }

    @Override
    public ArrayList<Poi> listByCity(String city) {
        ArrayList<Poi> pois = new ArrayList<>();
        for (Document doc : col.find(new Document("city", city))) {
            pois.add(documentToPoi(doc));
        }
        return pois;
    }

    @Override
    public ArrayList<Poi> listByCountry(String country) {
        ArrayList<Poi> pois = new ArrayList<>();
        for (Document doc : col.find(new Document("country", country))) {
            pois.add(documentToPoi(doc));
        }
        return pois;
    }

    @Override
    public boolean updatePoiByID(Poi poi) {
        Document query = new Document("PoiID", poi.getPoiId());
        Document updatedDoc = new Document("$set", poiToDocument(poi));
        return col.updateOne(query, updatedDoc).getMatchedCount() > 0;
    }

    @Override
    public int deleteAll(boolean confirm) {
        if (confirm) {
            long deletedCount = col.deleteMany(new Document()).getDeletedCount();
            return (int) deletedCount;
        }
        return 0;
    }

    @Override
    public Poi deletePoiByID(int id, boolean confirm) {
        if (confirm) {
            Document query = new Document("PoiID", id);
            Document deletedDoc = col.findOneAndDelete(query);
            if (deletedDoc != null) {
                return documentToPoi(deletedDoc);
            }
        }
        return null;
    }

    @Override
    public int deleteByIDRange(int start, int end, boolean confirm) {
        if (confirm) {
            long deletedCount = col.deleteMany(new Document("PoiID", new Document("$gte", start).append("$lte", end))).getDeletedCount();
            return (int) deletedCount;
        }
        return 0;
    }

    @Override
    public int deleteByMonthModification(int month, boolean confirm) {
        if (confirm) {
            ArrayList<Document> toDelete = new ArrayList<>();
            for (Document doc : col.find()) {
                Date updateDate = doc.getDate("updateDate");
                if (updateDate != null && updateDate.getMonth() + 1 == month) {
                    toDelete.add(doc);
                }
            }
            int count = 0;
            for (Document doc : toDelete) {
                col.deleteOne(new Document("PoiID", doc.getInteger("PoiID")));
                count++;
            }
            return count;
        }
        return 0;
    }

    @Override
    public int deleteByCity(String city, boolean confirm) {
        if (confirm) {
            long deletedCount = col.deleteMany(new Document("city", city)).getDeletedCount();
            return (int) deletedCount;
        }
        return 0;
    }

    @Override
    public int deleteByCountry(String country, boolean confirm) {
        if (confirm) {
            long deletedCount = col.deleteMany(new Document("country", country)).getDeletedCount();
            return (int) deletedCount;
        }
        return 0;
    }

    private Document poiToDocument(Poi poi) {
        return new Document("PoiID", poi.getPoiId()) // PoiID no puede ser nulo
                .append("latitude", poi.getLatitude() != null ? poi.getLatitude() : 0.0)
                .append("longitude", poi.getLongitude() != null ? poi.getLongitude() : 0.0)
                .append("city", poi.getCity() != null ? poi.getCity() : "")
                .append("country", poi.getCountry() != null ? poi.getCountry() : "")
                .append("description", poi.getDescription() != null ? poi.getDescription() : "")
                .append("updateDate", poi.getUpdateDate());
    }

    private Poi documentToPoi(Document doc) {
        Integer id = doc.getInteger("PoiID");
        Double latitude = doc.getDouble("latitude");
        Double longitude = doc.getDouble("longitude");
        String city = doc.getString("city") != null ? doc.getString("city") : "";
        String country = doc.getString("country") != null ? doc.getString("country") : "";
        String description = doc.getString("description") != null ? doc.getString("description") : "";
        Date updateDate = doc.getDate("updateDate");

        return new Poi(id, latitude, longitude, country, city, description, updateDate);
    }
}
