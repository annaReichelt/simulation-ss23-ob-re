package src.Entities;

import java.util.HashSet;
import desmoj.core.simulator.*;
import src.*;
import src.RouteData.*;

public class Train extends Entity{
    
    private HashSet<Passanger> passengersOnTrain;
    private Time expectedArrivalTime;
    private Time actualArrivalTime;
    private Time waitingTime;
    private int expectedArrivalTrack;
    private int actualArrivalTrack;

    //we may not need this
    private int amountOfPassangers;

    public Train(Model owner, String name, boolean showInTrace,  Time arrivalTime, int arrivalTrack, Time waitingTime) {
        super(owner, name, showInTrace);
        this.expectedArrivalTime = arrivalTime;
        this.expectedArrivalTrack = arrivalTrack;
        this.actualArrivalTrack = arrivalTrack;
        this.waitingTime = waitingTime;

        //We assume that we at least arrive at the expected time
        this.actualArrivalTime = arrivalTime;
    }

    /**
     * creates passangers with randomized attributes
     * @param amount
     */
    public void generateRandomizedPassangers(int amount) {
        //Todo: fill the train with passangers
    }

    /**
     * creates passangers with specific attributes
     */
    public void generateSpecificPassangers(int amount, int destinationID, int expectedTravelTime, int ticketPrice) {
        //Todo: fill the train with passangers
    }


    //gettters, setters...
    public Time getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public Time getActualArrivalTime() {
        return actualArrivalTime;
    }

    public Time getWaitingTime() {
        return waitingTime;
    }

    public int getExpectedArrivalTrack() {
        return expectedArrivalTrack;
    }

    public int getActualArrivalTrack() {
        return actualArrivalTrack;
    }

    public void setActualArrivalTrack(int actualArrivalTrack) {
        this.actualArrivalTrack = actualArrivalTrack;
    }

    public void addActualArrivalTime(Time actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime.add(actualArrivalTime);
    }
}
