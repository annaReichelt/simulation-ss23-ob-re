package Entities;

public class TrainStation {

    private int revenewFromTicketSales = 0;
    private int lossesFromRefunds = 0;
    private int delayOfAllTrains = 0;
    private int stationID;

    private Track[] trainTracks;

    public TrainStation(int stationID, int amountOfTracks) {
        this.trainTracks = new Track[amountOfTracks];
        this.stationID = stationID;

        createTracks();
    }

    private void createTracks() {
        for (int i = 0; i < trainTracks.length; i++) {
            trainTracks[i] = new Track(i + 1);
        }
    }

    
    /**
     * Gets you the specified track within the station
     * @param number of track (ID)
     * @return track with specified number (ID) iff it exists.
     */
    public Track getTrackNo(int number) {
        number--;
        if (number >= trainTracks.length) return null; //TODO: Throw good error/log
        return this.trainTracks[number];
    }

    public boolean isTrackAvailable(int trackNo) {
        trackNo--;
        return trainTracks[trackNo].isAvailable();
    }

    //Getters, Setters...
    public void addLosses(int amount) { this.lossesFromRefunds += amount; }
    public void addTicketPrice(int price) { this.revenewFromTicketSales += price; }
    public int getStationID() { return this.stationID; }
    public int getdelayOfAllTrains() { return this.delayOfAllTrains; }
    public int getRevenue() { return this.revenewFromTicketSales; }
    public int getProfit() { return this.revenewFromTicketSales - this.lossesFromRefunds; }

}
