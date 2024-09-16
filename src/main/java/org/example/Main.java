package org.example;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HistoricalData hd = new HistoricalData();
        System.out.println(hd.GetData("AAPL", "TIME_SERIES_INTRADAY", "1min"));

    }
}