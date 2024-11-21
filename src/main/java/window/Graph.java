package window;

import indicators.MovingAvrage;
import indicators.indicator;
import org.example.HistoricalData;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.num.Num;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.time.ZonedDateTime;
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
    private ArrayList<indicator> indicators;
    private int width = 800;
    private int height = 600;
    private GraphType type = GraphType.LINE;
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private BarSeries stock;
    private String symbol;
    public Graph() {
        indicators = new ArrayList<>();
        indicators.add(new MovingAvrage(Color.BLUE,25));
        indicators.add(new MovingAvrage(Color.RED,50));
        indicators.add(new MovingAvrage(Color.GREEN,100));
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

        double min = stock.getFirstBar().getClosePrice().doubleValue();
        double max = stock.getFirstBar().getClosePrice().doubleValue();
        for(Bar bar : stock.getBarData()){
            if(bar.getClosePrice().doubleValue()<min){
                min = bar.getClosePrice().doubleValue();
            }
            if(bar.getClosePrice().doubleValue()>max){
                max = bar.getClosePrice().doubleValue();
            }
        }
        double Xdiff = (double) width /(stock.getBarCount());
        double x0 = 0;
        double x1 = Xdiff;
        double Ydiff = height/(max-min);
        for(int i = 0; i<stock.getBarCount()-1; i++){
            double y0 = stock.getBar(i).getClosePrice().doubleValue()-min;
            double y1 = stock.getBar(i+1).getClosePrice().doubleValue()-min;
            y0 = height - y0*Ydiff;
            y1 = height - y1*Ydiff;
            g.draw(new Line2D.Double(x0,y0,x1,y1));
            x0 += Xdiff;
            x1 += Xdiff;
                System.out.println(i+". Date: " + stock.getBar(i).getEndTime() + " close: " + stock.getBar(i).getClosePrice());
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
        double min = stock.getFirstBar().getClosePrice().doubleValue();
        double max = stock.getFirstBar().getClosePrice().doubleValue();
        for(Bar bar : stock.getBarData()){
            if(bar.getClosePrice().doubleValue()<min){
                min = bar.getClosePrice().doubleValue();
            }
            if(bar.getClosePrice().doubleValue()>max){
                max = bar.getClosePrice().doubleValue();
            }
        }
        //g.drawLine(50, 25, 50, 75);
        double Ydiff = height/(max-min);
        double x = 0;
        double Xdiff = (double) width /(stock.getBarCount()-1);
        //g.drawRect(0, 0, 50, 50);
        ZonedDateTime PrevDate = stock.getFirstBar().getEndTime();
        for(Bar bars : stock.getBarData()){
            if(bars.getEndTime().getDayOfYear() != PrevDate.getDayOfYear()){
                g.setColor(Color.white);
                g.draw(new Line2D.Double(x,0,x,height));
            }
            PrevDate = bars.getEndTime();
            double Y;
            double CandleWidth = Xdiff-2;//candletick width
            double CandleHeight;
            if(bars.getOpenPrice().isLessThan(bars.getClosePrice())){
                g.setColor(Color.GREEN);
                Y = (bars.getClosePrice().doubleValue()-min)*Ydiff;
                CandleHeight = Y - (bars.getOpenPrice().doubleValue()-min)*Ydiff;
                g.fill(new Rectangle2D.Double(x+1,height - Y,CandleWidth,CandleHeight));
            }
            if(bars.getOpenPrice().isGreaterThan(bars.getClosePrice())){
                g.setColor(Color.RED);
                Y = (bars.getOpenPrice().doubleValue()-min)*Ydiff;
                CandleHeight = Y - (bars.getClosePrice().doubleValue()-min)*Ydiff;
                g.fill(new Rectangle2D.Double(x+1,height - Y,CandleWidth,CandleHeight));
            }
            double axis = x+Xdiff/2;
            double Ylow = (bars.getLowPrice().doubleValue()-min)*Ydiff;
            double Yhigh = (bars.getHighPrice().doubleValue()-min)*Ydiff;
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

        for (indicator i : indicators) {
            if(i != null)
                i.draw(g, stock, width, height);
        }

        g.dispose();
        repaint();
    }
    public void setType(GraphType type){
        this.type = type;
        DrawGraph();
    }
    public void setStock(BarSeries stock){
        this.stock = stock;
        DrawGraph();
    }

    public ArrayList<indicator> getIndicators() {
        return indicators;
    }
    public void refresh(){
        DrawGraph();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }
}
