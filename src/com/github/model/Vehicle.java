package com.github.model;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.github.model.Enumeration.*;

import java.util.Calendar;

public class Vehicle {
    private VehicleType type;
    private VehicleSituation situation;
    private int scheduledRouteID;
    private TwoPointsMoving move;
    private Image image;
    private String routeName;


    public Vehicle(ScheduledRoute t) {
        routeName = t.getName();
        this.scheduledRouteID = t.getID();
        this.type = t.getType();
        situation = Enumeration.VehicleSituation.RUN;
        move = new TwoPointsMoving(t);


        switch (type){
            case TRAIN:
                image = new Image("resources/img/TrainImage.png");
                break;
            case ISOMETRIC_TRAIN:
                break;
            case REGION_BUS:
                image = new Image("resources/img/regionBus.png");
                break;
            case ISOMETRIC_REGION_BUS:
                break;
            case CITY_BUS:
                image = new Image("resources/img/cityBus.png");
                break;
            case ISOMETRIC_CITY_BUS:
                break;
            case TAXI:
                break;
        }
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
        return scheduledRouteID;
    }

    public void setID(int ID) {
        this.scheduledRouteID = ID;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getScheduledRouteID() {
        return scheduledRouteID;
    }

    public void setScheduledRouteID(int scheduledRouteID) {
        this.scheduledRouteID = scheduledRouteID;
    }

    public TwoPointsMoving getMove() {
        return move;
    }

    public void setMove(TwoPointsMoving move) {
        this.move = move;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image, move.getPosition().getX(), move.getPosition().getY(),15,15);
        gc.fillText(routeName, move.getPosition().getX()+18,move.getPosition().getY()+10);
    }

}
