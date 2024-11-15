package org.example;

public class Quote {
    private double ask;
    private double bid;
    public Quote(double ask, double bid) {
        this.ask = ask;
        this.bid = bid;
    }
    public double Ask() {
        return ask;
    }
    public double Bid() {
        return bid;
    }
}
