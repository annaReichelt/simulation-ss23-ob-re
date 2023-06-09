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
            TrainDepartureEvent newEvent = new TrainDepartureEvent(model, "Zugausfahrt " + train.getName(), true);
            newEvent.schedule(train, train.addToDepartureTime(model.getDelayTime()).toTimeInstant());
        }
        else {
            int newTrackNo = model.getFreeTrackNo();
            if(newTrackNo== -1) {
                // no free track available
                model.trainQueue.insert(train);
            }
            else {
                train.setActualArrivalTrack(newTrackNo);
                TrainArrivalEvent newEvent = new TrainArrivalEvent(model, getName() + " new Track", true);
                newEvent.schedule(train, new TimeSpan(0.0)); //for now switching tracks is free
            }
        }

    }
    
}
