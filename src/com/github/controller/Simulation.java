package com.github.controller;

import com.github.model.VehicleSimulation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.github.model.Clock;
import com.github.model.Train;

public class Simulation {
    @FXML private Canvas canvas_Station_0;
    @FXML private Canvas canvas_Station_1;
    @FXML private Canvas canvas_City_0;
    @FXML private Canvas canvas_City_1;
    @FXML private Canvas canvas_Region_0;
    @FXML private Canvas canvas_Region_1;

    private GraphicsContext gc_Station_0;
    private GraphicsContext gc_Station_1;
    private GraphicsContext gc_City_0;
    private GraphicsContext gc_City_1;
    private GraphicsContext gc_Region_0;
    private GraphicsContext gc_Region_1;

    AnimationTimer timer;
    private Image station;
    private Image city;
    private Image region;
    private Image train;
    private VehicleSimulation vehicleSimulation;
    private Train train_north;
    private Train train_south;
    private Clock clock;


    public void initialize() {

        gc_Station_0 = canvas_Station_0.getGraphicsContext2D();
        gc_Station_1 = canvas_Station_1.getGraphicsContext2D();
        gc_City_0 = canvas_City_0.getGraphicsContext2D();
        gc_City_1 = canvas_City_1.getGraphicsContext2D();
        gc_Region_0 = canvas_Region_0.getGraphicsContext2D();
        gc_Region_1 = canvas_Region_1.getGraphicsContext2D();

        station = new Image("resources/img/StationMap.png");
        city = new Image("resources/img/CityMap.png");
        region = new Image("resources/img/TrainMap.png");
        train = new Image("resources/img/TrainImage.png");
        vehicleSimulation = new VehicleSimulation(canvas_Region_1);


        train_north = new Train(970,0,-100, -100);
        train_south = new Train(100,465,1170, -100);
        clock = new Clock();





        gc_Station_0.drawImage(station,0,0, 960,640);
        gc_City_0.drawImage(city,0,0, 960,640);
        gc_Region_0.drawImage(region,0,0, 960,640);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc_Station_1.clearRect(0,0, canvas_Station_0.getWidth(), canvas_Station_0.getHeight());
                gc_City_1.clearRect(0,0, canvas_Station_0.getWidth(), canvas_Station_0.getHeight());
                gc_Region_1.clearRect(0,0, canvas_Station_0.getWidth(), canvas_Station_0.getHeight());

                train_north.draw(gc_Station_1);
                train_north.draw(gc_City_1);
                vehicleSimulation.draw(gc_Region_1);
                vehicleSimulation.update();
                train_north.update();

                /*gc_Station_1.setLineWidth(0.2);
                for (int x = 0; x < 1120 ; x+=20) {
                    gc_Station_1.strokeLine(x , 0 ,x, 960);
                    gc_City_1.strokeLine(x , 0 ,x, 960);
                    gc_Region_1.strokeLine(x , 0 ,x, 960);
                }
                for (int y = 0; y < 960 ; y+=20) {
                    gc_Station_1.strokeLine(0 , y ,1120, y);
                    gc_City_1.strokeLine(0 , y ,1120, y);
                    gc_Region_1.strokeLine(0 , y ,1120, y);
                }*/
                clock.timeSet(gc_Station_0);

            }

        };
        timer.start();
    }
}
