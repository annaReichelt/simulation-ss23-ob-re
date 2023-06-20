package src;
import java.util.*;

import org.hamcrest.CustomMatcher;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.*;
//import desmoj.extensions.visualization2d.engine.model.Statistic;
import desmoj.core.simulator.Queue;
import src.Entities.*;
import src.RouteData.*;
import src.Events.*;
import src.Policy.CustomerService;
import src.Policy.Statistics;

public class TrainStation extends Model{

    private static double revenewFromTicketSales = 0;
    private static double lossesFromRefunds = 0;
    private int delayOfAllTrains = 0;
    private String stationName;
    private StationGenerator sg;
    public int totalTrains = 0;

    private Map<Integer, Track> trainTracks;
    public HashSet<Train> trainsToCome;

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

    private void createTracks(ArrayList<Integer> trackIDs) {
        this.trainTracks = new HashMap<Integer, Track>();
        for (int i : trackIDs) {
            trainTracks.put(i, new Track(this, "Track " + i, true, i));
        }

    }

    /**
     * Gets you the specified track within the station
     * @param number of track (ID)
     * @return track with specified number (ID) iff it exists.
     */
    public Track getTrackNo(int number) {
        return trainTracks.get(number);
    }



    public Track whereIsTrainStanding(Train train) {
        int id = train.getActualArrivalTrack();
        return trainTracks.get(id);
    }

    public Track getMyCurrentTrack(int trackNum) {
        Track t = getTrackNo(trackNum);
        if (t != null) {
            return t;
        }
        throw new Error("The track you were looking for dosn't exist. Looked for Track #" + trackNum);
    }

    public boolean isTrackAvailable(int trackNo) {
        return getTrackNo(trackNo).isFree();
    }

    public int getFreeTrackNo() {
        for (Track t : trainTracks.values()) {
            if (t.isFree()) {
                return t.getTrackNo();
            }
        }
        return -1;
    }

    //Getters, Setters...
    public void addLossesFromRefunds(double amount) { lossesFromRefunds += amount; }
    public void addRevenue(double price) { revenewFromTicketSales += price; }
    public String getStationName() { return this.stationName; }
    public int getdelayOfAllTrains() { return this.delayOfAllTrains; }
    public static double getRevenue() { return revenewFromTicketSales; }
    public static double getProfit() { return revenewFromTicketSales - lossesFromRefunds; }



    // Modelkomponents:

    private ContDistExponential delayTime;

    public double getDelayTime() {
        return 0.0;
        //return delayTime.sample();
    }

    // if no track is available, the train has to wait
    public Queue<Train> trainQueue;

    public String description() {
        return "This model describes a train station with " + trainTracks.size() + " tracks.";
    }

    public void doInitialSchedules() {
        TrainGenerator eg = new TrainGenerator(this, "EventGenerator", true);
        eg.schedule(new TimeInstant(0.0));
    }

    public void init() {

        // train Queue init
        // Par 1: Modellzugehoerigkeit
    	// Par 2: Name der Warteschlange
    	// Par 3: show in report?
    	// Par 4: show in trace?
        trainQueue = new Queue<Train>(this, "TrainQueue", true, true);

        trainsToCome = new HashSet<Train>();

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
        createTracks(sg.getTrackIDs());
    }

    public static void main(String[] args) {
        // neues Experiment erzeugen
    	// ATTENTION!
    	// Use as experiment name a OS filename compatible string!!
    	// Otherwise your simulation will crash!!
        Experiment trainStationExperiment = new Experiment("TrainStationExperiment");


        TrainStation trainStationModel = new TrainStation(null, "TrainStation", true, true);
        Logger.getInstance().addModel(trainStationModel);

        CustomerService.GetInstance().addModelReference(trainStationModel);

        trainStationModel.connectToExperiment(trainStationExperiment);

        trainStationExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(10*60));
        trainStationExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(10*60));

        trainStationExperiment.stop(new TimeInstant(60 * 24 * 7));

        trainStationExperiment.start();

        trainStationExperiment.report();

        trainStationExperiment.finish();

        System.out.println(trainStationModel.totalTrains);
        System.out.println("Revenue: " + getRevenue());
        System.out.println("Losses: " + getRevenue());
        System.out.println("Profit: " + getProfit());
        Statistics.getInstance().printStats();
    }
}
