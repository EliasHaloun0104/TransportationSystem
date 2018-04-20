package com.github.model;

import java.util.ArrayList;
import java.util.List;

public class Route {

    int ID;



    Enumeration.VehicleType type;
    Enumeration.VehicleType type2;
    List<Region> route;

    public Route(Route route) {
        this.ID = route.getID();
        this.type = route.getType();
        this.type2 = route.getType2();
        this.route = route.getRoute();
        this.fixedTime = route.getFixedTime();
    }

    public List<Integer> getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(List<Integer> fixedTime) {
        this.fixedTime = fixedTime;
    }

    List<Integer> fixedTime;


    public Route(int ID, Enumeration.VehicleType type, Region...regions) {
        this.ID = ID;
        this.type = type;
        route = new ArrayList<>();
        fixedTime = new ArrayList<>();
        fixedTime.add(0); //startPoint Always
        float scale;
        if(this.type== Enumeration.VehicleType.TRAIN || this.type == Enumeration.VehicleType.REGION_BUS) {
            scale = 0.1f;
        }else if(type == Enumeration.VehicleType.CITY_BUS){
            scale = 0.05f;
        }else{
            scale = 1;
        }
            for (int i = 0; i < regions.length - 1; i++) {
                //Calculate Distance - multiply to minute *60 -  multiply Map scale 1/10  - divide by vehicle speed
                //get time in minute
                int distanceTime = (int) (fixedTime.get(i) + regions[i].getPosition().distance(regions[i + 1].getPosition()) * 60 *scale/ Enumeration.VehicleType.getVehicleSpeed(type));

                if (distanceTime < 60) {
                    fixedTime.add(distanceTime);
                } else {
                    fixedTime.add(distanceTime - 60);
                }
            }

        //System.out.println(fixedTime.toString());
        for (Region r: regions) {
            route.add(r);
        }
    }
    public Route (Enumeration.VehicleType type, List<Region> route){
        this.type = type;
        this.route = route;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Enumeration.VehicleType getType() {
        return type;
    }

    public void setType(Enumeration.VehicleType type) {
        this.type = type;
    }

    public Enumeration.VehicleType getType2() {
        return type2;
    }

    public void setType2(Enumeration.VehicleType type2) {
        this.type2 = type2;
    }

    public void setRoute(List<Region> route) {
        this.route = route;
    }

    public List<Region> getRoute() {
        return route;
    }

    public boolean contains(String name){
        for (Region r: route) {
            if(r.equals(name)){
                return true;
            }
        }
        return false;
    }


    public Route subList(String from, String to){
        int index_1 = -1;
        int index_2 = -1;
        for (int i=0;i<route.size();i++) {
            if (route.get(i).toString().equals(from)) {
                for (int j = i+1; j < route.size(); j++) {
                    if(route.get(j).toString().equals(to)){
                        index_1 = i;
                        index_2 = j+1;
                        break;
                    }
                }
            }
        }

        Route route = new Route(type, this.route.subList(index_1, index_2));
        return route;

    }



    @Override
    public String toString() {
        String a;
        if(type2!=null){
            a = type + "\\" + type2 + ":\n";
        }else{
            a = type + ":\n";
        }

        for (Region r: route) {
            a += r.getName() + ", ";
        }
        a +="\n";
        return a;
    }
}
