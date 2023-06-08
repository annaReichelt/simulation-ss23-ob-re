package src.RouteData;

public class StationData implements Comparable<StationData>{

    private String stationName; // destination
    private int trackNumber; // exprected arival track
    private Time arrivalTime; // expected arrival time
    private Time departureTime; // expected departure time
    private int addInfo; // 0 = no info, 1 = startingStation, 2 = endingStation
    private String tripID; // tripID e.g. Name of the singel route
    private String serviceID; // service_id e.g. Name of the broad route
    private Week activeDays; // days the route is active

    public StationData(String stationName, int trackNumber , Time arrivalTime, Time departureTime, int addInfo, String tripID) {
        this.stationName = stationName;
        this.trackNumber = trackNumber;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.addInfo = addInfo;
        this.tripID = tripID;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setAddInfo(int addInfo) {
        this.addInfo = addInfo;
    }

    public int compareTo(StationData other) {
        return this.arrivalTime.compareTo(other.arrivalTime);
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getTripID() {
        return tripID;
    }

    public void setWeek(Week activeDays) {
        this.activeDays = activeDays;
    }

    public Week getWeek() {
        return activeDays;
    }

    public String getStationName() {
        return stationName;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public int getAddInfo() {
        return addInfo;
    }

    public String toString() {
        return stationName + " " + trackNumber + " " + arrivalTime + " " + departureTime + " " + addInfo;
    }
}
