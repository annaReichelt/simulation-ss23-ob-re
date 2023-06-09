package src.RouteData;

import java.util.ArrayList;

import com.google.common.collect.TreeMultiset;

//Singleton class
public class StationGenerator {

    private static StationGenerator instance = null;
    private RouteData routeData;
    private TreeMultiset<StationData> data;
    private String mainStation;

    private StationGenerator(String name) {
        routeData = new RouteData(name);
        data = routeData.getStationsServed();
        mainStation = name;
    }
    public static StationGenerator getInstance() {
        if (instance == null) {
            instance = new StationGenerator("Salzburg Hauptbahnhof");
        }
        return instance;
    }

    public String getMainsStaion(){
        return mainStation;
    }

    public StationData getNextStationData(){
        return data.pollFirstEntry().getElement();
    }

    public int getTrackNumber(){
        return routeData.getTrackNumber();
    }

    public ArrayList<Integer> getTrackIDs(){
        return routeData.getTrackIDs();
    }

    public ArrayList<StationData> getNextStaionsUntil(Time time){
        ArrayList<StationData> nextStations = new ArrayList<StationData>();
        for(StationData station : data){
            if(station.getArrivalTime().compareTo(time) == -1){
                nextStations.add(station);
            }
            else{
                // because the data is sorted by time, we can break here
                break;
            }
        }
        return nextStations;
    }


}
