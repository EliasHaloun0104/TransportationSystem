package com.github.controller;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Delays extends RecursiveTreeObject<Delays> {
    StringProperty From;
    StringProperty To;
    StringProperty ActualTime;
    StringProperty Delay;
    StringProperty Message;

    public Delays(){
        super();
    }

    public Delays(String from, String to, String actualTime, String delay, String message) {
        this.From = new SimpleStringProperty(from);
        this.To = new SimpleStringProperty(to);
        this.ActualTime = new SimpleStringProperty(actualTime);
        this.Delay = new SimpleStringProperty(delay);
        this.Message = new SimpleStringProperty(message);
    }

}
