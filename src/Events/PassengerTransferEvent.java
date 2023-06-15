package src.Events;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;
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
        
        
        //Is passanger on the right track?


    }
    
}
