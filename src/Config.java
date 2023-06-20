package src;

public enum Config {

    //Config file for starting inputs to avoid having to touch any code when running different experiment parameters
    NUM_TRACKS(6),
    MIN_TRANSFERE(5),
    TRAIN_TRACK_CHANGE_TIME(0),
    TRACE_PASSANGERS(0), //0 = false, 1 == true
    TRACE_TRAINS(0),
    TRACE_TRACKS(0),
    MIN_CHANGE_TIME(10), //between 10 - 59 minutes
    SWITCH_TRACK_PENALTY(0),
    FROM_QUEUE_TO_TRACK_TIME(5),



    private final int value;
    Config(int value) { this.value = value; }
    public int getValue() { return value; }
    public boolean getBool() {
        if (value == 0) { return false; }
        else { return true;}
    }
}