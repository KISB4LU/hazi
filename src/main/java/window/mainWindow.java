package window;

import org.example.HistoricalData;
import window.login.User;
import window.login.loginPage;
import window.login.registerPage;
import window.menubar.MenuBar;
import window.trades.TradeHsitory;
import window.trades.tradeWindow;
import window.watchlist.WatchList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mainWindow extends JFrame {
    private HistoricalData hd = new HistoricalData();
    private JPanel MainPage;
    private JPanel MainWIndowPanel;
    private User user;
    public mainWindow() {
        super("borker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);
        setSize(1000, 700);
        setLayout(new FlowLayout());
        MainPage = new JPanel();
        MainWIndowPanel = new JPanel();
        MainPage.setLayout(new BorderLayout());
        loginPage login = new loginPage();
        registerPage register = new registerPage();
        Graph graph = new Graph();
        window.menubar.MenuBar menu = new MenuBar();
        WatchList watchList = new WatchList();
        tradeWindow trade = new tradeWindow(user);
        TradeHsitory history = new TradeHsitory();
        JPanel LeftPanel = new JPanel();
        LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));
        LeftPanel.add(watchList);
        LeftPanel.add(trade);
        trade.setVisible(true);
        //watchList.setSize(100, 700);
        MainPage.add(menu, BorderLayout.NORTH);
        MainPage.add(graph, BorderLayout.CENTER);
        MainPage.add(LeftPanel, BorderLayout.WEST);
        MainPage.add(history, BorderLayout.SOUTH);
        MainWIndowPanel.add(MainPage);
        MainWIndowPanel.add(login);
        MainWIndowPanel.add(register);
        add(MainWIndowPanel);
        //LeftPanel.setLayout(new BorderLayout()

        MainPage.setVisible(false);
        login.setVisible(true);
        register.setVisible(false);
        //LeftPanel.add(menu, BorderLayout.NORTH);
        //LeftPanel.add(graph, BorderLayout.CENTER);

        //JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, LeftPanel, watchList);
        //splitPane.setDividerLocation(800);
        //getContentPane().add(splitPane);
        //Listeners
        login.getLogin().addActionListener(e -> {
            user = login.login();
            if (user != null) {
                history.SetUser(user);
                MainPage.setVisible(true);
                login.setVisible(false);
            }
        });
        login.getRegister().addActionListener(e -> {
                MainPage.setVisible(false);
                login.setVisible(false);
                register.setVisible(true);
        });
        register.getConfirm().addActionListener(e -> {
           user = register.checkRegister();
           if (user != null) {
               history.SetUser(user);
               MainPage.setVisible(true);
               register.setVisible(false);
           }
        });
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
        watchList.getKereso().addActionListener(e -> {
            System.out.println("megnyomtam");
            String symbol = watchList.getKereso().getText();
            watchList.setSymbol(symbol);
            String startDate = formatDate(menu.getStart().getModel().getValue());
            String endDate = formatDate(menu.getEnd().getModel().getValue());
            graph.setStock(hd.GetChart(watchList.getSymbol(), menu.getTimeFrame(), startDate, endDate, "asd"));
        });
        watchList.getWatchlistTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = watchList.getWatchlistTable().rowAtPoint(e.getPoint());
                String symbol = watchList.getWatchlistTable().getModel().getValueAt(row, 0).toString();
                if(e.getClickCount() == 2) {
                    trade.setVisible(true);
                    trade.setSymbol(symbol);
                }
                if(e.getClickCount() == 1) {
                    watchList.setSymbol(symbol);
                    String startDate = formatDate(menu.getStart().getModel().getValue());
                    String endDate = formatDate(menu.getEnd().getModel().getValue());
                    graph.setStock(hd.GetChart(symbol, menu.getTimeFrame(), startDate, endDate, "asd"));
                }
            }
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
