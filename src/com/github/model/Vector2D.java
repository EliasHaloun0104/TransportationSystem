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

    @Override
    public String toString() {
        return "x:" + x +", y:" + y;
    }
}
