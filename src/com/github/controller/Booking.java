package com.github.controller;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Booking extends RecursiveTreeObject<Booking> {

    StringProperty bookingId;
    StringProperty accountUserName;
    StringProperty fromStation;
    StringProperty toStation;
    StringProperty routId;
    StringProperty amount;
    StringProperty date;

public Booking(){
    super();
}

    public Booking(String bookingId, String accountUserName, String fromStation, String toStation, String routId,
                   String amount, String date) {
        this.bookingId = new SimpleStringProperty(bookingId);
        this.accountUserName = new SimpleStringProperty(accountUserName);
        this.fromStation = new SimpleStringProperty(fromStation);
        this.toStation = new SimpleStringProperty(toStation);
        this.routId = new SimpleStringProperty(routId);
        this.amount = new SimpleStringProperty(amount);
        this.date = new SimpleStringProperty(date);
    }
}