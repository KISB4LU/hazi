package window;

import org.example.HistoricalData;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, LeftPanel, watchList);
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

        menu.getIndicators().addActionListener(e -> {
            Runnable refresh = graph::refresh;
           IndicatorWindow window = new IndicatorWindow(graph.getIndicators(), refresh);
           window.setVisible(true);
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
        System.out.println(watchList.getKereso());
        watchList.getKereso().addActionListener(e -> {
            System.out.println("megnyomtam");
            String symbol = watchList.getKereso().getText();
            watchList.setSymbol(symbol);
            String startDate = formatDate(menu.getStart().getModel().getValue());
            String endDate = formatDate(menu.getEnd().getModel().getValue());
            graph.setStock(hd.GetChart(watchList.getSymbol(), menu.getTimeFrame(), startDate, endDate, "asd"));
        });
    }

    private String formatDate(Object date) {
        if (date != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(((java.util.Date) date).getTime());
        }
        return "";
    }
}
