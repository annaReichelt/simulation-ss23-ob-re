package src.Events;

import desmoj.core.simulator.*;
import src.RouteData.*;
import src.*;
import src.Entities.*;

public class TrainGenerator extends ExternalEvent{
    
    private TrainStation trainStation;
    private StationGenerator sg;

    public TrainGenerator(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        trainStation = (TrainStation) owner;
        sg = StationGenerator.getInstance();
    }

    public void eventRoutine() {
        while(!sg.isDataEmpty()){
            generateTrain();
        } 
    }

    private void generateTrainArrivalEvent(StationData sd, Time arrivalTime, Time departureTime) {
        Train train = new Train(trainStation, sd.getTripID(), true, arrivalTime, sd.getTrackNumber(), departureTime);


        int addInfo = sd.getAddInfo();
        if (addInfo != 1) {  
            for (int i = 0; i < 10; i++) {
                createPassanger("Passanger #" + i, train);
            }
            for (int i = 0; i < 10; i++) {
                Passanger traveler = new Passanger(trainStation, "Passanger #" + i, true);
                createPassanger("Passanger #" + i, train);
            }
            //TODO: add passangers
            for (int i = 0; i < 10; i++) {
                Passanger traveler = new Passanger(trainStation, "Passanger #" + i, true);
                createPassanger("Passanger #" + i, train);
            }
            for (int i = 0; i < 10; i++) {
                Passanger traveler = new Passanger(trainStation, "Passanger #" + i, true);
                createPassanger("Passanger #" + i, train);
            }
        }

        TrainArrivalEvent tae = new TrainArrivalEvent(trainStation, "Zugeinfahrt " + train.getName(), true);
        tae.schedule(train, train.addToArrivalTime(trainStation.getDelayTime()).toTimeInstant());
        trainStation.trainsToCome.add(train);
    }
    
    /* Generate the next valid StationData and generate the TrainArrivalEvent
     * @param shouldRunEmpty: if true, the method will not run over the last StationData, if false, it will restart the StationData
     * @return -1 if no StationData is left, 0 if the day is not finished, 1 if the day is finished, 2 if the StationData is unexpectedly empty
    */
    private void generateTrain() {
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
                if(sd.getAddInfo() != 1) {
                    //TODO
                }
                generateTrainArrivalEvent(sd, localArrivalTime, localDepartureTime);
            }
        }
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
        passanger.createTravelRoute(train);

        return passanger;
    }
}
