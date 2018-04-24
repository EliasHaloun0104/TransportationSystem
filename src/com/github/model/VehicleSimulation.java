package com.github.model;

import javafx.scene.canvas.GraphicsContext;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;


public class VehicleSimulation {
    ArrayList<Vehicle> vehicles;

    public VehicleSimulation() {
        vehicles = new ArrayList<>();

        for (ScheduledRoute t: Destinations.getInstance().scheduledRoutes) {
            try {
                TimeProcess tp_0 = TimeProcess.now(0);
                TimeProcess tp_1 = TimeProcess.now(1);
                if(t.isTimeBetween(tp_0) || t.isTimeBetween(tp_1)){
                    vehicles.add(new Vehicle(t));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public void draw(GraphicsContext gc_Region, GraphicsContext gc_City){

        for (Vehicle v: vehicles){
            switch (v.getType()){
                case TRAIN:
                case REGION_BUS:
                    v.draw(gc_Region);
                    break;
                case CITY_BUS:
                    v.draw(gc_City);
                    break;
            }
        }
    }
}
