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
    public Station getStationByID(int ID){
        for (Station s: stations) {
            if(s.getID() == ID){
                return s;
            }
        }
        return null;
    }

    public static Destinations getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Destinations ourInstance) {
        Destinations.ourInstance = ourInstance;
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

}
