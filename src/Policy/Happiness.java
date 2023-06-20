package src.Policy;

public enum Happiness {

     SMALL_MALUS(5), 
     NORMAL_MALUS(10), 
     LARGE_MALUS(25),
     CANCELED_MALUS(50);

     private final int value;
     Happiness(int value) { this.value = value; }
     public int getValue() { return this.value; }
}