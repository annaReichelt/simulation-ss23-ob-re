package src.Events;

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
            model.getTrackNo(trackNumber).setFree(false);
            //TODO: passanger stuff
            TrainDepartureEvent newEvent = new TrainDepartureEvent(model, "Zugausfahrt", isCurrent());
            newEvent.schedule(train, new TimeSpan(train.getWaitingTime() + model.getDelayTime()));
        }
        else {
            train.setActualArrivalTrack(model.getFreeTrackNo());
            if(train.getActualArrivalTrack() == -1) {
                // no free track available
                model.trainQueue.insert(train);
            }
            else {
                TrainArrivalEvent newEvent = new TrainArrivalEvent(model, getName() + " new Track", isCurrent());
                newEvent.schedule(train, new TimeSpan(0.0)); //for now switching tracks is free
            }
        }

    }
    
}