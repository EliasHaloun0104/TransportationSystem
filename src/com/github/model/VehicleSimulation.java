package com.github.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Calendar;


public class VehicleSimulation {
    Vehicle vehicle;
    Route route;

    Region fromR;
    Region toR;

    int current_position;



    public VehicleSimulation(Route route) {
        this.route = route;
        current_position = 0;
        fromR = route.getRoute().get(current_position);
        toR = route.getRoute().get(++current_position);

        vehicle = new Vehicle(12, route.getType(), new Vector2D(fromR.getPosition()),new Vector2D(toR.getPosition()));

    }


    public void draw(GraphicsContext gc){
        vehicle.draw(gc);

        if(vehicle.getSituation()== Enumeration.VehicleSituation.STOP_SWITCH_DESTINATION){
            updateDestination();

        }
        vehicle.update(fromR,toR);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Region getFromR() {
        return fromR;
    }

    public void setFromR(Region fromR) {
        this.fromR = fromR;
    }

    public Region getToR() {
        return toR;
    }

    public void setToR(Region toR) {
        this.toR = toR;
    }

    public int getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(int current_position) {
        this.current_position = current_position;
    }

    private void updateDestination(){
        vehicle.setSituation(Enumeration.VehicleSituation.STOP);
        fromR = route.getRoute().get(current_position);
        try{
            toR = route.getRoute().get(++current_position);
        }catch (IndexOutOfBoundsException e){
            current_position = 0;
            toR = route.getRoute().get(++current_position);
        }

    }


}
