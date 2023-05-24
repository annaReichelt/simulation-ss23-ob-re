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
            trainTracks[i] = new Track(i);
        }
    }
}
