package window.watchlist;

import org.example.Asset;
import org.example.HistoricalData;
import org.example.Quote;
import threads.watchlistThread;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WatchList extends JPanel {
    private HistoricalData hd;
    private JPanel searchPanel;
    private  JTable watchlistTable;
    private String CurrentSymbol;
    private JTextField bevitel;
    private JButton addButton;
    private List<Element> watchlist;
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
        watchlist = new ArrayList<>();
        watchlist.add(new Element("AAPL"));
        watchlist.add(new Element("TSLA"));
        watchlist.add(new Element("NFLX"));
        searchPanel = new JPanel();
        window.watchlist.watchlistTable model  =new watchlistTable(watchlist);
        watchlistTable = new JTable(model);
        watchlistCellRenderer renderer = new watchlistCellRenderer(watchlistTable.getDefaultRenderer(Element.class));
        for (int i = 0; i < model.getColumnCount(); i++) {
            watchlistTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        watchlistThread thread = new watchlistThread(watchlist, model);
        thread.start();

        searchPanel.add(bevitel);
        searchPanel.add(addButton);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(watchlistTable),BorderLayout.CENTER);

       Asset assets[] = null;
        try {
             assets = hd.GetAsset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(assets[1].getName());

        addButton.addActionListener(e -> {

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
    public JTable getWatchlistTable() {
        return watchlistTable;
    }

}
