package com.github.model;

import java.util.ArrayList;

public class ScheduledRoute extends Route {
    ArrayList<Integer> schedule;

    public ScheduledRoute(Route route, int startMinute) {
        super(route);
        schedule = new ArrayList<>();
        for (Integer i: route.getFixedTime()) {
            int in = i+startMinute;
            if(in<60){
                schedule.add(in);

            }else{
                schedule.add(in-60);
            }
        }
        System.out.println(route.getType() + ":" + schedule);
    }

    public ArrayList<Integer> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Integer> schedule) {
        this.schedule = schedule;
    }
    }
