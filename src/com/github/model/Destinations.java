package com.github.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Destinations {
    private static Destinations ourInstance = new Destinations();
    private ArrayList<Region> destinations;
    private ArrayList<Route> routes;


    public static Destinations getInstance() {
        return ourInstance;
    }
    public ArrayList<Region> getDestinations() {
        return destinations;
    }
    public ArrayList<Route> getRoutes() {
        return routes;
    }
    public ArrayList<Region> getDestinationExcept(String selected) {
        ArrayList<Region> newRegion = new ArrayList<>();
        for (Region p: destinations) {
            if(!p.equals(selected)){
                newRegion.add(p);
            }
        }
        return newRegion;
    }
    public void setDestinations(ArrayList<Region> region) {
        this.destinations = region;
    }

    public String getSomeThing(String from, String to){
        ArrayList<Route> route_From_To = new ArrayList<>();
        ArrayList<Route> route_From = new ArrayList<>();
        ArrayList<Route> route_To = new ArrayList<>();
        String clc = "Crownlands Center";
        for (int i = 0; i < routes.size(); i++) {
            boolean containFrom = routes.get(i).contains(from);
            boolean containTo = routes.get(i).contains(to);
            if(containFrom&& containTo) {
                try{
                    route_From_To.add(routes.get(i).subList(from, to));
                }catch (IndexOutOfBoundsException e){
                    //Do Nothing
                }

            }
            if(containFrom && !containTo){
                try {
                    route_From.add(routes.get(i).subList(from, clc));
                }catch (IndexOutOfBoundsException e){
                    //Do nothing;
                }
            }
            if(!containFrom && containTo){
                try {
                    route_To.add(routes.get(i).subList(clc, to));
                }catch (IndexOutOfBoundsException e){
                    //Do nothing
                }
            }
        }
        if(route_From.size()>0 && route_To.size()>0){
            for (Route f: route_From) {
                for (Route t: route_To) {
                    List<Region> tempList = f.getRoute();
                    tempList.addAll(t.getRoute());
                    Route r = new Route(f.getType(), tempList);
                    r.setType2(t.getType());
                    route_From_To.add(r);
                }
            }
        }
        String a = "FROM_TO\n";
        for (Route r: route_From_To) {
            a += r.toString();
        }


        return a;
    }

    private Destinations() {

        Region north = new Region(3554, "North", new Vector2D(5, 5));
        Region valleOfArryn = new Region(7773, "Valle of Arryn",new Vector2D(5,5));
        Region crownLand = new Region(2230, "Crownlands Center",new Vector2D(5,5));
        Region reach = new Region(4422, "Reach",new Vector2D(5,5));
        Region stormLands = new Region(4122, "Storm lands",new Vector2D(5,5));
        Region dorne = new Region(3232, "Dorne",new Vector2D(5,5));
        Region rockLand =new Region(8112, "Rockland",new Vector2D(5,5));
        City dt_city =new City(22301, "Daenerys Targaryen",new Vector2D(5,5), crownLand);
        City tl_city = new City(22302, "Tyrion Lannister", new Vector2D(5, 5), crownLand);
        City js_city = new City(22303,"Jon Snow",new Vector2D(5,5),crownLand);

        Route regionRoute_1 = new Route(Route.Type.TRAIN, north,valleOfArryn,crownLand,reach,stormLands,dorne,stormLands,reach,crownLand,valleOfArryn, north);
        Route regionRoute_2 = new Route(Route.Type.REGION_BUS,crownLand,rockLand, crownLand);
        Route regionRoute_3 = new Route(Route.Type.REGION_BUS,crownLand,reach, crownLand);
        Route regionRoute_4 = new Route(Route.Type.REGION_BUS,crownLand,valleOfArryn, crownLand);
        Route city_Route_1 = new Route(Route.Type.CITY_BUS,crownLand, tl_city, crownLand);
        Route city_Route_2 = new Route(Route.Type.CITY_BUS,crownLand, dt_city, crownLand);
        Route city_Route_3 = new Route(Route.Type.CITY_BUS,crownLand, js_city, crownLand);

        destinations = new ArrayList<>();
        destinations.addAll(Arrays.asList(north,valleOfArryn,crownLand,reach,stormLands,dorne,rockLand,dt_city, tl_city,js_city));

        routes = new ArrayList<>();
        routes.addAll(Arrays.asList(regionRoute_1,regionRoute_2,regionRoute_3,regionRoute_4,city_Route_1,city_Route_2,city_Route_3));



    }

}
