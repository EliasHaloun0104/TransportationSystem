package com.github.controller;

import com.github.model.VehicleSimulation;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Stage3_Controller {
    @FXML private Canvas ctx;
    private GraphicsContext gc;
    AnimationTimer timer;
    VehicleSimulation vehicleSimulation;
    Font gameOfThrownFont = Font.loadFont(getClass().getResourceAsStream("/resources/GameOfThrones.ttf"), 14);

    private Image backGround;
    private Image train;

    public void initialize(){
        backGround = new Image("resources/OldMap.jpg");
        train = new Image("resources/TrainImage.png");
        gc = ctx.getGraphicsContext2D();
        vehicleSimulation = new VehicleSimulation(ctx);
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
                gc.drawImage(backGround,0,0,878,751);
                gc.setFill(Color.BLACK);
                gc.setFont(gameOfThrownFont);
                gc.fillText("North", 100,100);
                vehicleSimulation.draw(gc);
                vehicleSimulation.update();
            }

        };

    }
    @FXML
    private void action(){
        timer.start();
    }


}
