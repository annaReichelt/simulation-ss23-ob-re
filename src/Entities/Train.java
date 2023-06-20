package src.Entities;

import java.util.ArrayList;
import java.util.HashSet;
import desmoj.core.simulator.*;
import src.*;
import src.RouteData.*;

public class Train extends Entity{
    
    private HashSet<Passanger> passengersOnTrain = new HashSet<Passanger>();
    private ArrayList<Passanger> futureTravelers = new ArrayList<Passanger>();
    private Time expectedArrivalTime;
    private Time actualArrivalTime;
    private Time expectedDepartureTime;
    private Time actualDepartureTime;
    private int expectedArrivalTrack;
    private int actualArrivalTrack;
    private boolean isCancled = false;

    TrainStation station;

    //TODO: Simplify contructor - Perhaps by using the TrainID which links back to RouteData where all of the blanks will be filled in
    public Train(Model owner, String name, boolean showInTrace,  Time arrivalTime, int arrivalTrack, Time expectedDepartureTime) {
        super(owner, name, Config.TRACE_TRAINS.getBool());
        this.station = (TrainStation) owner;
        this.expectedArrivalTime = arrivalTime;
        this.expectedArrivalTrack = arrivalTrack;
        this.actualArrivalTrack = arrivalTrack;
        this.expectedDepartureTime = expectedDepartureTime;
        this.actualDepartureTime = expectedDepartureTime;

        //We assume that we at least arrive at the expected time
        this.actualArrivalTime = arrivalTime;
    }

    public void addSubscriber(Passanger traveler) {
        futureTravelers.add(traveler);
    }

    private void announceTrackChange() {

        //inform future travelers where they need to go
        for (Passanger traveler : futureTravelers) {
            traveler.updateTargetTrack(this.actualArrivalTrack);
        }

        //inform current travelers where they will end up
        for (Passanger traveler : passengersOnTrain) {
            traveler.changeArrivalTrack(this.actualArrivalTrack);
        }
    }

    //gettters, setters...
    public HashSet<Passanger> getPassangersOnTrain() {
        return this.passengersOnTrain;
    }

    public boolean removePassanger(Passanger passanger) {
        return this.passengersOnTrain.remove(passanger);
    }

    public Time getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public Time getActualArrivalTime() {
        return actualArrivalTime;
    }

    public Time getExpectedDepartureTime() {
        return expectedDepartureTime;
    }

    public Time getActualDepartureTime() {
        return actualDepartureTime;
    }

    public int getExpectedArrivalTrack() {
        return expectedArrivalTrack;
    }

    public int getActualArrivalTrack() {
        return actualArrivalTrack;
    }

    public void setActualArrivalTrack(int actualArrivalTrack) {
        this.actualArrivalTrack = actualArrivalTrack;
        announceTrackChange();
    }

    public Time addToArrivalTime(Time incTime) {
        actualArrivalTime = actualArrivalTime.add(incTime);
        if(actualArrivalTime.compareTo(actualDepartureTime) > 0) {
            actualDepartureTime = actualArrivalTime;
        }
        return this.actualArrivalTime;
    }

    public Time addToArrivalTime(double minutes) {
        return addToArrivalTime(new Time(minutes));
    }

    public Time addToDepartureTime(Time incTime) {
        actualDepartureTime = actualDepartureTime.add(incTime);
        return this.actualDepartureTime;
    }

    public Time addToDepartureTime(double minutes) {
        return addToDepartureTime(new Time(minutes));
    }

    public void addPassangerToTrain(Passanger passanger) {
        this.passengersOnTrain.add(passanger);
    }

    public void cancelTrain() {
        this.isCancled = true;
    }

    public boolean isTrainCancled() {
        return this.isCancled;
    }
}
