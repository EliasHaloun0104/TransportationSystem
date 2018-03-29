package com.github.model;

import javafx.geometry.Point2D;

public class SuperPoint2D {
    float x;
    float y;

    public SuperPoint2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point2D getPosition() {
        return new Point2D(x,y);
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void addToX(float x){
        this.x += x;
    }
    public void addToY(float y){
        this.y += y;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public Point2D getRight(){
        return new Point2D(x+1,y);
    }
    public Point2D getDown(){
        return new Point2D(x,y+1);
    }
    public Point2D getUp(){
        return new Point2D(x,y-1);
    }
    public Point2D getLeft(){
        return new Point2D(x-1,y);
    }

    public void moveLeft(){
        x--;
    }
    public void moveRight(){
        x++;
    }
    public void moveUp(){
        y--;
    }
    public void moveDown(){
        y++;
    }
}
