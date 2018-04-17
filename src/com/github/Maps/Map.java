package com.github.Maps;

import com.github.model.Vector2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Calendar;

public class Map {
    public enum CurrentMap{
        REGION, CITY,STATION
    }
    CurrentMap currentMap;
    Image cityMap;
    Image regionMap;
    Image stationMap;


    final Vector2D position;
    final Vector2D dimension;
    Vector2D currentPosition;
    Vector2D currentDimension;
    final Vector2D dimensionVariable;
    final Vector2D positionVariable;


    final Vector2D middlePosition;
    final Vector2D middleDimension;
    Vector2D currentMiddlePosition;
    Vector2D currentMiddleDimension;


    boolean fadeOutIsActive;
    boolean fadeInIsActive;
    double fadeStart;

    public Map(Canvas canvas) {
        currentMap = CurrentMap.REGION;
        cityMap = new Image("resources/CityMap.png");
        regionMap = new Image("resources/TrainMap.png");
        stationMap = new Image("resources/StationMap.png");
        float canvasWidth = (float) canvas.getWidth();
        float canvasHeight = (float) canvas.getHeight();
        float fadeSpeed = 0.04f;

        position = new Vector2D(0,0);
        dimension = new Vector2D(canvasWidth,canvasHeight);

        currentPosition = new Vector2D(0,0);
        currentDimension = new Vector2D(canvasWidth,canvasHeight);

        dimensionVariable = new Vector2D(canvasWidth*fadeSpeed, canvasHeight*fadeSpeed );
        positionVariable = new Vector2D(-canvasWidth*fadeSpeed/2, -canvasHeight*fadeSpeed/2 );

        middleDimension = new Vector2D(canvasWidth*fadeSpeed/2, canvasHeight*fadeSpeed/2 );
        middlePosition = new Vector2D(((canvasWidth-middleDimension.getX())/2),((canvasHeight-middleDimension.getY())/2));
        currentMiddleDimension = new Vector2D( canvasWidth*fadeSpeed/2,  canvasHeight*fadeSpeed/2);
        currentMiddlePosition = new Vector2D(((canvasWidth-middleDimension.getX())/2),((canvasHeight-middleDimension.getY())/2));


        fadeInIsActive = false;
        fadeOutIsActive = false;
    }

    public void draw(GraphicsContext gc){
        double now = Calendar.getInstance().getTimeInMillis();
        switch (currentMap){
            case REGION:
                gc.drawImage(regionMap,currentPosition.getX(),currentPosition.getY(),currentDimension.getX(),currentDimension.getY());
                switchView(now,gc,cityMap,CurrentMap.CITY);
                break;
            case CITY:
                gc.drawImage(cityMap,currentPosition.getX(),currentPosition.getY(),currentDimension.getX(),currentDimension.getY());
                switchView(now,gc,stationMap,CurrentMap.STATION);
                break;
            case STATION:
                gc.drawImage(stationMap,currentPosition.getX(),currentPosition.getY(),currentDimension.getX(),currentDimension.getY());
                break;
        }


    }

    public void activeFadeOut(){
        fadeOutIsActive = true;
        fadeStart = Calendar.getInstance().getTimeInMillis();
    }

    public void switchView(double now, GraphicsContext gc, Image nextImage, CurrentMap currentMap){
        if(fadeOutIsActive && now<fadeStart+6000){
            currentDimension.add(dimensionVariable);
            currentPosition.add(positionVariable);
            gc.drawImage(nextImage,currentMiddlePosition.getX(),currentMiddlePosition.getY(),currentDimension.getX()*1/100,currentDimension.getY()*1/100);
        }else if (fadeOutIsActive & now <fadeStart+12000){
            gc.drawImage(nextImage,currentMiddlePosition.getX(),currentMiddlePosition.getY(),currentMiddleDimension.getX(),currentMiddleDimension.getY());
            currentMiddleDimension.add(dimensionVariable);
            currentMiddlePosition.add(positionVariable);
            currentDimension.add(dimensionVariable);
            currentPosition.add(positionVariable);

            if(currentMiddlePosition.getX()<0){
                currentPosition = position;
                currentDimension = dimension;
                currentMiddlePosition = middlePosition;
                currentMiddleDimension = middleDimension;
                this.currentMap = currentMap;
                fadeOutIsActive = false;
            }
        }
    }
}


