package org.example;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import javax.swing.*;

public class Main extends JFrame {

    public Main(){
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    void draw_chart(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        HistoricalData hd = new HistoricalData();
        Chart[] AAPL = null;
        try {
            AAPL = hd.GetChart("AAPL","1Min","1000","sip");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double min = AAPL[0].close();
        for(Chart c : AAPL){
            if(c.close()<min){
                min = c.close();
            }
        }
        double x0 = 0;
        double x1 = 2;
        //g2d.draw(new Line2D.Double(10.5,21.3,35.4,67.43));
        for(int i = 0; i < AAPL.length-1; i++){
            double y0 = AAPL[i].close();//-min;
            double y1 = AAPL[i+1].close();//-min;
            g2d.setColor(Color.RED);
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
            //System.out.printf("x0:%d y0:%d x1:%d y1:%d",x0,y0,x1,y1);
            x0 += 2;
            x1 += 2;
        }

    }
    public void paint(Graphics g){
        super.paint(g);
        draw_chart(g);
    }

    public static void main(String[] args) throws IOException {
        /*HistoricalData hd = new HistoricalData();
        String json = hd.GetData("AAPL","1Min","1000","sip");
        System.out.println(json);
        Chart[] AAPL = hd.GetChart("AAPL","1Min","1000","sip");
        for (Chart c : AAPL) {
            System.out.println("Date: " + c.date() + " close: " + c.close());
        }*/

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}