package com.github.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Calendar;

public class BreakNews {
    Text news;
    double textWidth;
    float startTime;
    Vector2D rectPosition;
    Vector2D textPosition;



    public BreakNews(String news, Canvas canvas){
        this.news = new Text(news);
        textWidth = this.news.getLayoutBounds().getWidth();
        rectPosition = new Vector2D(canvas.getWidth(),canvas.getHeight()-70);
        textPosition = new Vector2D(canvas.getWidth() +(canvas.getWidth()+textWidth)/2, 640-40);
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.web("800000ff"));
        gc.fillRect(rectPosition.getX(), rectPosition.getY(),4000,40);
        gc.setFill(Color.WHITESMOKE);
        gc.fillText(news.getText(), textPosition.getX(), textPosition.getY());

        //Update
        if(rectPosition.getX()>-1000){
            textPosition.addToX(-1f);
            rectPosition.addToX(-1f);
        }else{
            textPosition.addToY(1f);
            rectPosition.addToY(1f);
        }
    }
}
