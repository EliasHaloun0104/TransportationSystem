package com.github.controller;

import com.github.Maps.Map;
import com.github.Maps.MapManager;
import com.github.model.VehicleSimulation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Stage3_Controller {
    @FXML private Canvas ctx;
    private GraphicsContext gc;
    AnimationTimer timer;
    VehicleSimulation vehicleSimulation;
    Map map;
    private Image train;

    public void initialize(){
        map = new Map(ctx);
        train = new Image("resources/img/TrainImage.png");
        gc = ctx.getGraphicsContext2D();
        vehicleSimulation = new VehicleSimulation(ctx);
        ctx.setOnMouseMoved(mouseEvent -> System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY()));
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0,0,ctx.getWidth(),ctx.getHeight());
                map.draw(gc);
                vehicleSimulation.draw(gc);
                vehicleSimulation.update();
                gc.setLineWidth(0.4f);
                for(int x=0; x<ctx.getWidth(); x+=10 ){
                    gc.strokeLine(x,0,x,ctx.getHeight());
                }
                for(int y=0; y<ctx.getHeight(); y+=10 ){
                    gc.strokeLine(0,y,ctx.getWidth(),y);
                }


                }



        };

    }
    @FXML
    private void action(){
        timer.start();
    }

    @FXML
    private void replaceBackground(){
        map.activeFadeOut();
    }


}
