package src.Events;

import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import src.Config;
import src.TrainStation;
import src.Entities.*;

public class PassengerTransferEvent extends Event<Passanger>{

    TrainStation model;

    public PassengerTransferEvent(Model model, String name, boolean showInTrace) {
        super(model, name, showInTrace);
        this.model = (TrainStation) model;
    }

    //Assume, only travelers who need to change trains go through this routine
    @Override
    public void eventRoutine(Passanger traveler) throws SuspendExecution {
        
        //Passanger is not on right track
        if (traveler.targetTrack != traveler.getArrivalTrack()) {
            //TODO: Double check if you made an oopsie with the track indexing
            

            traveler.setArrivalTrack(traveler.targetTrack);
            //TODO: Check if time is correct here
            traveler.schedule(new PassengerTransferEvent(model, this.getName(), isCurrent()), new TimeInstant(model.presentTime().getTimeAsDouble() + Config.MIN_TRANSFERE.getValue(), TimeUnit.MINUTES));
        }

        Train trainOnTrack = model.getTrackNo(traveler.getActualTravelTime()).getTrainOnTrack();
        Train futureTrain = traveler.getConnectingTrain();

        //Passanger on correct track, but train is not
        if (trainOnTrack == null) {
            
            if (futureTrain.isTrainCancled()) {
                //TODO: Unhappy, refunds
            
            } else {
                traveler.increaseTravelTime((int) Config.MIN_TRANSFERE.getValue());
                traveler.schedule(new PassengerTransferEvent(model, this.getName(), isCurrent()), new TimeInstant(model.presentTime().getTimeAsDouble() + Config.MIN_TRANSFERE.getValue(), TimeUnit.MINUTES));
            }
        }

        //Passanger on correct track, Train on correct track
        if (trainOnTrack == futureTrain) {

            //further time delays that come from the train will be added through the train itself
            futureTrain.addPassangerToTrain(traveler);
        }
    }
}
