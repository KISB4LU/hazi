package window;

import indicators.MovingAvrage;
import indicators.indicator;
import org.example.Chart;
import org.example.HistoricalData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum GraphType{
    LINE,
    CANDLE
}
public class Graph extends JPanel {
    /*private BufferedImage image;
    Chart(){
        image = new BufferedImage(200,100,BufferedImage.TYPE_INT_RGB);
    }*/
    private List<indicator> indicators;
    private int width = 800;
    private int height = 600;
    private GraphType type = GraphType.LINE;
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Chart stock[];
    public Graph() {
        indicators = new ArrayList<>();
        indicators.add(new MovingAvrage(Color.BLUE,9));
        HistoricalData hd = new HistoricalData();
        stock = hd.GetChart("AAPL","5Min","2024-01-01","2024-01-02","sip");

        DrawGraph();
    }
    private void LineGraph(Graphics2D g){
        //g.drawLine(0,10,50,50);
        g.setColor(Color.BLUE);
        /*HistoricalData hd = new HistoricalData();
        Chart stock[] = null;
        try {
            stock = hd.GetChart("AAPL","15Min","1000","iex");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

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
        double Xdiff = (double) width /(stock.length);
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
    }
    private void Candlestick (Graphics2D g){
        /*HistoricalData hd = new HistoricalData();

        Chart stock[] = null;
        try {
            stock = hd.GetChart("AAPL","15Min","1000","iex");
            for(Chart c: stock){
                System.out.println("Date: "+c.date()+ " close: "+ c.close());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
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
        double Xdiff = (double) width /(stock.length-1);
        //g.drawRect(0, 0, 50, 50);
        String PrevDate = stock[0].date();
        for(Chart c : stock){
            String PrevDay[] = PrevDate.split("-");
            PrevDay = PrevDay[2].split("T");
            String CurDay[] = c.date().split("-");
            CurDay = CurDay[2].split("T");

            if(!PrevDay[0].equals(CurDay[0])){
                g.setColor(Color.white);
                g.draw(new Line2D.Double(x,0,x,height));
            }
            PrevDate = c.date();
            double Y;
            double CandleWidth = Xdiff-2;//candletick width
            double CandleHeight;
            if(c.open()<c.close()){
                g.setColor(Color.GREEN);
                Y = (c.close()-min)*Ydiff;
                CandleHeight = Y - (c.open()-min)*Ydiff;
                g.fill(new Rectangle2D.Double(x+1,height - Y,CandleWidth,CandleHeight));
            }
            if(c.open()>c.close()){
                g.setColor(Color.RED);
                Y = (c.open()-min)*Ydiff;
                CandleHeight = Y - (c.close()-min)*Ydiff;
                g.fill(new Rectangle2D.Double(x+1,height - Y,CandleWidth,CandleHeight));
            }
            double axis = x+Xdiff/2;
            double Ylow = (c.low()-min)*Ydiff;
            double Yhigh = (c.high()-min)*Ydiff;
            System.out.println("axis: " + axis + " Ylow: " + Ylow + " Yhigh: " + Yhigh);
            g.draw(new Line2D.Double(axis,height-Ylow,axis,height-Yhigh));
            x += Xdiff;
        }
    }

    private void DrawGraph() {
        Graphics2D g = image.createGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);

        switch (type) {
            case LINE:
                LineGraph(g);
                break;
            case CANDLE:
                Candlestick(g);
                break;
        }
        for(indicator i: indicators){
            i.draw(g, stock, width, height);
        }
        g.dispose();
        repaint();
    }
    public void setType(GraphType type){
        this.type = type;
        DrawGraph();
    }
    public void setStock(Chart[] stock){
        this.stock = stock;
        DrawGraph();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }
}
