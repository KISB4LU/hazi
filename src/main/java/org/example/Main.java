package org.example;
import window.MyWindow;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

public class Main{

    public static void main(String[] args) throws IOException {
        HistoricalData hd = new HistoricalData();
        String json = hd.GetData("AAPL","1Hour","100","sip");
        System.out.println(json);
        Chart[] AAPL = hd.GetChart("AAPL","1Hour","100","sip");
        double min = AAPL[0].close();
        for (Chart c : AAPL) {
            System.out.println("Date: " + c.date() + " close: " + c.close());
        }
        MyWindow window = new MyWindow();
        window.setVisible(true);
    }
}