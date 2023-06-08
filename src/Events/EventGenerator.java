package src.Events;

import desmoj.core.simulator.*;
import src.RouteData.*;
import src.*;
import src.Entities.Train;

public class EventGenerator extends ExternalEvent{
    
    private TrainStation trainStation;

    public EventGenerator(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        trainStation = (TrainStation) owner;
    }

    public void eventRoutine() {
        StationGenerator sg = StationGenerator.getInstance();
        StationData sd = sg.getNextStationData();
        Train train = new Train(trainStation, sd.getTripID(), true, sd.getArrivalTime(), sd.getTrackNumber(), new Time(0, 0, (int)(trainStation.getDelayTime())));
        if(sd == null)
            return;
        int addInfo = sd.getAddInfo();
        if (addInfo != 1) {
            //TODO: add passangers
        }   
        

    }
}
