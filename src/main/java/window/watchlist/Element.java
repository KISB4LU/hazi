package window.watchlist;

import org.example.HistoricalData;
import org.example.Quote;

import java.awt.*;
import java.io.IOException;

public class Element {
    private HistoricalData hd;
    private String symbol;
    private double ask;
    private double bid;
    private Color  askColor;
    private Color  bidColor;
    private  Quote quote;
    public Element(String symbol) {
        this.symbol = symbol;
        askColor = Color.GREEN;
        bidColor = Color.GREEN;
        hd = new HistoricalData();
        try {
            quote = hd.GetQuote(symbol);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ask = quote.Ask();
        bid = quote.Bid();
    };
    public void update(){
        try {
            quote = hd.GetQuote(symbol);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(bid < quote.Bid())
            bidColor = Color.GREEN;
        else if(bid > quote.Bid())
            bidColor = Color.RED;
        else
            bidColor = Color.BLACK;

        if(ask < quote.Ask())
            askColor = Color.GREEN;
        else if(ask > quote.Ask())
            askColor = Color.RED;
        else
            askColor = Color.BLACK;
        bid = quote.Bid();
        ask = quote.Ask();
    }
    public String getSymbol() {
        return symbol;
    }
    public double getAsk() {
        return ask;
    }
    public double getBid() {
        return bid;
    }
    public Color getAskColor() {
        return askColor;
    }
    public Color getBidColor() {
        return bidColor;
    }
}
