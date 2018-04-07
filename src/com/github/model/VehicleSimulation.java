package com.github.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.math.MathContext;
import java.util.*;

public class VehicleSimulation {
    Vector2D position;
    Vector2D destination;
    float speed;
    float angle;
    double stopTime;
    boolean isUpdate;

    public VehicleSimulation(float x1, float y1, float x2, float y2) {
        position = new Vector2D(x1,y1);
        destination = new Vector2D(x2,y2);
        speed = 1f;
        angle = position.angle(destination);
        isUpdate = true;
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillOval(position.getX(),position.getY(),20,20);
    }

    public void update(){
        position.addToX((float) (speed * Math.sin(angle)));
        position.addToY((float) (speed * Math.cos(angle)));

        if(position.inRange(destination) && isUpdate){
            speed = 0;
            stopTime = Calendar.getInstance().getTimeInMillis();
            isUpdate = false;
        }
        if(!isUpdate){
            if(Calendar.getInstance().getTimeInMillis()> (stopTime + 2000)){
                isUpdate = true;
                speed = 1;
                destination.set(710,450);
                angle = position.angle(destination);
            }
        }
    }
}
