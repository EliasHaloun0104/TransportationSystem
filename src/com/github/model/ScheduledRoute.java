package com.github.model;

import java.sql.Time;

public class ScheduledRoute {
    private int ID;
    private int scheduledID;
    private int ID_special;
    private String name;
    private Enumeration.VehicleType type;
    private int from;
    private int to;
    private TimeProcess duration;
    private TimeProcess start;
    private TimeProcess end;
    private float distance;
    private String driver;
    private String vehicle;


    public ScheduledRoute(int ID, int scheduledID, int ID_special, String name, String type, int from, int to, Time duration, Time start, Time end, float distance, String driver, String vehicle) {
        this.ID = ID;
        this.scheduledID = scheduledID;
        this.ID_special = ID_special;
        this.name = name;
        this.type = Enumeration.VehicleType.valueOf(type);
        this.from = from;
        this.to = to;
        this.duration = new TimeProcess(duration);
        this.start = new TimeProcess(start);
        this.end = new TimeProcess(end);
        this.distance = distance;
        this.driver = driver;
        this.vehicle = vehicle;

        //System.out.println(toString());
        /*try {
            TimeProcess t = new TimeProcess(TimeProcess.generateTime(Calendar.getInstance().get(Calendar.MINUTE)));
            if(isTimeBetween(t)){
                System.out.println(toString());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public String toString() {
        return "Route " + "ID:" + ID +
            ", scheduledID:" + scheduledID +
            ", ID_special=" + ID_special +
            ", name='" + name + '\'' +
            ", type=" + type +
            ", from: '" + from + '\'' +
            ", to: '" + to + '\'' +
            ", duration: " + duration +
            ", start: " + start +
            ", end: " + end +
            ", distance: " + distance +
            ", driver: '" + driver + '\'' +
            ", vehicle:'" + vehicle + '\'' +
            '}';
    }

    public boolean isTimeBetween(Time t){
        return t.before(end) && t.after(start);
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getScheduledID() {
        return scheduledID;
    }

    public void setScheduledID(int scheduledID) {
        this.scheduledID = scheduledID;
    }

    public int getID_special() {
        return ID_special;
    }

    public void setID_special(int ID_special) {
        this.ID_special = ID_special;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enumeration.VehicleType getType() {
        return type;
    }

    public void setType(Enumeration.VehicleType type) {
        this.type = type;
    }


    public TimeProcess getDuration() {
        return duration;
    }

    public void setDuration(TimeProcess duration) {
        this.duration = duration;
    }

    public TimeProcess getStart() {
        return start;
    }

    public void setStart(TimeProcess start) {
        this.start = start;
    }

    public TimeProcess getEnd() {
        return end;
    }

    public void setEnd(TimeProcess end) {
        this.end = end;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
