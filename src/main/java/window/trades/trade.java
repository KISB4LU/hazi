package window.trades;

import com.google.gson.annotations.Expose;
import org.example.HistoricalData;
import org.example.Quote;
import window.login.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

enum Side{
    BUY, SELL
}

/**
 * trade minden fontos elemét tartalmazza
 */
public class trade {
    private User user;
    @Expose
    private String symbol;
    @Expose
    private LocalDate tradeDate;
    @Expose
    private Side side;
    @Expose
    private boolean Open;
    @Expose
    private double quantity;
    @Expose
    private double price;
    @Expose
    private double Profit;
    @Expose
    private HistoricalData hd;
    @Expose
    private double tp;
    @Expose
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

    /**
     * inicializálja a tradet ami kezeli a poziciót
     * @param user felhasználó
     * @param symbol szimbolum
     * @param tradeDate trade létrejöttének napja
     * @param side eladási vagy vételi oldal
     * @param quantity darabszám
     * @param price ár
     * @param tp TakeProfit
     * @param sl StopLoss
     */
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

    /**
     * kiszámolja az aktuális profitot
     */
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
                Profit = (q.Bid()-price)*quantity;
                if(sl > q.Bid()){
                    user.addProfit(Profit);
                    Open = false;
                } else if(tp != 0 && tp < q.Bid()) {
                    user.addProfit(Profit);
                    Open = false;
                }
                break;
            case SELL:
                Profit = (price-q.Ask())*quantity;
                if(sl  != 0  && sl < q.Ask()){
                    user.addProfit(Profit);
                    Open = false;
                } else if(tp > q.Ask()) {
                    user.addProfit(Profit);
                    Open = false;
                }
                break;
        }
    }

    /**
     * ha nyitott a pozició lezárja
     */
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

        return Math.round(Profit * 100)/100.0;
    }
    public boolean isOpen() {
        return Open;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString(){
        return symbol+", price: "+price+", Profit: "+Profit +",tp: "+tp+", sl: "+sl;
    }
}
