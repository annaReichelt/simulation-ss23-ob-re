package src.Policy;

//I wanted to do enums because it would be easier to change for the different policies but Im sure theres a better way

public enum Happiness {

     MINOR(10), 
     SEMIMINOR(20), 
     MID(30), 
     SEMIMAJOR(40), 
     MAJOR(50);

     private int value;
     private Happiness(int value) { this.value = value; }
     private int getValue() { return this.value; }
     public void setValue(int value) {this.value = value; }

}
