package com.github.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Train {
    private Image image;
    private Vector2D startPosition;
    private Vector2D position;
    private Vector2D exitPosition;
    private Vector2D variable;
    private int stopTimer;
    private boolean isStop;



    public Train(float x, float y, float xEnd, float yEnd) {
        image = new Image("resources/img/wagon.png");
        startPosition = new Vector2D(x,y);
        position = new Vector2D(x,y);
        exitPosition = new Vector2D(x,y);

        if(x > xEnd)
            variable = new Vector2D(-1f,0.5f);
        else
            variable = new Vector2D(2f,-1f);
        stopTimer = 0;
        isStop = false;



    }

    private void update(){
        if(!isStop){
            position.add(variable);
            //Stop for new Passengers
            if(position.getX() <17.5*20 && position.getX()> 17.44*20){
                isStop = true;
                stopTimer = 300;
            }
            //Change path
            if(position.getX() < 10.5*20 && position.getX() > 9.5*20){
                variable.set(-0.5f,1);
            }
            //Continue to path
            if(position.getX() < 9.5*20 && position.getX() > 8.5*20){
                variable.set(-1,0.5f);
            }

            //Back to beginning
            if(position.getX() < -200){
                position.set(startPosition.getX(),startPosition.getY());
            }


        }else{
            stopTimer--;
            if(stopTimer == 0){
                isStop = false;
            }
        }


    }
    public void draw(GraphicsContext gc){
        gc.drawImage(image,position.getX(),position.getY(),80,64);
        update();
    }
}
