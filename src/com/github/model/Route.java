package com.github.model;

import java.util.ArrayList;
import java.util.List;

public class Route {
    enum Type{
        CITY_BUS, REGION_BUS, TRAIN
    }
    Type type;
    Type type2;
    List<Region> route;

    public Route(Type type, Region...regions) {
        this.type = type;
        route = new ArrayList<>();
        for (Region r: regions) {
            route.add(r);
        }
    }
    public Route (Type type, List<Region> route){
        this.type = type;
        this.route = route;
    }

    public void setType2(Type type2) {
        this.type2 = type2;
    }

    public Type getType() {
        return type;
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
