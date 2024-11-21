package threads;


import window.trades.trade;
import window.trades.tradeTable;

import javax.swing.*;
import java.util.List;

public class tradeThread extends Thread {
    private List<trade> trades;
    private tradeTable model;
    public tradeThread(List<trade> trades, tradeTable model) {
        this.trades = trades;
        this.model = model;
    }
    @Override
    public void run() {
        while (true) {
            try {
                for(trade t : trades) {
                    t.calculateProfit();
                }
                SwingUtilities.invokeLater(() -> model.fireTableDataChanged());
                sleep(500);
                System.out.println("ok");
            }catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
