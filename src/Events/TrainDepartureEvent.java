package src.Events;

import desmoj.core.simulator.*;
import src.*;
import src.Entities.*;

public class TrainDepartureEvent extends Event<Train>{
    
    private TrainStation model;

    // Konstruktor
    // Par 1: Modellzugehoerigkeit
    // Par 2: Name des Ereignisses
    // Par 3: show in trace?
    public TrainDepartureEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);

        model = (TrainStation) owner;
    }

    public void eventRoutine(Train train) {

        //setting track free actually works
        Track usedTrack = model.getTrackNo(train.getActualArrivalTrack()); 
        usedTrack.setFree(true);
        usedTrack.setTrainOnTrack(null);
        train.setTrainDeparted();
        Logger.getInstance().log("Train " + train.getName() + "was removed from station. TrackNo" + usedTrack.getTrackNo() + "is free: " + usedTrack.isFree());

        if(!model.trainQueue.isEmpty()) {
            Train nextTrain = model.trainQueue.first();
            model.trainQueue.remove(nextTrain);
            nextTrain.setActualArrivalTrack(train.getActualArrivalTrack());
            TrainArrivalEvent newEvent = new TrainArrivalEvent(model, "Zugeinfahrt nach Warten " + nextTrain.getName(), true);
            newEvent.schedule(nextTrain, new TimeSpan(5.0)); //5 minutes for switching tracks
            model.trainsToCome.remove(train);
        }
        
    }
}