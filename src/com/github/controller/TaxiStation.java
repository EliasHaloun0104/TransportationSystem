package com.github.controller;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaxiStation extends RecursiveTreeObject<TaxiStation> {
    StringProperty stationId;
    StringProperty stationName;
    public TaxiStation(){
        super();
    }

    public TaxiStation(String stationId, String stationName) {
        this.stationId = new SimpleStringProperty(stationId);
        this.stationName = new SimpleStringProperty(stationName);

    }
}
