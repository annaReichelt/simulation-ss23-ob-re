package src.Policy;

import java.util.HashSet;

import src.TrainStation;
import src.Entities.Passanger;

public class CustomerService {
    
    //singleton class
    private static CustomerService cs;

    private HashSet<Passanger> travelers = new HashSet<Passanger>();
    private TrainStation model;

    private CustomerService() {}

    public static CustomerService GetInstance() {

        if (cs == null) {
            cs = new CustomerService();
        }

        return cs;
    }

    /**
     * Add the traveler to the class HashSet to iterate for statistics
     * @param traveler
     */
    public void addTraveler(Passanger traveler) {
        this.travelers.add(traveler);
    }

    public void addModelReference(TrainStation model) {
        this.model = model;
    }

    //0-15 min late small malus
    //16-30 min late mid malus
    //>30 min late large malus
    //Canceled = canceled
    public void runStatistics() {

        int travelDelay;
        int totalDelay = 0;
        double ticketPrice;

        for (Passanger p : travelers) {
            
            //Get delay 
            travelDelay = p.getTravelDelay();
            totalDelay += travelDelay;
            ticketPrice = p.getTicketPrice();

            //apply happyness rules and refunds
            if (p.isTrainCanceled()) {
                p.decreaseHappiness(Happiness.CANCELED_MALUS.getValue());
                this.model.addLossesFromRefunds(Refund.CANCELED_REFUND.getValue() * ticketPrice);
            } else if (travelDelay <= 15) {
                p.decreaseHappiness(Happiness.SMALL_MALUS.getValue());
                this.model.addLossesFromRefunds(Refund.SMALL_REFUND.getValue() * ticketPrice);
            } else if (travelDelay <= 30) {
                p.decreaseHappiness(Happiness.NORMAL_MALUS.getValue());
                this.model.addLossesFromRefunds(Refund.NORMAL_REFUND.getValue() * ticketPrice);
            } else {
                p.decreaseHappiness(Happiness.LARGE_MALUS.getValue());
                this.model.addLossesFromRefunds(Refund.LARGE_REFUND.getValue() * ticketPrice);
            } 
        }
    }
}
