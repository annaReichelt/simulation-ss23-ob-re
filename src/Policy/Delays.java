package src.Policy;

public enum Delays {

     NO_DELAY(10), 
     SLIGHT_DELAY(30), 
     NORMAL_DELAY(60),
     LARGE_DELAY(120); //negative happyness = canceled

     private final int value;
     Delays(int value) { this.value = value; }
     public int getValue() { return this.value; }
}