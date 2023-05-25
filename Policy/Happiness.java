package Policy;

import javax.swing.event.AncestorEvent;

//I wanted to do enums because it would be easier to change for the different policies but Im sure theres a better way

public enum Happiness {

     MINOR(10), 
     SEMIMINOR(20), 
     MID(30), 
     SEMIMAJOR(40), 
     MAJOR(50);

     private int amount;
     private Happiness(int amount) { this.amount = amount; }
     private int getAmount() { return this.amount; }
     public void setAmount(int amount) {this.amount = amount; }

}
