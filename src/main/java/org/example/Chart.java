package org.example;

public class Chart {
    private String Date;
    private double open;
    private double high;
    private double low;
    private double close;
    private int volume;
    public Chart(String date, double open, double high, double low, double close, int volume) {
        this.Date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }
    public String date() {
        return Date;
    }

    public double open() {
        return open;
    }
    public double high() {
        return high;
    }
    public double low() {
        return low;
    }
    public double close() {
        return close;
    }
    public int volume() {
        return volume;
    }
}
