package window;

import javax.swing.*;

public class MyWindow extends JFrame {
    public MyWindow() {
        super("borker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);
        setSize(800, 600);
        Graph graph = new Graph();
        add(graph);
    }

}
