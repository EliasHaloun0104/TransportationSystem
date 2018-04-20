package com.github.model;

public class Enumeration {
    public enum VehicleType{
        TRAIN, ISOMETRIC_TRAIN,
        REGION_BUS, ISOMETRIC_REGION_BUS,
        CITY_BUS,ISOMETRIC_CITY_BUS,
        TAXI;

        public static int getVehicleSpeed(VehicleType type){
            switch (type){
                case TRAIN:
                    return 130;
                case ISOMETRIC_TRAIN:
                    break;
                case REGION_BUS:
                    return 110;
                case ISOMETRIC_REGION_BUS:
                    break;
                case CITY_BUS:
                    return 70;
                case ISOMETRIC_CITY_BUS:
                    break;
                case TAXI:
                    break;

            }
            return 0;
        }
    }
    public enum VehicleSituation{
            OUT_OF_SERVICE,
            STOP_SWITCH_DESTINATION,
            STOP,
            RUN
    }


}
