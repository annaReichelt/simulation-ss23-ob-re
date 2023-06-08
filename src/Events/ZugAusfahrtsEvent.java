package src.Events;

import desmoj.core.simulator.*;
import src.*;
import src.Entities.*;

public class ZugAusfahrtsEvent extends Event<Train>{
    
    private TrainStation model;

    // Konstruktor
    // Par 1: Modellzugehoerigkeit
    // Par 2: Name des Ereignisses
    // Par 3: show in trace?
    public ZugAusfahrtsEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);

        model = (TrainStation) owner;
    }

    public void eventRoutine(Train train) {
        model.getTrackNo(train.getActualArrivalTrack()).setFree(true);
        if(!model.trainQueue.isEmpty()) {
            Train nextTrain = model.trainQueue.first();
            model.trainQueue.remove(nextTrain);
            ZugEinfahrtsEvent newEvent = new ZugEinfahrtsEvent(model, "Zugeinfahrt", isCurrent());
            newEvent.schedule(nextTrain, new TimeSpan(5.0)); //5 minutes for switching tracks
        }
        
    }
}