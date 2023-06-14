package src;

public enum Config {

    //Config file for starting inputs to avoid having to touch any code when running different experiment parameters
    NUM_TRACKS(6),
    MIN_CHANGE_TIME(10); //between 10 - 59 minutes



    private final int value;
    Config(int value) { this.value = value; }
    public int getValue() { return value; }
}