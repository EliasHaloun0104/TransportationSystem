package com.github.controller;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Complaint extends RecursiveTreeObject<Complaint> {

    StringProperty id;
    StringProperty username;
    StringProperty date;
    StringProperty isHandled;
    StringProperty message;

    public Complaint(){
        super();
    }

    public Complaint(String id,String username, String date, String isHandled,String message) {
        this.id = new SimpleStringProperty(id);
        this.username = new SimpleStringProperty(username);
        this.date = new SimpleStringProperty(date);
        this.isHandled = new SimpleStringProperty(isHandled);
        this.message = new SimpleStringProperty(message);
    }
}
