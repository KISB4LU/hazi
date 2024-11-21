package window.trades;

import org.example.HistoricalData;
import org.example.Quote;
import window.login.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

enum Side{
    BUY, SELL
}

public class trade {
    private User user;
    private String symbol;
    private LocalDate tradeDate;
    private Side side;
    private boolean Open;
    private double quantity;
    private double price;
    private double Profit;
    private HistoricalData hd;
    private double tp;
    private double sl;
    public trade(User user, String symbol, LocalDate tradeDate, Side side, double quantity, double price) {
        user.addTrade(this);
        hd = new HistoricalData();
        Open = true;
        this.user = user;
        this.symbol = symbol;
        this.tradeDate = tradeDate;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        Profit = 0;
        sl =  0;
        tp = 0;
    }
    public trade(User user, String symbol, LocalDate tradeDate, Side side, double quantity, double price, double tp, double sl) {
        user.addTrade(this);
        hd = new HistoricalData();
        Open = true;
        this.user = user;
        this.symbol = symbol;
        this.tradeDate = tradeDate;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.tp = tp;
        this.sl = sl;
    }
    public void calculateProfit(){
        if(!Open)
            return;
        Quote q;
        try {
            q = hd.GetQuote(symbol);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        switch(side){
            case BUY:
                Profit = (q.Ask()-price)*quantity;
                if(sl > q.Ask()){
                    user.addProfit(Profit);
                    Open = false;
                } else if(tp != 0 && tp < q.Ask()) {
                    user.addProfit(Profit);
                    Open = false;
                }
                break;
            case SELL:
                Profit = (price-q.Bid())*quantity;
                if(sl  != 0  && sl < q.Bid()){
                    user.addProfit(Profit);
                    Open = false;
                } else if(tp > q.Bid()) {
                    user.addProfit(Profit);
                    Open = false;
                }
                break;
        }
    }
    public void closeTrade(){
        if(Open){
            calculateProfit();
            user.addProfit(Profit);
            Open = false;
        }
    }
    public LocalDate getTradeDate() {
        return tradeDate;
    }
    public Side getSide() {
        return side;
    }
    public double getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public double getProfit() {
        return Profit;
    }
    public boolean isOpen() {
        return Open;
    }
    public String getSymbol() {
        return symbol;
    }
}
