package com.github.model;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Calendar;

public class Vehicle {

    Enumeration.VehicleType type;
    Enumeration.VehicleSituation situation;
    int ID;
    Vector2D from;
    Vector2D to;
    Image image;
    float speed;
    Vector2D moveUpdate;
    double stopTime;


    public Vehicle(int ID, Enumeration.VehicleType type, Vector2D from,Vector2D to) {
        this.ID = ID;
        this.type = type;
        situation = Enumeration.VehicleSituation.RUN;
        this.from = from;
        this.to = to;
        switch (type){
            case TRAIN:
                image = new Image("resources/img/TrainImage.png");
                speed = 1f;
                break;
            case ISOMETRIC_TRAIN:
                break;
            case REGION_BUS:
                image = new Image("resources/img/regionBus.png");
                speed = 0.75f;
                break;
            case ISOMETRIC_REGION_BUS:
                break;
            case CITY_BUS:
                image = new Image("resources/img/cityBus.png");
                speed = 2f;
                break;
            case ISOMETRIC_CITY_BUS:
                break;
            case TAXI:
                break;
        }
        moveUpdate = calculateDirection();
        stopTime = 0;
    }

    public Enumeration.VehicleType getType() {
        return type;
    }

    public void setType(Enumeration.VehicleType type) {
        this.type = type;
    }

    public Enumeration.VehicleSituation getSituation() {
        return situation;
    }

    public void setSituation(Enumeration.VehicleSituation situation) {
        this.situation = situation;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Vector2D getFrom() {
        return from;
    }

    public void setFrom(Vector2D position) {
        this.from = position;
    }

    public Vector2D getTo() {
        return to;
    }

    public void setTo(Vector2D to) {
        this.to = to;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image, from.getX(), from.getY(),15,15);
    }
    public void update(Region fromR, Region toR) {
        if(situation== Enumeration.VehicleSituation.RUN){
            from.add(moveUpdate);
        }

        if(from.inRange(to) && situation == Enumeration.VehicleSituation.RUN){
            moveUpdate.set(0,0);
            situation = Enumeration.VehicleSituation.STOP_SWITCH_DESTINATION;
            stopTime = Calendar.getInstance().getTimeInMillis();
        }

        if(situation == Enumeration.VehicleSituation.STOP && (Calendar.getInstance().getTimeInMillis()> (stopTime + 2500))){
            from = new Vector2D(fromR.getPosition());
            to = new Vector2D(toR.getPosition());
            moveUpdate = calculateDirection();
            situation = Enumeration.VehicleSituation.RUN;
        }
    }

    public Vector2D calculateDirection(){
        float angle = from.angle(to);
        float xMove = Math.abs((float) (speed * Math.sin(angle)));
        float yMove = Math.abs((float) (speed * Math.cos(angle)));
        if(from.getX()> to.getX()){
            xMove *=-1;
        }
        if(from.getY()> to.getY()){
            yMove *=-1;
        }
        return new Vector2D(xMove,yMove);
    }
}
