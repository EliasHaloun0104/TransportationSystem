package com.github.model;


public class Region {
    private int ID;
    private String name;
    private Vector2D position;

    public Region(int ID, String name, Vector2D position) {
        this.ID = ID;
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return name;
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

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public boolean equals(String otherRegion){
        return toString().equals(otherRegion);
    }
}
