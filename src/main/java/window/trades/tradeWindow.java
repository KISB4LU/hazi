package window.trades;

import org.example.HistoricalData;
import org.example.Quote;
import window.login.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

public class tradeWindow extends JPanel {
    private User user;
    private HistoricalData hd;
    private String symbol;
    private JLabel label;
    private JLabel error;
    private JFormattedTextField quantity;
    private JFormattedTextField sl;
    private JFormattedTextField tp;
    private JButton Buy;
    private JButton Sell;

    public tradeWindow(User user) {
        this.user = user;
        hd = new HistoricalData();
        label = new JLabel("");
        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setVisible(false);
        quantity = new JFormattedTextField(new DecimalFormat("####.##"));
        sl = new JFormattedTextField(new DecimalFormat("####.##"));
        tp = new JFormattedTextField(new DecimalFormat("####.##"));
        quantity.setColumns(10);
        sl.setColumns(5);
        tp.setColumns(5);
        JPanel sltp = new JPanel();
        sltp.add(sl);
        sltp.add(tp);

        Buy = new JButton("Buy");
        Buy.setBackground(Color.GREEN);
        Sell = new JButton("Sell");
        Sell.setBackground(Color.RED);
        JPanel buttons = new JPanel();
        buttons.add(Buy);
        buttons.add(Sell);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(label);
        add(quantity);
        add(sltp);
        add(buttons);
        add(error);
        setVisible(false);

        Buy.addActionListener(e -> {
            Quote q;
            try {
                q = hd.GetQuote(symbol);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            double qty = Double.parseDouble(quantity.getText());
            double StopLoss = Double.parseDouble(sl.getText());
            double TakeProfit = Double.parseDouble(tp.getText());
            LocalDate time = LocalDate.now();
            double price = q.Ask();
            if(this.user.getBalance() >= price*qty) {
                trade Trade;
                if(StopLoss < price && TakeProfit > price)
                    Trade = new trade(this.user, symbol, time, Side.BUY, qty, price, TakeProfit, StopLoss);
                else {
                    error.setText("invalid tp or sl");
                    error.setVisible(true);
                }
            } else{
                error.setText("Insufficient funds");
                error.setVisible(true);
            }
        });
        Sell.addActionListener(e -> {
            Quote q;
            try {
                q = hd.GetQuote(symbol);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            double qty = Double.parseDouble(quantity.getText());
            double StopLoss = Double.parseDouble(sl.getText());
            double TakeProfit = Double.parseDouble(tp.getText());
            LocalDate time = LocalDate.now();
            double price = q.Bid();
            if(this.user.getBalance() >= price*qty) {
                trade Trade;
                if(StopLoss > price && TakeProfit < price)
                    Trade = new trade(this.user, symbol, time, Side.SELL, qty, price, TakeProfit, StopLoss);
                else {
                    error.setText("invalid tp or sl");
                    error.setVisible(true);
                }

            } else{
                error.setText("Insufficient funds");
                error.setVisible(true);
            }
        });
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
        label.setText(symbol);
    }
    public void setUser(User user){
        this.user = user;
        System.out.println(user);
    }
}
