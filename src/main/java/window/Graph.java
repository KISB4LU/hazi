package window;

import org.example.Chart;
import org.example.HistoricalData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Graph extends JPanel {
    /*private BufferedImage image;
    Chart(){
        image = new BufferedImage(200,100,BufferedImage.TYPE_INT_RGB);
    }*/
    private int width = 600;
    private int height = 400;
    private BufferedImage LineGraph(){
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        //g.drawLine(0,10,50,50);
        HistoricalData hd = new HistoricalData();
        Chart stock[] = null;
        try {
            stock = hd.GetChart("AAPL","5Min","1000","iex");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double min = stock[0].close();
        double max = stock[0].close();
        for(int i = 0; i<stock.length; i++){
            if(stock[i].close()<min){
                min = stock[i].close();
            }
            if(stock[i].close()>max){
                max = stock[i].close();
            }
        }
        double Xdiff =width/stock.length;
        double x0 = 0;
        double x1 = Xdiff;
        double Ydiff = height/(max-min);
        for(int i = 0; i<stock.length-1; i++){
            double y0 = stock[i].close()-min;
            double y1 = stock[i+1].close()-min;
            y0 = height - y0*Ydiff;
            y1 = height - y1*Ydiff;
            g.draw(new Line2D.Double(x0,y0,x1,y1));
            x0 += Xdiff;
            x1 += Xdiff;
                System.out.println(i+". Date: " + stock[i].date() + " close: " + stock[i].close());
        }
        return image;
    }
    private BufferedImage Candlestick (){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        HistoricalData hd = new HistoricalData();

        Chart stock[] = null;
        try {
            stock = hd.GetChart("AAPL","5Min","1000","iex");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        double min = stock[0].low();
        double max = stock[0].high();
        for(Chart c: stock){
            if(c.low()<min){
                min = c.low();
            }
            if(c.high()>max){
                max = c.high();
            }
        }
        //g.drawLine(50, 25, 50, 75);
        double Ydiff = height/(max-min);
        double x = 0;
        double Xdiff = width/stock.length;
        for(Chart c : stock){
            if(c.open()<c.close()){
                g.setColor(Color.GREEN);
            }
            if(c.open()>c.close()){
                g.setColor(Color.RED);
            }
            double axis = x+Xdiff/2;
            double Ylow = (c.low()-min)*Ydiff;
            double Yhigh = (c.high()-min)*Ydiff;
            System.out.println("axis: " + axis + " Ylow: " + Ylow + " Yhigh: " + Yhigh);
            g.draw(new Line2D.Double(axis,height-Ylow,axis,height-Yhigh));
            x += Xdiff;
        }
        return image;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(Candlestick(), 0, 0, null);
    }
}
