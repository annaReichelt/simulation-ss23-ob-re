package src.Events;

import desmoj.core.simulator.*;
import src.RouteData.*;
import src.*;
import src.Entities.*;
import java.util.*;

public class TrainGenerator extends ExternalEvent{
    
    private TrainStation trainStation;
    private StationGenerator sg;
    private HashMap<Time, Train> trainListNotGeneratePassangers;
    private HashMap<Time, Train> trainListGeneratePassangers;
    private TreeMap<Time, Train> trainListPickUpPassangers;


    public TrainGenerator(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        trainStation = (TrainStation) owner;
        sg = StationGenerator.getInstance();
    }

    public void eventRoutine() {
        generateTrainList();
        for(Train train : trainListGeneratePassangers.values()) {
            generateTrainArrivalEventWithPassangers(train);
        }
        for(Train train : trainListNotGeneratePassangers.values()) {
            generateTrainArrivalEvent(train);
        }
    }

    private void generateTrainArrivalEventWithPassangers(Train train) {

        for (int i = 0; i < 10; i++) {
            createPassanger("Passanger #" + i, train);
        }

        TrainArrivalEvent tae = new TrainArrivalEvent(trainStation, "Zugeinfahrt " + train.getName(), true);
        tae.schedule(train, train.addToArrivalTime(trainStation.getDelayTime()).toTimeInstant());
        trainStation.trainsToCome.add(train);
    }    

    private void generateTrainArrivalEvent(Train train) {
        TrainArrivalEvent tae = new TrainArrivalEvent(trainStation, "Zugeinfahrt " + train.getName(), true);
        tae.schedule(train, train.addToArrivalTime(trainStation.getDelayTime()).toTimeInstant());
        trainStation.trainsToCome.add(train);
    }

    private void generateTrainList() {
        trainListNotGeneratePassangers = new HashMap<>();
        trainListGeneratePassangers = new HashMap<>();
        trainListPickUpPassangers = new TreeMap<>();
        while(!sg.isDataEmpty()){
            StationData sd = sg.getNextStationData();
            trainStation.totalTrains ++;
            Time arrivalTime = sd.getArrivalTime();
            Time departureTime = sd.getDepartureTime();
            Week schedule = sd.getWeek();
            int day;
            for(day = 0; day < 7; day++) {
                if(schedule.isActive(day)) {
                    Time localArrivalTime = arrivalTime.add(new Time(day * 24, 0, 0));
                    Time localDepartureTime = departureTime.add(new Time(day * 24, 0, 0));
                    //wrap around after 1 week
                    if(localArrivalTime.compareTo(new Time(7 * 24, 0, 0)) >= 0) {
                        localArrivalTime = localArrivalTime.add(new Time(-7 * 24, 0, 0));
                    }
                    if(localDepartureTime.compareTo(new Time(7 * 24, 0, 0)) >= 0) {
                        localDepartureTime = localDepartureTime.add(new Time(-7 * 24, 0, 0));
                    }
                    Train localTrain = generateTrain(sd, localArrivalTime, localDepartureTime);
                    switch (sd.getAddInfo()) { // 0 = no info, 1 = startingStation, 2 = endingStation
                        case 1:
                            trainListPickUpPassangers.put(localArrivalTime, localTrain);
                            trainListNotGeneratePassangers.put(localArrivalTime, localTrain);
                            break;
                        case 2:
                            trainListGeneratePassangers.put(localArrivalTime, localTrain);
                            break;
                        default:
                            trainListPickUpPassangers.put(localArrivalTime, localTrain);
                            trainListGeneratePassangers.put(localArrivalTime, localTrain);
                            break;
                    }
                }
            }
        }
    }

    private Train generateTrain(StationData sd, Time arrivalTime, Time departureTime) {
        Train train = new Train(trainStation, sd.getTripID(), true, arrivalTime, sd.getTrackNumber(), departureTime);
        return train;
    }

    private ArrayList<Train> getNextTrains(Time from, Time to) {
        ArrayList<Train> trains = new ArrayList<Train>();
        for(Time time : trainListPickUpPassangers.keySet()) {
            if(time.compareTo(from) >= 0 && time.compareTo(to) <= 0) {
                trains.add(trainListPickUpPassangers.get(time));
            }
        }
        return trains;
    }

    /**
     * Creates passangers for a train. The remaining data for the class vars is then pulled from RouteData
     * @param name of the Passanger
     * @param train the Passanger will be traveling in
     * @return passanger object
     */
    private Passanger createPassanger(String name, Train train) {
        Passanger passanger = new Passanger(trainStation, name, true);
        train.addPassangerToTrain(passanger);
        passanger.createTravelRoute(train, getNextTrains(train.getExpectedArrivalTime().add(new Time(15)), train.getExpectedArrivalTime().add(new Time(50))));

        return passanger;
    }
}
