package src.RouteData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import com.google.common.collect.TreeMultiset;

/**
 * This class handles getting and cleaning the raw data
 */

public class RouteData {

    private String stationName;
    private int trackNumber;
    private TreeMultiset<StationData> stationsServed;
    private TreeSet<String> trip_ids;
    private ArrayList<String> trackIDs;
    private ArrayList<Integer> trackNumbers;
    private Map<String, String> trips; //trip_id, service_id
    private Map<String, Week> calendar; //service_id, Week 


    public RouteData(String stationName) {
        this.stationName = stationName;
        trackNumber = 0;
        stationsServed = TreeMultiset.create();
        trackIDs = new ArrayList<String>();
        trackNumbers = new ArrayList<Integer>();
        trips = new HashMap<String, String>();
        trip_ids = new TreeSet<String>();
        calendar = new TreeMap<String, Week>();

        crunchData();
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public ArrayList<Integer> getTrackIDs() {
        return trackNumbers;
    }

    public TreeMultiset<StationData> getStationsServed() {
        // copy the data
        TreeMultiset<StationData> copy = TreeMultiset.create();
        for(StationData station : stationsServed){
            copy.add(station);
        }
        System.out.println("Stations served: " + copy.size());
        return copy;
    }

    private void crunchData() {
        // read from file stops.txt
        String rawPath = "src/RouteData/rawData";
        String filePath = rawPath + "/stops.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // line by line
                // stop_id, stop_name, stop_lat, stop_lon, zone_id
                String[] values = line.split(",");
                if(values[1].contains(stationName)) {
                    trackNumber++;
                    trackIDs.add(values[0]);
                }     
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // convert trackIDs to trackNumbers
        for(int i = 0; i < trackIDs.size(); i++) {
            String[] trackNr = trackIDs.get(i).replace("\"", "").split(":");
            trackNumbers.add(Integer.parseInt(trackNr[trackNr.length - 1]));
        }

        // read from file stop_times.txt
        boolean stop = false;
        StationData stationData = new StationData("", -1, new Time("00:00:00"), new Time("00:00:00"), 0, ""); //so compiler doesn't complain
        filePath = rawPath + "/stop_times.txt";
         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // line by line
                // trip_id, arrival_time, departure_time, stop_id, stop_sequence, stop_headsign, pickup_type, drop_off_type, shape_dist_traveled
                String[] values = line.split(",");
                if(stop) {
                    stationData.setStationName(values[3]);
                    if(values[8].replace("\"", "") == "0.00" )
                        // ending station
                        stationData.setAddInfo(2);
                    stop = false;
                }
                if(trackIDs.contains(values[3])) {
                    String[] trackNr = values[3].replace("\"", "").split(":");
                    int addInfo = 0;
                    //shape_dist_traveled == 0 -> starting station
                    if(values[8].replace("\"", "") == "0.00" )
                        addInfo = 1;
                    stationData = new StationData("", Integer.parseInt(trackNr[trackNr.length - 1]) ,
                                                    new Time(values[1]), new Time(values[2]), addInfo, values[0]);
                    stationsServed.add(stationData);
                    trip_ids.add(values[0]);
                    stop = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // read from file trips.txt
        filePath = rawPath + "/trips.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // line by line
                // route_id, service_id, trip_id, shape_id, trip_headsign, direction_id, block_id
                String[] values = line.split(",");
                if(trip_ids.contains(values[2])) {
                    trips.put(values[2], values[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add service_id to stationsServed
        for(StationData station : stationsServed){
            if(trips.containsKey(station.getTripID())) {
                station.setServiceID(trips.get(station.getTripID()));
            }
        }

        // read from file calendar.txt
        filePath = rawPath + "/calendar.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
          String line;
            while ((line = br.readLine()) != null) {
                // line by line
                // service_id, monday, tuesday, wednesday, thursday, friday, saturday, sunday, start_date, end_date
                String[] values = line.split(",");
                Week week = new Week(values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                calendar.put(values[0], week);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add week to stationsServed
        for (StationData station : stationsServed) {
            if(calendar.containsKey(station.getServiceID())) {
                station.setWeek(calendar.get(station.getServiceID()));
            }
        }
    }

    public static void main(String[] args) {
        RouteData routeData = new RouteData("Salzburg Hauptbahnhof");
        TreeMultiset<StationData> data = routeData.getStationsServed();
        System.out.println(data.size());
        /*for(StationData station : data) {
            System.out.println(station.getStationName() + " " + station.getTrackNumber() + " " + station.getArrivalTime() + " " + station.getDepartureTime() + " " + station.getAddInfo() + " " + station.getWeek());
        }*/
        for(int i = 0; i < 10; i++) {
            System.out.println(data.pollFirstEntry().getElement());
        }



    }

}