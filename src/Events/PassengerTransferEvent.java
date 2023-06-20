package src.Events;

import java.util.concurrent.TimeUnit;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
import src.Config;
import src.Logger;
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
    public void eventRoutine(Passanger traveler) {

        Logger.getInstance().log("Creating new transfere event for " + traveler.getName());
        // check if train is departured
        if (traveler.connectingTrain.isTrainDeparted()) {
            Logger.getInstance().log("Passanger " + traveler.getName() + " missed his train.");
            traveler.trainMissed();
            return;
        }

        //Passanger is not on right track
        if (traveler.targetTrack != traveler.getArrivalTrack()) {
            Logger.getInstance().log("Passanger " + traveler.getName() + " needs to change track from  " + traveler.getArrivalTrack() + " to " + traveler.targetTrack);
            traveler.setArrivalTrack(traveler.targetTrack);
            PassengerTransferEvent pte = new PassengerTransferEvent(model, traveler.getName() + "needs to change tracks", isCurrent());
            pte.schedule(traveler, new TimeSpan(4.0));

        } else {
            Train trainOnTrack = model.getTrackNo(traveler.getArrivalTrack()).getTrainOnTrack();
            Train futureTrain = traveler.getConnectingTrain();

            Logger.getInstance().log("Passanger " + traveler.getName() + " on correct track " + traveler.getArrivalTrack());

            // log train on track and future train
            //Logger.getInstance().log("Train on track: " + trainOnTrack + " Future train: " + futureTrain);

            //Passanger on correct track, but train is not
            if (trainOnTrack == null) {
                if (futureTrain.isTrainCancled()) {
                    traveler.cancelTrain();
                    Logger.getInstance().log("Passanger " + traveler.getName() + " canceled his train.");
                } else {
                    Logger.getInstance().log("Track: " + model.getTrackNo(traveler.getArrivalTrack()));
                    //TODO: Travel time increase
                    Logger.getInstance().log("Passanger " + traveler.getName() + " on correct track " + traveler.getArrivalTrack() + " but train isnt.");
                    traveler.setArrivalTrack(traveler.connectingTrain.getActualArrivalTrack());
                    PassengerTransferEvent pte = new PassengerTransferEvent(model, traveler.getName() + "needs to wait", isCurrent());

                    // check if Simulation time if after the arrival time of the train
                    if (futureTrain.getActualArrivalTime().toTimeInstant().getTimeAsDouble() <= model.presentTime().getTimeAsDouble()) {
                        // if so, schedule the event to be executed in 1 minutes
                        pte.schedule(traveler, new TimeSpan(1.0));
                    } else
                        // Schedule the event to be executed when the train arrives or 
                        pte.schedule(traveler, futureTrain.getActualArrivalTime().toTimeInstant());
                }
            }

            //Passanger on correct track, Train on correct track
            if (trainOnTrack == futureTrain) {

                //further time delays that come from the train will be added through the train itself
                futureTrain.addPassangerToTrain(traveler);
                Logger.getInstance().log("Passanger " + traveler.getName() + " transferred successfully.");
                //System.out.println("Passanger transferred successfully");
            }
        }
    }
}

