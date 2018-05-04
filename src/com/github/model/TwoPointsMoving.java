package com.github.model;


import java.util.Calendar;

public class TwoPointsMoving {
    private ScheduledRoute t;
    private Station from_station;
    private Station to_station;
    private Vector2D from;
    private Vector2D to;
    private Vector2D position;

    public TwoPointsMoving(ScheduledRoute t){
        this.t = t;
        from_station = Destinations.getInstance().getStationByName(t.getFrom());
        to_station = Destinations.getInstance().getStationByName(t.getTo());

        this.from = new Vector2D(from_station.getPosition());
        this.to = new Vector2D(to_station.getPosition());
        calculatePosition();
    }



    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getTo() {
        return to;
    }

    public void setTo(Vector2D to) {
        this.to = to;
    }

    public void calculatePosition() {
        double percentageOfRoute = Calendar.getInstance().get(Calendar.MINUTE)+
                                    Calendar.getInstance().get(Calendar.SECOND)/60
                                    -t.getStart().getMinute();
        if(percentageOfRoute<0){
            percentageOfRoute += 60;
        }
        percentageOfRoute = percentageOfRoute/ t.getDuration().getMinute();
        double distanceFromStart = percentageOfRoute*from.distance(to);


        double xMove = Math.abs((float) (distanceFromStart * Math.sin(from.angle(to))));
        double yMove = Math.abs((float) (distanceFromStart * Math.cos(from.angle(to))));
        if(from.getX()> to.getX()){
            xMove *=-1;
        }
        if(from.getY()> to.getY()){
            yMove *=-1;
        }
        position = new Vector2D(xMove+from.getX(),yMove+from.getY());


    }

    @Override
    public String toString() {
        return t.getName() + "- From: " + from_station.getName() +
            " To: " + to_station.getName() +
            " (" + t.getEnd() + ")";
    }
}
