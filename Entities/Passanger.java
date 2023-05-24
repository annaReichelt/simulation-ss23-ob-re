package Entities;
import java.util.HashSet;

public class Passanger {

    //100 as base happiness
    private int happiness = 100;
    private boolean onCorrectTrack = false;

    private int arrivalTrack;
    private int destinationID;
    private int expectedTravelTime;
    private int actualTravelTime;
    private int ticketPrice;
    
    public Passanger(int destinationID, int expectedTravelTime, int ticketPrice) {

        this.destinationID = destinationID;
        this.expectedTravelTime = expectedTravelTime;
        this.ticketPrice = ticketPrice;

        //we assume that every passanger travels AT LEAST the expected time.
        this.actualTravelTime = expectedTravelTime;

    }
}
