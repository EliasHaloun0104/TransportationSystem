package com.github.model;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.ParseException;
import java.util.*;

public class ScheduleOrganizer {
    HashMap<Integer, ArrayList<ScheduledRoute>> items = new HashMap<>();

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

    public HashMap<Integer, ArrayList<ScheduledRoute>> findRouteContain2Value(int from, int to){ //Search for route contains 2 value
        HashMap<Integer, ArrayList<ScheduledRoute>> results = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<ScheduledRoute>> entry : items.entrySet()) {
            int a = entry.getKey();
            for (int i = 0; i < entry.getValue().size() ; i++) {
                if(entry.getValue().get(i).getStation_from()== from){
                    for (int j = i; j < entry.getValue().size() ; j++) {
                        if(entry.getValue().get(j).getStation_to() == to){
                            results.put(a, new ArrayList<>(entry.getValue().subList(i,j+1)));
                        }
                    }
                }
                //price.put(entry.getKey(), getPrice(entry.getValue()));
            }
        }
        return results;
    }

    public VBox getText(int from, int to){
        VBox vBox = new VBox();
        for(Map.Entry<Integer, ArrayList<ScheduledRoute>> entry : findRouteContain2Value(from, to).entrySet()) {
            String a = String.format("FROM %-12s @ %s, TO %-12s @ %s ",
                    Destinations.getInstance().getStations().get(entry.getValue().get(0).getStation_from()),
                    entry.getValue().get(0).getStartTime(),
                    Destinations.getInstance().getStations().get(entry.getValue().get(entry.getValue().size()-1).getStation_to()),
                    entry.getValue().get(entry.getValue().size()-1).getEndTime());
            HBox hBox = new HBox();
            Button button = new Button("Book");
            // TODO replace the Cody94 by Account.name
            button.setOnAction(event -> makeBooking(0,"Cody94",entry.getKey()));
            hBox.getChildren().addAll(new Label(a +"\\  " + 0 + " GD  "), button);
            vBox.getChildren().add(hBox);
        }
        return vBox;
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
                try {
                    TimeProcess tp_0 = TimeProcess.now(0);
                    TimeProcess tp_1 = TimeProcess.now(1);
                    if(t.isTimeBetween(tp_0) || t.isTimeBetween(tp_1)){
                        vehicles.add(new Vehicle(t));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return vehicles;
    }
}
