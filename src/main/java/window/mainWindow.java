package window;

import indicators.indicator;
import org.example.HistoricalData;
import threads.GraphThread;
import window.login.User;
import window.login.loginPage;
import window.login.registerPage;
import window.login.userHandler;
import window.menubar.MenuBar;
import window.trades.CloseOrder;
import window.trades.TradeHsitory;
import window.trades.trade;
import window.trades.tradeWindow;
import window.watchlist.WatchList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class mainWindow extends JFrame {
    private HistoricalData hd = new HistoricalData();
    private JPanel MainPage;
    private JPanel MainWIndowPanel;
    private User user;
    public mainWindow() {
        super("borker");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);
        setSize(1000, 700);

        MainPage = new JPanel();
        MainWIndowPanel = new JPanel();
        MainPage.setLayout(new BorderLayout());
        loginPage login = new loginPage();
        registerPage register = new registerPage(login);
        Graph graph = new Graph();
        window.menubar.MenuBar menu = new MenuBar();
        WatchList watchList = new WatchList();
        tradeWindow TradeWindow = new tradeWindow(user);
        TradeHsitory history = new TradeHsitory();
        Runnable updateProfile = menu::updateProfile;
        CloseOrder closeOrder = new CloseOrder(updateProfile);
        closeOrder.setVisible(false);
        //history.setSize(500,200);
        JPanel LeftPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        //JPanel RightPanel = new JPanel();
        LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));
        bottomPanel.setLayout(new FlowLayout());
        LeftPanel.add(watchList);
        LeftPanel.add(TradeWindow);
        bottomPanel.add(closeOrder);
        bottomPanel.add(history);/*
        RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.Y_AXIS));
        RightPanel.add(graph);
        RightPanel.add(history);*/

        JSplitPane HsplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, LeftPanel, graph);
        HsplitPane.setDividerLocation(200);

        JSplitPane VsplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, HsplitPane, bottomPanel);
        VsplitPane.setDividerLocation(450);
        MainPage.add(menu, BorderLayout.NORTH);
        MainPage.add(VsplitPane, BorderLayout.CENTER);
        /*MainPage.add(LeftPanel, BorderLayout.WEST);
        MainPage.add(RightPanel, BorderLayout.CENTER);*/
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
                TradeWindow.setUser(user);
                menu.setProfile(user);
                MainPage.setVisible(true);
                login.setVisible(false);
                graph.setIndicators((ArrayList<indicator>) user.getIndicators());
                watchList.setwatclist(user.getWatchlist());

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
               TradeWindow.setUser(user);
               menu.setProfile(user);
               graph.setIndicators((ArrayList<indicator>) user.getIndicators());
               watchList.setwatclist(user.getWatchlist());
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

        GraphThread gt = new GraphThread(graph);
        gt.start();
        menu.getTimeFrameBox().addActionListener(e -> {
            if (e.getSource() == menu.getTimeFrameBox()) {
                String TimeFrame = menu.getTimeFrameBox().getSelectedItem().toString();
                String startDate = formatDate(menu.getStart().getModel().getValue());
                String endDate = formatDate(menu.getEnd().getModel().getValue());
                gt.setBars(watchList.getSymbol(), TimeFrame, startDate, endDate);
            }
        });

        menu.getStart().addActionListener(e -> {
            String startDate = formatDate(menu.getStart().getModel().getValue());
            String endDate = formatDate(menu.getEnd().getModel().getValue());
            gt.setBars(watchList.getSymbol(), menu.getTimeFrame(), startDate, endDate);
        });

        menu.getEnd().addActionListener(e -> {
            String startDate = formatDate(menu.getStart().getModel().getValue());
            String endDate = formatDate(menu.getEnd().getModel().getValue());
            gt.setBars(watchList.getSymbol(), menu.getTimeFrame(), startDate, endDate);
        });
        watchList.getKereso().addActionListener(e -> {
            System.out.println("megnyomtam");
            String symbol = watchList.getKereso().getText();
            watchList.setSymbol(symbol);
            String startDate = formatDate(menu.getStart().getModel().getValue());
            String endDate = formatDate(menu.getEnd().getModel().getValue());
            gt.setBars(watchList.getSymbol(), menu.getTimeFrame(), startDate, endDate);
        });
        watchList.getWatchlistTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = watchList.getWatchlistTable().rowAtPoint(e.getPoint());
                String symbol = watchList.getWatchlistTable().getModel().getValueAt(row, 0).toString();
                if(e.getClickCount() == 2) {
                    TradeWindow.setVisible(true);
                    TradeWindow.setSymbol(symbol);
                }
                if(e.getClickCount() == 1) {
                    watchList.setSymbol(symbol);
                    String startDate = formatDate(menu.getStart().getModel().getValue());
                    String endDate = formatDate(menu.getEnd().getModel().getValue());
                    gt.setBars(symbol, menu.getTimeFrame(), startDate, endDate);
                }
            }
        });

        Timer timer = new Timer(100, null);
        timer.addActionListener(e -> {
            if (history.table() != null) {
                timer.stop();
                history.table().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int row = history.table().rowAtPoint(e.getPoint());
                        if (e.getClickCount() == 2) {
                            trade Trade = history.model().getElementAt(row);
                            if(Trade.isOpen()) {
                                closeOrder.setTrade(Trade);
                                closeOrder.setVisible(true);
                                closeOrder.setProfit();
                                //System.out.println(Trade);
                            }
                        }
                    }
                });
            }
        });
        timer.start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(user != null) {
                    userHandler uh = new userHandler();
                    User[] users = uh.readUsers();
                    user.setIndicators(graph.getIndicators());
                    user.setWatchlist(watchList.getWatchlist());
                    for(int i = 0; i < users.length; i++) {
                        if(users[i].getName().equals(user.getName()) && users[i].getPassword().equals(user.getPassword())) {
                            users[i] = user;
                        }
                    }
                    uh.writeUsers(users);
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
