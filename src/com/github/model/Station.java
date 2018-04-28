package com.github.model;

import com.google.common.hash.HashCode;

import java.util.HashMap;

public class Station{
    int ID;
    String name;
    String city;
    Vector2D position;

    public Station(int ID, String name, String city, int x, int y) {
        this.ID = ID;
        this.name = name;
        this.city = city;
        position = new Vector2D(x,y);
    }

    @Override
    public boolean equals(Object obj) {
        return Integer.parseInt(obj.toString()) == ID || obj.toString().equals(name);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Station{" +
            "ID=" + ID +
            ", name='" + name + '\'' +
            ", city='" + city + '\'' +
            ", position=" + position +
            '}';
    }
}
