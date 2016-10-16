package com.github.funnygopher.imhungry.model;

import io.realm.RealmObject;

public class Place extends RealmObject {

    private long id;
    private String name = "";
    private String description = "";
    private int price; // Not a monetary value. More of a scale from 1 - 5.
    private String address = "";
    private boolean thisSpecificPlace;
    private float distance;

    public Place() {
        // default constructor
    }

    public Place(String name, String description, int price, String address, boolean thisSpecificPlace) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.address = address;
        this.thisSpecificPlace = thisSpecificPlace;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public boolean isThisSpecificPlace() {
        return thisSpecificPlace;
    }

    public void setDistance(float miles) {
        distance = miles;
    }

    public float getDistance() {
        return distance;
    }

    public String getFormattedDistance() {
        return "2.0 mi";
    }
}
