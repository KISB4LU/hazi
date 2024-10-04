package window;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {
    public MyWindow() {
        super("borker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);
        setSize(1000, 700);
        Graph graph = new Graph();
        MenuBar menu = new MenuBar();

        JPanel LeftPanel = new JPanel();
        LeftPanel.setLayout(new BorderLayout());

        LeftPanel.add(menu, BorderLayout.NORTH);
        LeftPanel.add(graph, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, LeftPanel, new WatchList());
        splitPane.setDividerLocation(800);
        getContentPane().add(splitPane);

        menu.getLine().addActionListener(e -> {
            System.out.println("i pressed the fucking button");
            graph.setType(GraphType.LINE);
        });

        menu.getCandle().addActionListener(e -> {
            System.out.println("i pressed the fucking button");
            graph.setType(GraphType.CANDLE);
        });

    }

}
