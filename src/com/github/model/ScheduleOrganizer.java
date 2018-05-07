package com.github.model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.*;


public class ScheduleOrganizer {
    HashMap<Integer, ArrayList<ScheduledRoute>> items = new HashMap<>();
    HashMap<Integer, ArrayList<ScheduledRoute>> searchResults;

    public synchronized void addToList(Integer special_ID, ScheduledRoute myItem) {
        List<ScheduledRoute> itemsList = items.get(special_ID);
        // if list does not exist create it
        if(itemsList == null) {
            itemsList = new ArrayList<>();
            itemsList.add(myItem);
            items.put(special_ID, (ArrayList<ScheduledRoute>) itemsList);
        } else {
            // add if item is not already in list
            if(!itemsList.contains(myItem)) itemsList.add(myItem);
        }
    }

    public void findRouteContain2Value(int from, int to){ //Search for route contains 2 value
        searchResults = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<ScheduledRoute>> entry : items.entrySet()) {
            int a = entry.getKey();
            for (int i = 0; i < entry.getValue().size() ; i++) {
                if(entry.getValue().get(i).getStation_from()== from){
                    for (int j = i; j < entry.getValue().size() ; j++) {
                        if(entry.getValue().get(j).getStation_to() == to){
                            searchResults.put(a, new ArrayList<>(entry.getValue().subList(i,j+1)));
                        }
                    }
                }
                //price.put(entry.getKey(), getPrice(entry.getValue()));
            }
        }
    }

    public GridPane getText(int from, int to){
        int rowNo = 0;
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("From"),0,rowNo);
        gridPane.add(new Label("Time"),1,rowNo);
        gridPane.add(new Label("To"),2,rowNo);
        gridPane.add(new Label("Time"),3,rowNo);
        gridPane.add(new Label("Price"),4,rowNo);
        gridPane.add(new Label("Book"),5,rowNo);
        rowNo++;
        findRouteContain2Value(from, to);
        for(Map.Entry<Integer, ArrayList<ScheduledRoute>> entry : searchResults.entrySet()) {
            gridPane.add(new Label(Destinations.getInstance().getStations().get(entry.getValue().get(0).getStation_from()).toString()),0,rowNo);
            gridPane.add(new Label(entry.getValue().get(0).getStartTime().toString()),1,rowNo);
            gridPane.add(new Label(Destinations.getInstance().getStations().get(entry.getValue().get(entry.getValue().size()-1).getStation_to()).toString()),2,rowNo);
            gridPane.add(new Label(entry.getValue().get(entry.getValue().size()-1).getEndTime().toString()),3,rowNo);
            gridPane.add(new Label(0 + " GD"),4,rowNo);

            Button bookButton = new Button("Book");
            // TODO replace the Cody94 by Account.name
            bookButton.setOnAction(event -> makeBooking(0,"Cody94",entry.getKey()));
            gridPane.add(bookButton,5,rowNo);
            rowNo++;


        }
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        gridPane.setHgap(22);
        return gridPane;
    }

    public void makeBooking(int amount, String accountId, int route_Id){
        DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
        dbConnection.makeBooking(amount,accountId,route_Id);
        System.out.println("DONE");
    }




    public ArrayList<Vehicle> generateVehicles(){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        for(Map.Entry<Integer, ArrayList<ScheduledRoute>> entry : items.entrySet()) {
            for (ScheduledRoute t: entry.getValue()) {
                boolean bol_1 = Calendar.getInstance().get(Calendar.MINUTE)>= t.getStartTime().getMinute();
                boolean bol_2 = Calendar.getInstance().get(Calendar.MINUTE)< t.getEndTime().getMinute();
                boolean bol_3 = (t.getStartTime().getMinute()>t.getEndTime().getMinute()) && !bol_1 && !bol_2;
                if((bol_1 && bol_2) || bol_3){
                    vehicles.add(new Vehicle(t));
                }
            }
        }
        return vehicles;
    }
}
