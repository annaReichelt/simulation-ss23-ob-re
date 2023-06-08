package Entities;

import java.util.HashSet;
import desmoj.core.simulator.*;

public class Train extends Entity{
    
    private HashSet<Passanger> passengersOnTrain;
    private int expectedArrivalTime;
    private int actualArrivalTime;
    private int waitingTime;
    private int expectedArrivalTrack;
    private int actualArrivalTrack;
    private int trainID;

    //we may not need this
    private int amountOfPassangers;

    public Train(Model owner, String name, boolean showInTrace, int trainID, int arrivalTime, int arrivalTrack, int waitingTime) {
        super(owner, name, showInTrace);
        this.trainID = trainID;
        this.expectedArrivalTime = arrivalTime;
        this.expectedArrivalTrack = arrivalTrack;
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

}
