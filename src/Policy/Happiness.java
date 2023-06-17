package src.Policy;

//I wanted to do enums because it would be easier to change for the different policies but Im sure theres a better way

public enum Happiness {

     SMALL_MALUS(10), 
     NORMAL_MALUS(25), 
     LARGE_MALUS(50),
     CANCELED_MALUS(100);

     private final int value;
     Happiness(int value) { this.value = value; }
     public int getValue() { return this.value; }
}