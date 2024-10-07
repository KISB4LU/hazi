package window;

import org.example.Asset;
import org.example.HistoricalData;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WatchList extends JPanel {
    private HistoricalData hd = new HistoricalData();
    private String CurrentSymbol;
    private JTextField kereso;
    private JList watchlist;
    private JButton addButton;
    public WatchList() {
        CurrentSymbol = "AAPL";
        addButton = new JButton("Add");
        kereso = new JTextField(10);
        watchlist = new JList<String>();

        add(kereso, BorderLayout.NORTH);
        add(addButton, BorderLayout.NORTH);
        add(watchlist, BorderLayout.CENTER);
        Asset assets[] = null;
        try {
             assets = hd.GetAsset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(assets[1].getName());
    }
    public JTextField getKereso() {
        return this.kereso;
    }
    public String getSymbol() {
        return CurrentSymbol;
    }
    public void setSymbol(String symbol) {
        CurrentSymbol = symbol;
    }

}
