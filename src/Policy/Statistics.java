package src.Policy;

public class Statistics {
    
    //Singleton stats collector
    public int passangers = 0;
    public int trains = 0;
    public int directRoutes = 0;
    public int endingInSalzburgRoutes = 0;
    public int routesWithTrainChange = 0;
    public int missedTrains = 0; 

    private static Statistics stats = null;

    private Statistics() { }

    public static Statistics getInstance() {
        if (stats == null) {
            stats = new Statistics();
        }
        return stats;
    }

    public void incrementPassanger() {
        this.passangers += 1;
    }

    public void incrementTrains() {
        this.trains += 1;
    }

    public void incrementDirectTravelRoutes() {
        this.directRoutes += 1;
    }

    public void incrementRoutesEndingInSalzburg() {
        this.endingInSalzburgRoutes += 1;
    }

    public void incementRoutesWithTrainChange() {
        this.routesWithTrainChange += 1;
    }

    public void printStats() {
        System.out.println("Traveling Passangers: " + this.passangers);
        System.out.println("Trains instansiated: " + this.trains);
        System.out.println("Direct Travel Routes: " + this.directRoutes);
        System.out.println("Routes ending in Salzburg: " + this.endingInSalzburgRoutes);
        System.out.println("Routes with Train Change: " + this.routesWithTrainChange);
        System.out.println("Missed Trains: " + this.missedTrains);
    }

}
