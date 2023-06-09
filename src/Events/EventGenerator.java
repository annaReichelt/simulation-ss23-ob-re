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
        if (sd == null) {
            return;
        }
        Train train = new Train(trainStation, sd.getTripID(), true, sd.getArrivalTime(), sd.getTrackNumber(), new Time(0, 0, (int)(trainStation.getDelayTime())));
        int addInfo = sd.getAddInfo();
        if (addInfo != 1) {
            //TODO: add passangers
        }

        TrainArrivalEvent tae = new TrainArrivalEvent(trainStation, sd.getStationName(), true);
        tae.schedule(train, sd.getDepartureTime().toTimeInstant());
        

    }
}
