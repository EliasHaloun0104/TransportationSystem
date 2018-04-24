package com.github.model;


import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;

public class TwoPointsMoving {
    ScheduledRoute t;
    boolean isRun;
    Station from_station;
    Station to_station;
    Vector2D from;
    Vector2D to;
    Vector2D position;

    public TwoPointsMoving(ScheduledRoute t){
        this.t = t;
        from_station = Destinations.getInstance().getStationByID(t.getFrom());
        to_station = Destinations.getInstance().getStationByID(t.getTo());

        this.from = new Vector2D(from_station.getPosition());
        this.to = new Vector2D(to_station.getPosition());
        position =new Vector2D(0,0);
        update();

        this.isRun = isRun;

    }

    public Station getFrom_station() {
        return from_station;
    }

    public void setFrom_station(Station from_station) {
        this.from_station = from_station;
    }

    public Station getTo_station() {
        return to_station;
    }

    public void setTo_station(Station to_station) {
        this.to_station = to_station;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getFrom() {
        return from;
    }

    public void setFrom(Vector2D from) {
        this.from = from;
    }

    public Vector2D getTo() {
        return to;
    }

    public void setTo(Vector2D to) {
        this.to = to;
    }


    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public void update() {
        double percentageOfRoute = Calendar.getInstance().get(Calendar.MINUTE)+Calendar.getInstance().get(Calendar.SECOND)/60-t.getStart().getMinute();
        if(percentageOfRoute<0){
            percentageOfRoute += 60;
        }
        double distanceFromStart = percentageOfRoute*from.distance(to)/t.getDuration().getMinute();

        double xMove = Math.abs((float) (distanceFromStart * Math.sin(from.angle(to))));
        double yMove = Math.abs((float) (distanceFromStart * Math.cos(from.angle(to))));
        if(from.getX()> to.getX()){
            xMove *=-1;
        }
        if(from.getY()> to.getY()){
            yMove *=-1;
        }
        position.set(xMove+from.getX(),yMove+from.getY());


    }

    @Override
    public String toString() {
        return "TwoPointsMoving{" +
            "isRun=" + isRun +
            ", from_station=" + from_station +
            ", to_station=" + to_station +
            ", from=" + from +
            ", to=" + to +
            ", position=" + position +
            '}';
    }
}
