package src.Policy;

import java.util.HashSet;
import src.Entities.Passanger;

public class CustomerService {
    
    //singleton class
    private static CustomerService cs;

    private HashSet<Passanger> travelers = new HashSet<Passanger>();

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


    //0-15 min late small malus
    //16-30 min late mid malus
    //>30 min late large malus
    //Canceled = canceled
    public void runStatistics() {

        int travelDelay;

        for (Passanger p : travelers) {
            
            //Get delay 
            travelDelay = p.getTravelDelay();

            //apply happyness rules
            if (p.isTrainCanceled()) {
                p.decreaseHappiness(Happiness.CANCELED_MALUS.getValue());
                //TODO: Refund
            } else if (travelDelay <= 15) {
                p.decreaseHappiness(Happiness.SMALL_MALUS.getValue());
                //TODO: Refund
            } else if (travelDelay <= 30) {
                p.decreaseHappiness(Happiness.NORMAL_MALUS.getValue());
                //TODO: Refund
            } else {
                p.decreaseHappiness(Happiness.LARGE_MALUS.getValue());
                //TODO: Refund
            } 
        }
    }
}
