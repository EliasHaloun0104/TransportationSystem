package com.github.model;

import java.awt.geom.Point2D;

public class City extends Region {
    Region region;

    public City(int ID, String name, Vector2D position, Region region) {
        super(ID, name, position);
        this.region = region;
    }
}
