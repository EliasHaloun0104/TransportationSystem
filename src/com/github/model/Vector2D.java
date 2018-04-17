package com.github.model;

public class Vector2D {
    private float x;
    private float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }




    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void addToX(float x){
        this.x += x;
    }
    public void addToY(float y){
        this.y += y;
    }

    public void add(Vector2D point){
        x += point.getX();
        y += point.getY();
    }
    public void add(float x, float y){
        this.x += x;
        this.y += y;
    }

    public boolean inRange(Vector2D point){
        return distance(point)<2;
    }

    //hypotenuse
    public float distance(Vector2D point){
        return (float) Math.pow(Math.pow(point.getY()-y,2) + Math.pow(point.getX()-x,2),0.5);
    }

    public float angle(Vector2D point){
        return (float) Math.asin((point.getX()-x)/distance(point));
    }


    @Override
    public String toString() {
        return "x:" + x +", y:" + y;
    }
}
