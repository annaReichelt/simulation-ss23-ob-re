package src;
import java.util.ArrayList;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.*;
import src.Entities.*;
import src.RouteData.*;
import src.Events.*;

public class TrainStation extends Model{

    private int revenewFromTicketSales = 0;
    private int lossesFromRefunds = 0;
    private int delayOfAllTrains = 0;
    private String stationName;
    private StationGenerator sg;
    public int totalTrains = 0;

    private Track[] trainTracks;

    /*public TrainStation(Model owner, String name, boolean showInReport, 
            boolean showIntrace, String stationName, int amountOfTracks) {
        super(owner, name, showInReport, showIntrace);
        this.stationName = stationName;

        createTracksPrimitive(amountOfTracks);
    }*/

    public TrainStation(Model owner, String name, boolean showInReport, 
            boolean showIntrace) {
        // implemention with real Data
        super(owner, name, showInReport, showIntrace);
    }

    private void createTracksPrimitive(int amountOfTracks) {
        this.trainTracks = new Track[amountOfTracks];
        for (int i = 0; i < trainTracks.length; i++) {
            trainTracks[i] = new Track(this, "Track " + (i+1), true, i+1);
        }
    }

    private void createTracks(int amountOfTracks) {
        this.trainTracks = new Track[amountOfTracks];
        ArrayList<Integer> trackIDs = sg.getTrackIDs();
        for (int i = 0; i < trainTracks.length; i++) {
            trainTracks[i] = new Track(this, "Track " + trackIDs.get(i), true, i+1);
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
        for(Track track : trainTracks) {
            if(track.getTrackNo() == trackNo) {
                return track.isFree();
            }
        }
        return false;
    }

    public int getFreeTrackNo() {
        for (int i = 0; i < trainTracks.length; i++) {
            if (trainTracks[i].isFree()) return i+1;
        }
        return -1;
    }

    //Getters, Setters...
    public void addLosses(int amount) { this.lossesFromRefunds += amount; }
    public void addTicketPrice(int price) { this.revenewFromTicketSales += price; }
    public String getStationName() { return this.stationName; }
    public int getdelayOfAllTrains() { return this.delayOfAllTrains; }
    public int getRevenue() { return this.revenewFromTicketSales; }
    public int getProfit() { return this.revenewFromTicketSales - this.lossesFromRefunds; }



    // Modelkomponents:

    private ContDistExponential delayTime;

    public double getDelayTime() {
        return 0.0;
//        return delayTime.sample();
    }

    // if no track is available, the train has to wait
    public Queue<Train> trainQueue;

    public String description() {
        return "This model describes a train station with " + trainTracks.length + " tracks.";
    }

    public void doInitialSchedules() {
        EventGenerator eg = new EventGenerator(this, "EventGenerator", true);
        eg.schedule(new TimeInstant(0.0));
    }

    public void init() {

        // train Queue init
        // Par 1: Modellzugehoerigkeit
    	// Par 2: Name der Warteschlange
    	// Par 3: show in report?
    	// Par 4: show in trace?
        trainQueue = new Queue<Train>(this, "TrainQueue", true, true);

        // delayTime init
        // Par 1: Modellzugehoerigkeit
        // Par 2: Name des Generators
        // Par 3: mittlere zusätzliche Verzögerungszeit
        // Par 4: show in report?
        // Par 5: show in trace?
        delayTime = new ContDistExponential(this, "DelayTime", 3.0, true, true);
        delayTime.setNonNegative(true);

        sg = StationGenerator.getInstance();
        this.stationName = sg.getMainsStaion();
        createTracks(sg.getTrackNumber());
    }

    public static void main(String[] args) {
        // neues Experiment erzeugen
    	// ATTENTION!
    	// Use as experiment name a OS filename compatible string!!
    	// Otherwise your simulation will crash!!
        Experiment trainStationExperiment = new Experiment("TrainStationExperiment");

        
        TrainStation trainStationModel = new TrainStation(null, "TrainStation", true, true);

        trainStationModel.connectToExperiment(trainStationExperiment);

        trainStationExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(10*60));
        trainStationExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(10*60));

        trainStationExperiment.stop(new TimeInstant(60 * 24 * 7));

        trainStationExperiment.start();

        trainStationExperiment.report();

        trainStationExperiment.finish();
        System.out.println(trainStationModel.totalTrains);
    }
}
