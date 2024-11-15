package indicators;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.text.NumberFormat;

public class MovingAvrage extends indicator {
    Color style;
    int len;
    private JPanel settings;

    public MovingAvrage(Color color, int len) {
        style = color;
        this.len = len;
        initSettings();
    }
    public MovingAvrage() {
        initSettings();
    }
    private void initSettings(){
        settings = new JPanel();

        JColorChooser cc =  new JColorChooser(style);

        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setMinimum(0);
        numberFormatter.setMaximum(Integer.MAX_VALUE);
        JFormattedTextField length = new JFormattedTextField(numberFormatter);
        length.setColumns(10);

        settings.add(length);
        settings.add(cc);
        settings.setVisible(false);

        length.addActionListener(e -> len = Integer.parseInt(length.getText()));
    }
    @Override
    public void draw(Graphics2D g, BarSeries stock, int width, int height) {
        System.out.println("Moving Avrage");
        g.setColor(style);

        ClosePriceIndicator closePrice = new ClosePriceIndicator(stock);


        SMAIndicator sma = new SMAIndicator(closePrice, len);
        Num min= stock.getFirstBar().getClosePrice(),
            max = stock.getFirstBar().getClosePrice();
        for(Bar bar : stock.getBarData()) {
            if(min.isGreaterThan(bar.getClosePrice())) min = bar.getClosePrice();
            if(max.isLessThan(bar.getClosePrice())) max = bar.getClosePrice();
        }

        //draving
        double Xdiff = (double) width/ (stock.getBarCount()-1);
        double Ydiff = height/(max.doubleValue()-min.doubleValue());
        double x0 = 0;
        double x1 = Xdiff;
        System.out.println("min: "+min + " max: "+max);
        for (int i = 0; i < stock.getBarCount()-1; i++) {
            double y0 = height- sma.getValue(i).minus(min).doubleValue()*Ydiff;
            double y1 = height- sma.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));
            System.out.println("ma["+i+"]:"+sma.getValue(i)+" x0: "+x0+" y0: "+y0+" x1: "+x1+" y1: "+y1);
            x0 += Xdiff;
            x1 += Xdiff;
        }
    }
    @Override
    public JPanel getPanel() {
        return settings;
    }
    @Override
    public String toString() {
        return "Moving Avrage (" + len + ")";
    }

}
