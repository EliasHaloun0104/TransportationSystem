package com.github.model;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Queue;


public class VehicleSimulation {
    Queue<Vector2D> destinations;
    Vector2D position;
    Vector2D destination;
    Vector2D moveUpdate;
    float speed;
    double stopTime;
    boolean isUpdate;
    Image image;
    Path path;
    PathTransition pathTransition;
    Animation animation;
    Circle pen;

    public VehicleSimulation(Canvas ctx) {
        image = new Image("resources/TrainImage.png");
        destinations = new ArrayDeque<>();
        //This data should fetch from server
        destinations.add(new Vector2D((float) (ctx.getWidth()*640/878),(float) (ctx.getHeight()*120/751)));
        destinations.add(new Vector2D((float) (ctx.getWidth()*795/878),(float) (ctx.getHeight()*310/751)));
        destinations.add(new Vector2D((float) (ctx.getWidth()*710/878),(float) (ctx.getHeight()*450/751)));
        destinations.add(new Vector2D((float) (ctx.getWidth()*460/878),(float) (ctx.getHeight()*390/751)));
        destinations.add(new Vector2D((float) (ctx.getWidth()*370/878),(float) (ctx.getHeight()*530/751)));
        destinations.add(new Vector2D((float) (ctx.getWidth()*185/878),(float) (ctx.getHeight()*585/751)));
        destinations.add(new Vector2D((float) (ctx.getWidth()*95/878),(float) (ctx.getHeight()*310/751)));

        Path path = new Path();
        path.setStroke(Color.RED);
        path.setStrokeWidth(10);
        path.getElements().addAll(new MoveTo(20, 20), new CubicCurveTo(380, 0, 380, 120, 200, 120), new CubicCurveTo(0, 120, 0, 240, 380, 240), new LineTo(20,20));

        pen = new Circle(0, 0, 4);
        pathTransition = new PathTransition(Duration.seconds(10),path,pen);
        pathTransition.playFromStart();

        position = destinations.poll();
        destination = destinations.poll();
        speed = 1f;
        moveUpdate = calculateDirection();
        isUpdate = true;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image,position.getX(),position.getY(),12,12);
        gc.fillOval(pen.getCenterX(), pen.getCenterY(),20,20);
    }

    public void update(){
        position.add(moveUpdate);

        if(position.inRange(destination) && isUpdate){
            moveUpdate = new Vector2D(0,0);
            stopTime = Calendar.getInstance().getTimeInMillis();
            isUpdate = false;
        }
        if(!isUpdate){
            if(Calendar.getInstance().getTimeInMillis()> (stopTime + 500)){
                if(destinations.size()>0) {
                    isUpdate = true;
                    destination = destinations.poll();
                    moveUpdate = calculateDirection();
                }
            }
        }
    }

    private Vector2D calculateDirection(){
        float angle = position.angle(destination);
        float xMove = Math.abs((float) (speed * Math.sin(angle)));
        float yMove = Math.abs((float) (speed * Math.cos(angle)));
        if(position.getX()> destination.getX()){
            xMove *=-1;
        }
        if(position.getY()> destination.getY()){
            yMove *=-1;
        }
        return new Vector2D(xMove,yMove);
    }
}
