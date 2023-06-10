package src.Entities;
import java.util.HashSet;
import java.util.Random;

import desmoj.core.simulator.*;
import src.TrainStation;

public class Passanger extends Entity {

    //100 as base happiness
    private int happiness = 100;
    private boolean onCorrectTrack = false;

    private int arrivalTrack;
    private String destinationName;
    private int expectedTravelTime;
    private int actualTravelTime;
    private double ticketPrice;

    TrainStation StationModel;
    
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
            this.destinationName = "Beyond"; //Where exactly does not matter
        }
        collectTicketPayment();
    }

    private void collectTicketPayment() {
        Random rd = new Random();
        Double rdPrice = rd.nextDouble(15.00, 120.00);
        this.ticketPrice = rdPrice;

        StationModel.addRevenue(rdPrice);
    }


    //Changing trains is required && Travel does NOT end in salzburg
    private void createRouteWithTrainChange(Train train) {

        //Take a train that stops in salzburg
        //look for trains leaving salzburg approx. 10 - 60 minutes after arrival of the original train -> Do we have such a mechanic yet?
        //Take one of those connecting trains at random
        //Assign that to the travel route of this passanger
        //Subscribe passanger to the connecting train to get updates on track changes
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
