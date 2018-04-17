package com.github.Maps;

import com.github.model.Vector2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MapManager {
    public enum CurrentMap{
        CITY,REGION,STATION
    }
    CurrentMap currentMap;


    boolean switchMap;
    double switchStartTime;
    float opacity;
    final Vector2D dimensionVariable;


    public MapManager(Canvas canvas) {

        currentMap = CurrentMap.REGION;

        switchMap = false;
        switchStartTime = 0;
        opacity = 1;
        dimensionVariable = new Vector2D((float) canvas.getWidth()/10, (float) canvas.getHeight()/10 );


    }


}
