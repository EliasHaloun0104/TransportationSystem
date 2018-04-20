package com.github.model;


import java.util.ArrayList;

public class City extends Region {
    Region region;

    public City(int ID, String name, Vector2D position, Region region) {
        super(ID, name, new Vector2D(position));
        this.region = region;
    }
}
