package src.Events;

import desmoj.core.simulator.*;
import src.RouteData.*;
import src.*;
import src.Entities.Passanger;
import src.Entities.Train;

public class EventGenerator extends ExternalEvent{
    
    private TrainStation trainStation;

    public EventGenerator(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        trainStation = (TrainStation) owner;
    }

    public void eventRoutine() {
        StationGenerator sg = StationGenerator.getInstance();
        StationData sd;
        boolean drive = false;
        Time arrivalTime, departureTime;

        trainStation.totalTrains ++;
        int day;

        do {
            sd = sg.getNextStationData();
            if (sd == null) {
                return;
            }
            arrivalTime = sd.getArrivalTime();
            departureTime = sd.getDepartureTime();
            Week schedule = sd.getWeek();
            Time simulationTime = new Time(trainStation.presentTime().getTimeAsDouble());
            int simHour = simulationTime.getHour();
            day = (int)Math.floor(simHour / 24);

            //TODO: FIX THIS MESS WITH SIMTIME BEHIND BUT ARRIVALTIME CAN BE > 24 

            /*if(arrivalTime.getHour() >= 24) {
                day--;
            }*/

            if(simHour % 24 > arrivalTime.getHour()) {
                // simulationTime is "yesterday" if the train arrivelTime is "today"
                day++;
            }
            
            

            System.out.println(day + " " + simHour % 24 + " " + arrivalTime.getHour());

            arrivalTime = arrivalTime.add(new Time(day * 24, 0, 0));
            departureTime = departureTime.add(new Time(day * 24, 0, 0));

            drive = schedule.isActive(day);
        }
        while(!drive); 

        //System.out.println(trainStation.presentTime().getTimeAsDouble() + " " + day + " " + arrivalTime + " " + sd);

        Train train = new Train(trainStation, sd.getTripID(), true, arrivalTime, sd.getTrackNumber(), departureTime);
        int addInfo = sd.getAddInfo();

        if (addInfo != 1) {
            //TODO generate passangers

        }

        TrainArrivalEvent tae = new TrainArrivalEvent(trainStation, "Zugeinfahrt " + train.getName(), true);
        tae.schedule(train, train.addToArrivalTime(trainStation.getDelayTime()).toTimeInstant());
        
        // generate new EventGenerator
        //TODO: Not a todo but suggestion: Remove this and throw the entire thing into a loop with i = amount of trains in the next 7x 24 hours?
        EventGenerator eg = new EventGenerator(trainStation, "EventGenerator", true);
        eg.schedule(arrivalTime.toTimeInstant());
    }

    /**
     * Creates passangers for a train. The remaining data for the class vars is then pulled from RouteData
     * @param name of the Passanger
     * @param train the Passanger will be traveling in
     * @return passanger object
     */
    private Passanger createPassanger(String name, Train train) {
        Passanger passanger = new Passanger(trainStation, name, true);
        passanger.createTravelRoute(train);

        return passanger;
    }
}
