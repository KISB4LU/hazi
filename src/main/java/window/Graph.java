package window;

import org.example.Chart;
import org.example.HistoricalData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Graph extends JPanel {
    /*private BufferedImage image;
    Chart(){
        image = new BufferedImage(200,100,BufferedImage.TYPE_INT_RGB);
    }*/
    private int width = 400;
    private int height = 200;
    private BufferedImage draw(){
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        HistoricalData hd = new HistoricalData();
        Chart stock[] = null;
        try {
            stock = hd.GetChart("AAPL","1Hour","100","iex");
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
        double x0 = 0;
        double x1 = width/stock.length;
        for(int i = 0; i<stock.length-1; i++){
            double y0 = stock[i].close()/max;
            double y1 = stock[i+1].close()/max;
            y0 *= height;
            y1 *= height;
            g.draw(new Line2D.Double(x0,y0,x1,y1));
            x0 +=width/stock.length;
            x1 +=width/stock.length;
        }
        return image;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(draw(), 0, 0, null);
    }
}
