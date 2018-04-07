package com.github.controller;

import com.github.model.TrainMove;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;


public class Stage3_Controller {
    @FXML private Canvas ctx;
    private GraphicsContext gc;
    AnimationTimer timer;
    TrainMove trainMove;

    private Image image;

    public void initialize(){
        image = new Image("resources/TrainMap.png");
        gc = ctx.getGraphicsContext2D();
        trainMove = new TrainMove(640,120,795,310);
        ctx.setOnMouseMoved(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY());
            }
        });
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0,0,ctx.getWidth(),ctx.getHeight());
                gc.drawImage(image,0,0,878,751);
                gc.setFill(Color.BLACK);
                gc.fillOval(640,120,20,20);
                gc.fillOval(795,310,20,20);
                gc.fillOval(710, 450,20,20);
                trainMove.draw(gc);
                trainMove.update();
            }

        };

    }
    @FXML
    private void action(){
        timer.start();
    }


}
