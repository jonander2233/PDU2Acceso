package org.practica.models;

public class Tpoi {
    private int id;
    private Integer fkPoiId;
    private String name;
    private String description;

    public Tpoi(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setFkPoiId(int fkPoiId) {
        this.fkPoiId = fkPoiId;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Integer getFkPoiId() {
        return fkPoiId;
    }
}
