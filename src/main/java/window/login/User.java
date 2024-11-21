package window.login;

import window.trades.trade;

import java.util.List;

public class User {
    private String name;
    private double balance;
    private String password;
    private List<trade> trades;
    public User(String name,  String password, double balance) {
        this.name = name;
        this.balance = balance;
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
        return trades;
    }
    public void addProfit(double profit) {
        balance += profit;
    }
    public void addTrade(trade trade) {
        trades.add(trade);
    }
}
