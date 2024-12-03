package threads;


import window.trades.trade;
import window.trades.tradeTable;

import javax.swing.*;
import java.util.List;

/**
 * időközönként kiszámolja a megvett részvények profitját
 */
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
                if(trades != null) {
                    for (trade t : trades) {
                        t.calculateProfit();
                        if(t.isOpen())
                            System.out.println(t);
                    }
                    SwingUtilities.invokeLater(() -> model.fireTableDataChanged());
                }
                sleep(500); //500
            }catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
