package com.github.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Train {
    Image image;
    private SuperPoint2D startPosition;
    private SuperPoint2D position;
    private SuperPoint2D exitPosition;
    private SuperPoint2D variable;

    public Train(float x, float y, float xEnd, float yEnd) {
        image = new Image("resources/wagon.png");
        startPosition = new SuperPoint2D(x,y);
        position = new SuperPoint2D(x,y);
        exitPosition = new SuperPoint2D(x,y);

        if(x > xEnd)
            variable = new SuperPoint2D(-0.6f,0.3f);
        else
            variable = new SuperPoint2D(0.6f,-0.3f);

    }

    public void update(){
        position.addToX(variable.x);
        position.addToY(variable.y);
        if(position.x < exitPosition.x ||position.y < exitPosition.y){
            position = startPosition;
        }
        if(position.getX() < 8*20 && position.getX() > 7*20 && position.getY()< 21*20 && position.getY()>20*20){
            System.out.println("1");
        }
    }
    public void draw(GraphicsContext gc){
        gc.drawImage(image,position.getX(),position.getY());
        gc.fillOval(12*20,21*20,20,20);
    }
}
