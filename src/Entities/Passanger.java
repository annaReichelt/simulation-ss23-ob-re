package src.Entities;
import java.util.HashSet;
import java.util.Random;

import desmoj.core.simulator.*;
import src.Config;
import src.TrainStation;
import src.RouteData.Time;

public class Passanger extends Entity {

    //100 as base happiness
    private int happiness = 100;
    private boolean onCorrectTrack = false;

    private int arrivalTrack;

    private String destinationName;
    private int expectedTravelTime;
    private int actualTravelTime;
    private double ticketPrice;

    private TrainStation StationModel;
    private Train arrTrain;
    private Train depTrain = null;

    //Track that the passanger needs to go to, if he changes trains
    private int targetTrack;
    
    public Passanger(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        this.StationModel = (TrainStation) owner;
    }


    /**
     * Creates a travel route for the passanger with the specified starting train and the specified case.
     * 
     * Case 0: Direct travel (The end of the TravelRoute will be determindes randomly; Either the travel ends in salzburg, or the passanger stays seated in the same train)
     * Case 1: Indirect travel (Passenger needs to change train in salzburg)
     * 
     * Case 1 is constructed such that it searches for random trains leaving salzburg approx. 10 - 60 minutes after planned arrival in salzburg.
     * 
     * @param caseID
     * @param train that the passanger will be traveling in first (ie. the train in which the pass. is instanciated)
     */
    public void createTravelRoute(int caseID, Train train) {
        if (caseID == 0) {
            createDirectTravelRoute(train);

        } else if (caseID == 1) {
            createRouteWithTrainChange(train);
        }
    }

    /**
     * Creates a random travel route for the passanger based on the current train queue. 
     * The randomization decides, whether or not the route will be direct, or a train change is required
     * @param train
     */
    public void createTravelRoute(Train train) {

        Random rd = new Random();
        double rdValue = rd.nextDouble(100);

        if (rdValue <= 50) {
            createDirectTravelRoute(train);

        } else {
            createRouteWithTrainChange(train);
        }
    }

    //No changing train is required | Travel ends in salzburg
    private void createDirectTravelRoute(Train train) {

        //randomly assign, if the passangers travel stop in salzburg
        //if not, then they only travel with that particular train onto oblivion

        Random rd = new Random();
        Double rdValue = rd.nextDouble(100);

        if (rdValue <= 50) {
            this.destinationName = "Salzburg Hauptbahnhof";
        } else {
            //TODO: Fix later for reliable identification
            //Where exactly does not matter to us
            this.destinationName = "Beyond"; 
        }
        collectTicketPayment();
    }

    private void collectTicketPayment() {
        Random rd = new Random();
        Double rdPrice = rd.nextDouble(15.00, 120.00);
        this.ticketPrice = rdPrice;

        StationModel.addRevenue(rdPrice);
    }

    /**
     * Uses the model Queue to find the next train leaving Salzburg 10 - 59 minutes after planned arrival and assigns
     * that train as the connecting train, which the passanger subscribes to. 
     * 
     * We assume that the first available train in that time frame is their connecting train due to their implecable travel planning skillz
     * 
     * @param train that the passanger starts in
     */
    private void createRouteWithTrainChange(Train train) {

        Time arrivalTime = train.getExpectedArrivalTime();
        Time difference;
        Time otherArrivalTime;

        //Security for the first instanciated train of the sim
        if (StationModel.trainQueue.isEmpty()) {
            createDirectTravelRoute(train);
        }

        for (Train connectingTrain : StationModel.trainQueue) {
            otherArrivalTime = connectingTrain.getExpectedArrivalTime();

            if (arrivalTime.compareTo(otherArrivalTime) == -1) {
                difference = arrivalTime.difference(otherArrivalTime);
                
                //difference needs to be larger than 10 minutes and smaller than 59 minutes (for simplicity to avoid dealing with 1h) 
                if (difference.hour == 0 && difference.minute > Config.MIN_CHANGE_TIME.getValue()) {

                    //this is the connecting train for this passanger
                    this.depTrain = connectingTrain;
                    subscribeToConnectingTrain(connectingTrain);
                    collectTicketPayment();
                    return;
                }
            }
        }

        //security if no matching train was found -> would otherwise create passangers without a route
        createDirectTravelRoute(train);
    }

    private void subscribeToConnectingTrain(Train train) {
        train.addSubscriber(this);
    }

    public void updateTargetTrack(int trackNo) {
        this.targetTrack = trackNo;
    }

    //Getters, Setters...
    public void setOnCorrectTrack(boolean bool) { this.onCorrectTrack = bool; }
    public void increaseHappiness(int amount) { this.happiness += amount; }
    public void changeArrivalTrack(int trackNo) { this.arrivalTrack = trackNo; }
    public void increaseTravelTime(int time) { this.actualTravelTime += time; }
    public int getHappiness() { return this.happiness;}
    public int getArrivalTrack() { return this.arrivalTrack; }
    public String getDestinationID() { return this.destinationName; }
    public int getExpectedTravelTime() { return this.expectedTravelTime;}
    public int getActualTravelTime() { return this.actualTravelTime; }
    public double getTicketPrice() { return this.ticketPrice; }
    public boolean isOnCorrectTrack() { return this.onCorrectTrack; }
}
