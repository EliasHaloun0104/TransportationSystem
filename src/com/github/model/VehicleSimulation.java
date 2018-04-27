package com.github.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;


public class VehicleSimulation {
    private ArrayList<Vehicle> vehicles;
    private BreakNews breakNews;

    int timeToUpdate;

    public VehicleSimulation() {
        callScheduledRoute();
    }

    public void callScheduledRoute(){
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

        timeToUpdate =Calendar.getInstance().get(Calendar.MINUTE);
        breakNews = new BreakNews(getNews());


    }

    public BreakNews getBreakNews() {
        return breakNews;
    }

    public void setBreakNews(BreakNews breakNews) {
        this.breakNews = breakNews;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }


    public int getTimeToUpdate() {
        return timeToUpdate;
    }

    public void setTimeToUpdate(int timeToUpdate) {
        this.timeToUpdate = timeToUpdate;
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

        if(Calendar.getInstance().get(Calendar.MINUTE)!=timeToUpdate){//Update every one minute
            callScheduledRoute();
        }
    }
    public ArrayList<Text> getNews(){
        ArrayList<Text> news = new ArrayList<>();
        for (Vehicle v: vehicles) {
            news.add(new Text(v.getMove().toString()));
        }
        return news;
    }

}
