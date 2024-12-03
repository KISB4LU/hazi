package window.watchlist;

import org.example.Asset;
import org.example.HistoricalData;
import org.example.Quote;
import threads.watchlistThread;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * watchlist ami segitségével a felhasználó nyomon követheti a kedvenc részévényei aktuális árfolyamát
 */
public class WatchList extends JPanel {
    private HistoricalData hd;
    private JPanel searchPanel;
    private  JTable watchlistTable;
    private String CurrentSymbol;
    private JTextField bevitel;
    private JButton addButton;
    private List<Element> watchlist;
    private watchlistTable model;
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
        searchPanel = new JPanel();
        model = new watchlistTable(watchlist);
        watchlistTable = new JTable(model);
        watchlistCellRenderer renderer = new watchlistCellRenderer(watchlistTable.getDefaultRenderer(Element.class));
        for (int i = 0; i < model.getColumnCount(); i++) {
            watchlistTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        watchlistTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = watchlistTable.getSelectedRow();
            int selectedCol = watchlistTable.getSelectedColumn();

            if(selectedRow != -1 && selectedCol == 3)
                model.DeleteElement(selectedRow);
        });
        //watchlistThread thread = new watchlistThread(watchlist, model);
        //thread.start();

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
            model.addElement(bevitel.getText());
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

    public void setwatclist(List<Element> watchlist) {
        this.watchlist = watchlist;
        model.setElements(watchlist);
        watchlistThread thread = new watchlistThread(this.watchlist, model);
        thread.start();
        model.fireTableDataChanged();
    }
    public List<Element> getWatchlist() {
        return model.getElements();
    }

}
