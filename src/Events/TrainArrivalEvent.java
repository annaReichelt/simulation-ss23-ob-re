package src.Events;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import src.*;
import src.Entities.*;

public class TrainArrivalEvent extends Event<Train>{

    private TrainStation model;

    // Konstruktor
    // Par 1: Modellzugehoerigkeit
    // Par 2: Name des Ereignisses
    // Par 3: show in trace?
    public TrainArrivalEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);

        model = (TrainStation) owner;
    }

    public void eventRoutine(Train train) {

        int trackNumber = train.getActualArrivalTrack();
        if(model.isTrackAvailable(trackNumber)) {

            Logger.getInstance().log("Train " + train.getName() + "target track is free. Setting " + train.getActualArrivalTrack() + " to unavailable.");
            model.getTrackNo(trackNumber).setFree(false);
            model.getTrackNo(trackNumber).setTrainOnTrack(train);

            //Passanger Stuff
            HashSet<Passanger> exitingPassangers = new HashSet<Passanger>();
            int travelType;

            //Assume: Train is instantiated and every passanger knows where to go
            for (Passanger traveler : train.getPassangersOnTrain()) {
                traveler.setArrivalTrack(trackNumber);
                travelType = traveler.getTravelRoutAttribute();

                if (travelType == 0) {
                    Logger.getInstance().log("Passanger " + traveler.getName() + "is leaving Train. Travel complete.");
                    exitingPassangers.add(traveler);

                } else if (travelType == 2) {
                    //train change, passanger exits train
                    exitingPassangers.add(traveler);
                    Logger.getInstance().log("Passanger " + traveler.getName() + "needs to change train.");
                    PassengerTransferEvent pTransferEvent = new PassengerTransferEvent(model, traveler.getName() + "is changing trains", true);
                    pTransferEvent.schedule(traveler, new TimeSpan(0.0, TimeUnit.MINUTES));
                
                } else if (travelType == 1) {
                    Logger.getInstance().log("Passanger " + traveler.getName() + "stays seated.");
                } else {
                    //travel type hasnt been set -> error
                    throw new Error("Travel Type hasn't been set correctly. Needs to be set to 0, 1 or 2 in Passanger.java");

                }
                //TODO: Travel time calculations for late arrivals depending on actual arrival time
            }

            //remove passangers from original train
            for (Passanger passanger : exitingPassangers) {
                Logger.getInstance().log("Passanger " + passanger.getName() + "removed from passangerlist of train " + train.getName());
                if (!train.removePassanger(passanger)) {
                    throw new Error("Passanger couldn't be removed from Train");
                }
            }

            //Start train departure event
            TrainDepartureEvent newEvent = new TrainDepartureEvent(model, "Zugausfahrt " + train.getName(), true);
            Logger.getInstance().log("creating new departure event for train " + train.getName());
            newEvent.schedule(train, train.addToDepartureTime(model.getDelayTime()).toTimeInstant());

        }
        else {
            int newTrackNo = model.getFreeTrackNo();
            if(newTrackNo == -1) {
                // no free track available
                model.trainQueue.insert(train);
                //TODO: Added time needs to be recorded
            }
            else {
                train.setActualArrivalTrack(newTrackNo);
                informPassangersChangedArrivalTrack(train, newTrackNo);
                train.announceTrackChange();
                TrainArrivalEvent newEvent = new TrainArrivalEvent(model, getName() + " new Track", true);

                //TODO: Time penalty for track change
                newEvent.schedule(train, new TimeSpan(0.0)); //for now switching tracks is free
            }
        }
    }

    //Informs the passangers already IN the train about the changed track
    private void informPassangersChangedArrivalTrack(Train train, int newTrack) {
        for (Passanger traveler : train.getPassangersOnTrain()) {
            traveler.setArrivalTrack(newTrack);
        }

        for (Passanger traveler : train.futureTravelers) {
            traveler.targetTrack = newTrack;
        }

    }
}
