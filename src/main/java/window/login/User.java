package window.login;

import com.google.gson.annotations.Expose;
import indicators.indicator;
import window.trades.trade;
import window.watchlist.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * felhaszáló minden egyes adatát és a beállitásokat tárolja
 */
public class User {
    @Expose
    private String name;
    @Expose
    private double balance;
    @Expose
    private String password;
    @Expose
    private List<trade> trades;
    @Expose
    private List<indicator> indicators;
    @Expose
    private List<Element> watchlist;

    /**
     * inicializálja az usert a megadott paraméterek alapján
     * @param name név
     * @param password jelszó
     * @param balance egyenleg
     */
    public User(String name,  String password, double balance) {
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public double getBalance() {
        return balance;
    }
    public List<trade> getTrades() {
        if(trades == null)
            trades = new ArrayList<>();
        return trades;
    }
    public List<indicator> getIndicators() {
        if(indicators == null)
            indicators = new ArrayList<>();
        return indicators;
    }
    public List<Element> getWatchlist() {
        if(watchlist == null)
            watchlist = new ArrayList<>();
        return watchlist;
    }
    public void setIndicators(List<indicator> indicators) {
        this.indicators = indicators;
    }
    public void setWatchlist(List<Element> watchlist) {
        this.watchlist = watchlist;
    }
    public void addProfit(double profit) {
        balance += profit;
    }
    public void addTrade(trade trade) {
        trades.add(trade);
    }
}
