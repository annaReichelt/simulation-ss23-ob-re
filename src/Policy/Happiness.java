package src.Policy;

public enum Happiness {

     SMALL_MALUS(10), 
     NORMAL_MALUS(25), 
     LARGE_MALUS(50),
     CANCELED_MALUS(100); //negative happyness = canceled

     private final int value;
     Happiness(int value) { this.value = value; }
     public int getValue() { return this.value; }
}