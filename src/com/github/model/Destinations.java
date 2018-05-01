package com.github.model;


import java.util.ArrayList;


public class Destinations {
    private static Destinations ourInstance = new Destinations();
    private ArrayList<Station> stations;
    ArrayList<ScheduledRoute> scheduledRoutes;


    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }


    public ArrayList<Station> getStations() {
        return stations;
    }

    public Station getStationByName(String name){
        for (Station s: stations) {
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }
    public ArrayList<String> getStationsName(){
        ArrayList<String> stationName = new ArrayList<>();
        for (Station s: stations) {
            stationName.add(s.getName());
        }
        return stationName;
    }
    //Get station name except the chosen one
    public ArrayList<String> getStationNameExcept(String station){
        ArrayList<String> stationName = new ArrayList<>();
        for (Station s: stations) {
            if(!station.equals(s.getName())){
                stationName.add(s.getName());
            }

        }
        return stationName;
    }


    public static Destinations getInstance() {
        return ourInstance;
    }


    private Destinations() {

        DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
        stations = dbConnection.getStations();
        dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
        scheduledRoutes = dbConnection.getRoutesFFF();

    }

    public static Destinations getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Destinations ourInstance) {
        Destinations.ourInstance = ourInstance;
    }

    public ArrayList<ScheduledRoute> getScheduledRoutes() {
        return scheduledRoutes;
    }

    public void setScheduledRoutes(ArrayList<ScheduledRoute> scheduledRoutes) {
        this.scheduledRoutes = scheduledRoutes;
    }
}
