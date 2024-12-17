package org.practica.models;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Poi {
    private int poiId;
    private Double latitude;
    private Double longitude;
    private String city;
    private String country;
    private String description;
    private Date updateDate;
    private List<Tpoi> tpois;

    public Poi(int poiId, Double latitude, Double longitude,String country, String city, String description, Date updateDate) {
        this.poiId = poiId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.description = description;
        this.updateDate = updateDate;
        this.country = country;
        tpois = new ArrayList<>();
    }
    public void addTpoi(Tpoi tpoi){
        tpoi.setFkPoiId(this.poiId);
        tpois.add(tpoi);
    }
    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    public String getCountry() {
        return country;
    }

    public int getPoiId() {
        return poiId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public Date getUpdateDate() {
        return updateDate;
    }


    @Override
    public String toString() {
        return "Poi{" +
                "poiId=" + poiId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }
}
