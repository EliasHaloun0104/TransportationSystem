package com.github.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.model.Enumeration.VehicleType.*;

public class Destinations {
    private static Destinations ourInstance = new Destinations();
    private ArrayList<Region> destinations;
    private ArrayList<Route> routes;
    private ArrayList<ScheduledRoute> ScheduledRoutes;

    public static Destinations getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Destinations ourInstance) {
        Destinations.ourInstance = ourInstance;
    }

    public void setDestinations(ArrayList<Region> destinations) {
        this.destinations = destinations;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public ArrayList<ScheduledRoute> getScheduledRoutes() {
        return ScheduledRoutes;
    }

    public void setScheduledRoutes(ArrayList<ScheduledRoute> ScheduledRoutes) {
        this.ScheduledRoutes = ScheduledRoutes;
    }

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

        Region north = new Region(3554, "North", new Vector2D(745, 140));
        Region valleOfArryn = new Region(7773, "Valle of Arryn",new Vector2D(753,403));
        Region crownLand = new Region(2230, "Crownlands Center",new Vector2D(478,320));
        Region reach = new Region(4422, "Reach",new Vector2D(360,573));
        Region stormLands = new Region(4122, "Storm lands",new Vector2D(146,442));
        Region dorne = new Region(3232, "Dorne",new Vector2D(82,189));
        Region rockLand =new Region(8112, "Rockland",new Vector2D(370,177));

        City daenerys_targaryen =new City(22301, "Daenerys Targaryen",new Vector2D(84.5f,337.5f), crownLand);
        City daenerys_targaryen_1 =new City(22301, "Daenerys Targaryen",new Vector2D(78.5f,189.5f), crownLand);
        City daenerys_targaryen_2 =new City(22301, "Daenerys Targaryen",new Vector2D(211.5f,142.5f), crownLand);
        City daenerys_targaryen_3 =new City(22301, "Daenerys Targaryen",new Vector2D(359.5f,212.5f), crownLand);
        City tyrion_lannister = new City(22302, "Tyrion Lannister",new Vector2D(223f,416f) , crownLand);
        City tyrion_lannister_1 = new City(22302, "Tyrion Lannister",new Vector2D(115f,545f) , crownLand);
        City tyrion_lannister_2 = new City(22302, "Tyrion Lannister", new Vector2D(357f,510f) , crownLand);
        City tyrion_lannister_3 = new City(22302, "Tyrion Lannister", new Vector2D(539f,598f) , crownLand);
        City jon_snow = new City(22303,"Jon Snow",new Vector2D(529,107),crownLand);
        City jon_snow_1 = new City(22303,"Jon Snow",new Vector2D(695,240),crownLand);
        City jon_snow_2 = new City(22303,"Jon Snow",new Vector2D(529,107),crownLand);
        City jon_snow_3 = new City(22303,"Jon Snow",new Vector2D(867,164),crownLand);
        City jon_snow_4 = new City(22303,"Jon Snow",new Vector2D(858,338),crownLand);
        City jon_snow_5 = new City(22303,"Jon Snow",new Vector2D(852,541),crownLand);
        City jon_snow_6 = new City(22303,"Jon Snow",new Vector2D(668,444),crownLand);

        Route regionRoute_1 = new Route(0,TRAIN, north,valleOfArryn,crownLand,reach,stormLands,dorne,stormLands,reach,crownLand,valleOfArryn, north);
        Route regionRoute_2 = new Route(1,REGION_BUS,crownLand,rockLand, crownLand);
        Route regionRoute_3 = new Route(2,REGION_BUS,crownLand,reach, crownLand);
        Route regionRoute_4 = new Route(3,REGION_BUS,crownLand,valleOfArryn, crownLand);
        Route city_Route_1 = new Route(4,CITY_BUS,crownLand, tyrion_lannister,tyrion_lannister_1,tyrion_lannister_2,tyrion_lannister_3, crownLand);
        Route city_Route_2 = new Route(5,CITY_BUS,crownLand, daenerys_targaryen,daenerys_targaryen_1, daenerys_targaryen_2, daenerys_targaryen_3, crownLand);
        Route city_Route_3 = new Route(6,CITY_BUS,crownLand, jon_snow, jon_snow_1,jon_snow_2,jon_snow_3,jon_snow_4,jon_snow_5,jon_snow_6, crownLand);

        ScheduledRoute ScheduledRoute_R_1_A = new ScheduledRoute(regionRoute_1,4);
        ScheduledRoute ScheduledRoute_R_1_B = new ScheduledRoute(regionRoute_1,34);
        ScheduledRoute ScheduledRoute_R_2_A = new ScheduledRoute(regionRoute_2,8);
        ScheduledRoute ScheduledRoute_R_2_B = new ScheduledRoute(regionRoute_2,18);
        ScheduledRoute ScheduledRoute_R_2_C = new ScheduledRoute(regionRoute_2,28);
        ScheduledRoute ScheduledRoute_R_2_D = new ScheduledRoute(regionRoute_2,38);
        ScheduledRoute ScheduledRoute_R_2_E = new ScheduledRoute(regionRoute_2,48);
        ScheduledRoute ScheduledRoute_R_2_F = new ScheduledRoute(regionRoute_2,58);
        ScheduledRoute ScheduledRoute_R_3_A = new ScheduledRoute(regionRoute_3,7);
        ScheduledRoute ScheduledRoute_R_3_B = new ScheduledRoute(regionRoute_3,37);
        ScheduledRoute ScheduledRoute_R_4_A = new ScheduledRoute(regionRoute_4,14);
        ScheduledRoute ScheduledRoute_R_4_B = new ScheduledRoute(regionRoute_4,34);
        ScheduledRoute ScheduledRoute_R_4_C = new ScheduledRoute(regionRoute_4,54);
        ScheduledRoute ScheduledRoute_C_1_A = new ScheduledRoute(city_Route_1,6);
        ScheduledRoute ScheduledRoute_C_1_B = new ScheduledRoute(city_Route_1,26);
        ScheduledRoute ScheduledRoute_C_1_C = new ScheduledRoute(city_Route_1,46);
        ScheduledRoute ScheduledRoute_C_2_A = new ScheduledRoute(city_Route_2,12);
        ScheduledRoute ScheduledRoute_C_2_B = new ScheduledRoute(city_Route_2,32);
        ScheduledRoute ScheduledRoute_C_2_C = new ScheduledRoute(city_Route_2,52);
        ScheduledRoute ScheduledRoute_C_3_A = new ScheduledRoute(city_Route_3,0);


        destinations = new ArrayList<>();
        destinations.addAll(Arrays.asList(
            north,
            valleOfArryn,
            crownLand,reach,
            stormLands,
            dorne,
            rockLand,
            daenerys_targaryen,
            daenerys_targaryen_1,
            daenerys_targaryen_2,
            daenerys_targaryen_3,
            tyrion_lannister,
            tyrion_lannister_1,
            tyrion_lannister_2,
            tyrion_lannister_3,
            jon_snow,
            jon_snow_1,
            jon_snow_2,
            jon_snow_3,
            jon_snow_4,
            jon_snow_5,
            jon_snow_6

            ));

        routes = new ArrayList<>();
        routes.addAll(Arrays.asList(regionRoute_1,regionRoute_2,regionRoute_3,regionRoute_4,city_Route_1,city_Route_2,city_Route_3));

        ScheduledRoutes = new ArrayList<>();
        ScheduledRoutes.addAll(Arrays.asList(
            ScheduledRoute_R_1_A,
            ScheduledRoute_R_1_B,
            ScheduledRoute_R_2_A,
            ScheduledRoute_R_2_B,
            ScheduledRoute_R_2_C,
            ScheduledRoute_R_2_D,
            ScheduledRoute_R_2_E,
            ScheduledRoute_R_2_F,
            ScheduledRoute_R_3_A,
            ScheduledRoute_R_3_B,
            ScheduledRoute_R_4_A,
            ScheduledRoute_R_4_B,
            ScheduledRoute_R_4_C,
            ScheduledRoute_C_1_A,
            ScheduledRoute_C_1_B,
            ScheduledRoute_C_1_C,
            ScheduledRoute_C_2_A,
            ScheduledRoute_C_2_B,
            ScheduledRoute_C_2_C,
            ScheduledRoute_C_3_A
        ));

    }

}
