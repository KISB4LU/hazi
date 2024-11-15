package window;

import org.example.Asset;
import org.example.HistoricalData;
import org.example.Quote;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WatchList extends JPanel {
    private HistoricalData hd;
    private JPanel searchPanel;
    private  JPanel watchlistPanel;
    private String CurrentSymbol;
    private JTextField bevitel;
    private JButton addButton;
    private ArrayList<Element> watchlist;
    public WatchList() {
        hd = new HistoricalData();
        CurrentSymbol = "AAPL";
        try {
            Quote quote = hd.GetQuote(CurrentSymbol);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addButton = new JButton("Add");
        bevitel = new JTextField(10);
        watchlist = new ArrayList<Element>();
        searchPanel = new JPanel();
        watchlistPanel = new JPanel();
        watchlistPanel.setLayout(new BoxLayout(watchlistPanel, BoxLayout.Y_AXIS));

        searchPanel.add(bevitel);
        searchPanel.add(addButton);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(watchlistPanel),BorderLayout.CENTER);

       Asset assets[] = null;
        try {
             assets = hd.GetAsset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(assets[1].getName());

        addButton.addActionListener(e -> {
            String symbol = bevitel.getText();
            System.out.println("element : "+symbol);
            Element newElement  = new Element(symbol, watchlist);
            watchlist.add(newElement);
            watchlistPanel.add(newElement);
        });
    }
    public JTextField getKereso() {
        return bevitel;
    }
    public String getSymbol() {
        return CurrentSymbol;
    }
    public void setSymbol(String symbol) {
        CurrentSymbol = symbol;
    }

}
