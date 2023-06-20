package src.Entities;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import desmoj.core.simulator.*;
import src.Config;
import src.TrainStation;
import src.Policy.CustomerService;
import src.Policy.Statistics;
import src.RouteData.Time;

public class Passanger extends Entity {

    //50 as base happiness
    private int happiness = 50;
    private boolean onCorrectTrack = false;

    private int arrivalTrack;

    private String destinationName;
    private int expectedTravelTime;
    private int actualTravelTime;
    private double ticketPrice;

    private TrainStation StationModel;
    public Train connectingTrain = null;
    private boolean trainIsCanceled = false;
    private boolean trainMissed = false;

    //0 = Salzburg is endstation, 1 = Continues travel in CURRENT train, 2 = needs to transfere to a differen train
    private int travelRouteAttribute;   

    //Track that the passanger needs to go to, if he changes trains
    public int targetTrack;
    
    public Passanger(Model owner, String name, boolean showInTrace) {
        super(owner, name, Config.TRACE_PASSANGERS.getBool());
        this.StationModel = (TrainStation) owner;
        CustomerService.GetInstance().addTraveler(this);
    }

    /**
     * Creates a random travel route for the passanger based on the current train queue. 
     * The randomization decides, whether or not the route will be direct, or a train change is required
     * @param train
     */
    public void createTravelRoute(Train train, ArrayList<Train> potentialConnectionTrains) {

        if (potentialConnectionTrains.isEmpty()) {
            createDirectTravelRoute(train);

        } else {
            
            Random rd = new Random();
            double rdValue = rd.nextDouble(100);

            if (rdValue <= 50) {
                createDirectTravelRoute(train);
            } else {
                createRouteWithTrainChange(train, potentialConnectionTrains);
            }   
        }
        setArrivalTrack(train.getExpectedArrivalTrack());
    }

    //No changing train is required | Travel ends in salzburg
    private void createDirectTravelRoute(Train train) {

        //randomly assign, if the passangers travel stop in salzburg
        //if not, then they travel with that particular train to Valhalla
        Random rd = new Random();
        Double rdValue = (double) rd.nextInt(100);

        if (rdValue <= 50) {
            this.destinationName = "Salzburg Hauptbahnhof";
            this.travelRouteAttribute = 0;
            Statistics.getInstance().incrementRoutesEndingInSalzburg();
        } else {
            //Where exactly does not matter to us
            this.destinationName = "Valhalla";
            this.travelRouteAttribute = 1; 
            Statistics.getInstance().incrementDirectTravelRoutes();
        }
        collectTicketPayment();
    }

    private void collectTicketPayment() {
        Random rd = new Random();
        Double rdPrice = (double) rd.nextInt(120);
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
    private void createRouteWithTrainChange(Train train, ArrayList<Train> potentialConnections) {

        if (potentialConnections.isEmpty()) {
            throw new Error("Potential Connecting Trains is null! Not sure how you even made it this far.");
        }

        this.connectingTrain = potentialConnections.get(0);
        this.travelRouteAttribute = 2;
        Statistics.getInstance().incementRoutesWithTrainChange();
        this.targetTrack = connectingTrain.getExpectedArrivalTrack();
        subscribeToConnectingTrain(connectingTrain);
        collectTicketPayment();
    }

    private void subscribeToConnectingTrain(Train train) {
        train.addSubscriber(this);
    }

    public void updateTargetTrack(int trackNo) {
        this.targetTrack = trackNo;
    }

    public int getTravelDelay() {
        return this.actualTravelTime - this.expectedTravelTime;
    }

    //Getters, Setters...
    public void setArrivalTrack(int trackNo) { this.arrivalTrack = trackNo; }
    public void setOnCorrectTrack(boolean bool) { this.onCorrectTrack = bool; }
    public void increaseHappiness(int amount) { this.happiness += amount; }
    public void decreaseHappiness(int amount) { this.happiness -= amount; }
    public void changeArrivalTrack(int trackNo) { this.arrivalTrack = trackNo; }
    public void increaseTravelTime(int time) { this.actualTravelTime += time; }
    public int getHappiness() { return this.happiness;}
    public int getArrivalTrack() { return this.arrivalTrack; }
    public String getDestinationID() { return this.destinationName; }
    public int getExpectedTravelTime() { return this.expectedTravelTime;}
    public int getActualTravelTime() { return this.actualTravelTime; }
    public double getTicketPrice() { return this.ticketPrice; }
    public int getTravelRoutAttribute() { return this.travelRouteAttribute; }
    public boolean isOnCorrectTrack() { return this.onCorrectTrack; }
    public Train getConnectingTrain() { return this.connectingTrain; }
    public void cancelTrain() { this.trainIsCanceled = true; }
    public boolean isTrainCanceled() { return this.trainIsCanceled; }
    public void trainMissed() { this.trainMissed = true; }
    public boolean isTrainMissed() { return this.trainMissed; }

}
