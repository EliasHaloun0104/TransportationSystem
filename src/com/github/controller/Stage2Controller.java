package com.github.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.github.model.Clock;
import com.github.model.Train;

public class Stage2Controller {
    @FXML
    private Canvas ctx_1;
    private GraphicsContext gc_1;
    AnimationTimer timer;
    private Image image;
    private Train train_north;
    private Train train_south;
    private Clock clock;

    public void initialize() {
        image = new Image("resources/img/city.png");
        train_north = new Train(970,0,-100, -100);
        train_south = new Train(100,465,1170, -100);
        clock = new Clock();

        gc_1 = ctx_1.getGraphicsContext2D();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc_1.clearRect(0,0,ctx_1.getWidth(),ctx_1.getHeight());

                gc_1.drawImage(image,0,0);
                //gc_1.fillText("x: " + x+ "y: "+ y,150,300);
                train_north.draw(gc_1);
                //train_south.draw(gc_1);
                train_north.update();
                //train_south.update();

                gc_1.setLineWidth(0.2);
                for (int x = 0; x < 1120 ; x+=20) {
                    gc_1.strokeLine(x , 0 ,x, 960);
                }
                for (int y = 0; y < 960 ; y+=20) {
                    gc_1.strokeLine(0 , y ,1120, y);
                }
                clock.timeSet(gc_1);

            }

        };
        timer.start();
    }
}
