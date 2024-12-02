package org.practica.models;

import java.util.Date;

public class Poi {
    private int poiId;
    private Double latitude;
    private Double longitude;
    private String city;
    private String description;
    private Date updateDate;

    public Poi(int poiId, Double latitude, Double longitude, String city, String description, Date updateDate) {
        this.poiId = poiId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.description = description;
        this.updateDate = updateDate;
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
}
