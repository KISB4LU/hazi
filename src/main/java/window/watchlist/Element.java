package window.watchlist;

import com.google.gson.annotations.Expose;
import org.example.HistoricalData;
import org.example.Quote;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * watclist lista elem
 */
public class Element {
    private HistoricalData hd;
    @Expose
    private String symbol;
    @Expose
    private double ask;
    @Expose
    private double bid;

    private Color  askColor;
    private Color  bidColor;
    private  Quote quote;
    private JButton delete;
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
        delete = new JButton("X");
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
    public JButton getDelete() {
        return delete;
    }
    public void setHd(HistoricalData hd) {
        this.hd = hd;
    }

    @Override
    public String toString() {
        return symbol + ": ask: "+ask+", bid: "+bid;
    }
}
