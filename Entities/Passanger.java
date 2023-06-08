package Entities;
import java.util.HashSet;

import desmoj.core.simulator.*;

public class Passanger extends Entity{

    //100 as base happiness
    private int happiness = 100;
    private boolean onCorrectTrack = false;

    private int arrivalTrack;
    private int destinationID;
    private int expectedTravelTime;
    private int actualTravelTime;
    private int ticketPrice;
    
    public Passanger(Model owner, String name, boolean showInTrace, int destinationID, int expectedTravelTime, int ticketPrice) {
        super(owner, name, showInTrace);

        this.destinationID = destinationID;
        this.expectedTravelTime = expectedTravelTime;
        this.ticketPrice = ticketPrice;

        //we assume that every passanger travels AT LEAST the expected time.
        this.actualTravelTime = expectedTravelTime;

    }

    //Getters, Setters...
    public void setOnCorrectTrack(boolean bool) { this.onCorrectTrack = bool; }
    public void increaseHappiness(int amount) { this.happiness += amount; }
    public void changeArrivalTrack(int trackNo) { this.arrivalTrack = trackNo; }
    public void increaseTravelTime(int time) { this.actualTravelTime += time; }
    public int getHappiness() { return this.happiness;}
    public int getArrivalTrack() { return this.arrivalTrack; }
    public int getDestinationID() { return this.destinationID; }
    public int getExpectedTravelTime() { return this.expectedTravelTime;}
    public int getActualTravelTime() { return this.actualTravelTime; }
    public int getTicketPrice() { return this.ticketPrice; }
    public boolean isOnCorrectTrack() { return this.onCorrectTrack; }
}
