package src.Policy;

public enum Delays {

     NO_DELAY(10), 
     SLIGHT_DELAY(15), 
     NORMAL_DELAY(30),
     LARGE_DELAY(45); //negative happyness = canceled

     private final int value;
     Delays(int value) { this.value = value; }
     public int getValue() { return this.value; }
}