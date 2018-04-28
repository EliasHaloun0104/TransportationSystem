package com.github.model;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteCalculate {
    HashMap<Integer, ArrayList<ScheduledRoute>> items = new HashMap<>();
    HashMap<Integer, Integer> price = new HashMap<>();

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
    public VBox getText(){
        VBox vBox = new VBox();


        for(Map.Entry<Integer, ArrayList<ScheduledRoute>> entry : items.entrySet()) {
            String a = entry.getKey() + ":" + entry.getValue().get(0).getFrom() + ": " + entry.getValue().get(0).getStart() +" " + entry.getValue().get(entry.getValue().size()-1).getTo() + ": "  +entry.getValue().get(entry.getValue().size()-1).getEnd();
            HBox hBox = new HBox();
            hBox.getChildren().addAll(new Label(a +"\\  " + getPrice(entry.getValue()) + " GD  "), new Button("Book"));
            vBox.getChildren().add(hBox);
        }
        return vBox;
    }

    public void getResult(String from, String to){
        for(Map.Entry<Integer, ArrayList<ScheduledRoute>> entry : items.entrySet()) {
            int a = entry.getKey();
            ArrayList<ScheduledRoute> newScheduledRoute;
            for (int i = 0; i < entry.getValue().size() ; i++) {
                if(entry.getValue().get(i).getFrom().equals(from)){
                    for (int j = i; j < entry.getValue().size() ; j++) {
                        if(entry.getValue().get(j).getTo().equals(to)){
                            newScheduledRoute = new ArrayList<>(entry.getValue().subList(i,j+1));                         items.put(a,newScheduledRoute);
                        }
                    }
                }
                price.put(entry.getKey(), getPrice(entry.getValue()));
            }

        }
    }

    private int getPrice(ArrayList<ScheduledRoute> scheduledRoutes){
        int price = 0;
        for (ScheduledRoute s: scheduledRoutes
             ) {
            price += s.getPrice();
        }
        return price;
    }

}
