package window.trades;

import javax.swing.*;
import java.awt.*;

/**
 * ennek a panel segitségével lehet zárni a megrendeléseket
 */
public class CloseOrder extends JPanel {
    private JButton Close;
    private trade Trade;
    private JLabel Symbol;
    private JLabel Profit;
    private Runnable update;
    public CloseOrder(Runnable update) {
        this.update = update;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(false);

        Symbol = new JLabel();
        Profit = new JLabel("Profit");
        Close = new JButton("Close");

        add(Symbol);
        add(Profit);
        add(Close);

        Close.addActionListener(e -> {
            if(Trade != null) {
                Trade.closeTrade();
                update.run();
                setVisible(false);
            }
        });
    }
    public void setTrade(trade Trade) {
        this.Trade = Trade;
        Symbol.setText(Trade.getQuantity()+" "+Trade.getSymbol());
    }
    public void setProfit(){
        if(Trade != null)
            Profit.setText("Profit: "+Trade.getProfit());
    }
}
