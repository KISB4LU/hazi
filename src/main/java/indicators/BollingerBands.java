package indicators;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

import com.google.gson.annotations.Expose;

public class BollingerBands extends indicator{
    @Expose
    private int length;
    private JFormattedTextField lengthField;
    private JPanel settings;

    /**
     * Bollinger szallag konstruktora ami beállitja az alapértelmezett paramétereket
     * és konfigurálja a settings panelt
     */
    public BollingerBands() {
        this.length = 20;

        settings = new JPanel();
        lengthField = new JFormattedTextField(new NumberFormatter(NumberFormat.getIntegerInstance()));
        lengthField.setValue(length);

        settings.add(lengthField);
        settings.setVisible(false);

        lengthField.addActionListener(e -> length = Integer.parseInt(lengthField.getText()));
    }


    @Override
    public void draw(Graphics2D g, BarSeries stock, int width, int height){
        StandardDeviationIndicator deviation = new StandardDeviationIndicator(new ClosePriceIndicator(stock), length);
        SMAIndicator sma = new SMAIndicator(new ClosePriceIndicator(stock), length);

        BollingerBandsMiddleIndicator middleBand = new BollingerBandsMiddleIndicator(sma);
        BollingerBandsUpperIndicator upperBand = new BollingerBandsUpperIndicator(middleBand, deviation);
        BollingerBandsLowerIndicator lowerBand = new BollingerBandsLowerIndicator(middleBand, deviation);


        Num min = stock.getFirstBar().getClosePrice(),
            max = stock.getLastBar().getClosePrice();
        for(Bar bar : stock.getBarData()){
            if(min.isGreaterThan(bar.getClosePrice())) min = bar.getClosePrice();
            if(max.isLessThan(bar.getClosePrice())) max = bar.getClosePrice();
        }

        double Xdiff = (double) width/ (stock.getBarCount()-1);
        double Ydiff = height/(max.doubleValue()-min.doubleValue());
        double x0 = 0;
        double x1 = Xdiff;
        double y0, y1;

        for(int i = 0; i < stock.getBarCount()-1; i++){
            g.setColor(Color.RED);
            y0 = height - upperBand.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - upperBand.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));

            g.setColor(Color.GREEN);
            y0 = height - middleBand.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - middleBand.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));

            g.setColor(Color.RED);
            y0 = height - lowerBand.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - lowerBand.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));

            x0 += Xdiff;
            x1 += Xdiff;
        }
    }


    @Override
    public JPanel getPanel(){
        return settings;
    }


    @Override
    public void save(){
        length = Integer.parseInt(lengthField.getText());
    }
    @Override
    public String toString() {
        return "BollingerBands("+length+")";
    }
}
