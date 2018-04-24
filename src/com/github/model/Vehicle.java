package com.github.model;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.github.model.Enumeration.*;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.List;
import java.util.Queue;

public class Vehicle {
    private VehicleType type;
    private VehicleSituation situation;
    private int scheduledRouteID;
    private TwoPointsMoving move;
    private Image image;
    private double stopTime;
    private boolean isReadyToDelete;


    public Vehicle(ScheduledRoute t) {
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
        stopTime = 0;
        isReadyToDelete = false;
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

    public boolean isReadyToDelete() {
        return isReadyToDelete;
    }

    public void setReadyToDelete(boolean readyToDelete) {
        isReadyToDelete = readyToDelete;
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

    public double getStopTime() {
        return stopTime;
    }

    public void setStopTime(double stopTime) {
        this.stopTime = stopTime;
    }

    public void vehicleUpdate(){
//        if(!move.isRun()){
//            stopTime = Calendar.getInstance().getTimeInMillis()+3000; //StopTime is 30 seconds
//            if(stations.size()>1){
//                move = new TwoPointsMoving(stations.poll(),stations.peek(),VehicleType.getVehicleSpeed(type), true);
//            }else{
//                isReadyToDelete = true;
//            }
//
//        }

    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image, move.getPosition().getX(), move.getPosition().getY(),15,15);
        if(Calendar.getInstance().getTimeInMillis() > stopTime){
            move.update();
            vehicleUpdate();
        }

    }


    /*private void updateDestination(){
        vehicle.setSituation(Enumeration.VehicleSituation.STOP);
        sta = route.getRoute().get(current_position);
        try{
            toR = route.getRoute().get(++current_position);
        }catch (IndexOutOfBoundsException e){
            current_position = 0;
            toR = route.getRoute().get(++current_position);
        }

    }*/
}
