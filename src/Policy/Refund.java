package src.Policy;

public enum Refund {

    //factors for refunding the ticket prices
    SMALL_REFUND(0.0), 
    NORMAL_REFUND(0.25), 
    LARGE_REFUND(0.5),
    CANCELED_REFUND(1);

    private final double value;
    Refund(double value) { this.value = value; }
    public double getValue() { return this.value; }
}
