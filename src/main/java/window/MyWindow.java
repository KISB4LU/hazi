package window;

import org.example.HistoricalData;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {
    private HistoricalData hd = new HistoricalData();

    public MyWindow() {
        super("borker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);
        setSize(1000, 700);
        Graph graph = new Graph();
        MenuBar menu = new MenuBar();
        WatchList watchList = new WatchList();

        JPanel LeftPanel = new JPanel();
        LeftPanel.setLayout(new BorderLayout());

        LeftPanel.add(menu, BorderLayout.NORTH);
        LeftPanel.add(graph, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, LeftPanel, new WatchList());
        splitPane.setDividerLocation(800);
        getContentPane().add(splitPane);
        //Listeners
        menu.getLine().addActionListener(e -> {
            System.out.println("i pressed the fucking button");
            graph.setType(GraphType.LINE);
        });

        menu.getCandle().addActionListener(e -> {
            System.out.println("i pressed the fucking button");
            graph.setType(GraphType.CANDLE);

        });

        menu.getTimeFrameBox().addActionListener(e -> {
            if (e.getSource() == menu.getTimeFrameBox()) {
                String TimeFrame = menu.getTimeFrameBox().getSelectedItem().toString();
                String startDate = formatDate(menu.getStart().getModel().getValue());
                String endDate = formatDate(menu.getEnd().getModel().getValue());
                graph.setStock(hd.GetChart(watchList.getSymbol(), TimeFrame, startDate, endDate, "asd"));
            }
        });

        menu.getStart().addActionListener(e -> {
            String startDate = formatDate(menu.getStart().getModel().getValue());
            String endDate = formatDate(menu.getEnd().getModel().getValue());
            graph.setStock(hd.GetChart(watchList.getSymbol(), menu.getTimeFrame(), startDate, endDate, "asd"));
        });

        menu.getEnd().addActionListener(e -> {
            String startDate = formatDate(menu.getStart().getModel().getValue());
            String endDate = formatDate(menu.getEnd().getModel().getValue());
            graph.setStock(hd.GetChart(watchList.getSymbol(), menu.getTimeFrame(), startDate, endDate, "asd"));
        });

        watchList.getKereso().addActionListener(e -> {
            System.out.println("megnyomtam");
            String symbol = watchList.getKereso().getText();
            watchList.setSymbol(symbol);
            String startDate = formatDate(menu.getStart().getModel().getValue());
            String endDate = formatDate(menu.getEnd().getModel().getValue());
            graph.setStock(hd.GetChart(watchList.getSymbol(), menu.getTimeFrame(), startDate, endDate, "asd"));
        });

// Helper metódus a dátum formázására

    }

    private String formatDate(Object date) {
        if (date != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(((java.util.Date) date).getTime());
        }
        return "";
    }
}
