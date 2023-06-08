package src.Events;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class PassengerTransfereEvent extends Event{

    public PassengerTransfereEvent(Model arg0, String arg1, boolean arg2) {
        super(arg0, arg1, arg2);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void eventRoutine(Entity arg0) throws SuspendExecution {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eventRoutine'");
    }
    
}
