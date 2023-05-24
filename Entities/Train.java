package Entities;

import java.util.HashSet;

public class Train {
    
    private HashSet<Passanger> passengersOnTrain;
    private int expectedArrivalTime;
    private int actualArrivalTime;
    private int waitingTime;
    private int expectedArrivalTrack;
    private int actualArrivalTrack;
    private int trainID;

    //we may not need this
    private int amountOfPassangers;

    public Train(int trainID, int arrivalTime, int arrivalTrack, int waitingTime) {
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
