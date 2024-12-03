package indicators;

import com.google.gson.annotations.Expose;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.ParabolicSarIndicator;
import org.ta4j.core.num.Num;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;

public class ParabolicSAR extends indicator{
    @Expose
    private double Start;
    @Expose
    private double Increment;
    @Expose
    private double MaxValue;
    private JPanel save;
    private JFormattedTextField StartField;
    private JFormattedTextField IncrementField;
    private JFormattedTextField MaxValueField;

    /**
     * ParabolicSar konstruktora ami beállitja az alapértelmezett paramétereket
     * és konfigurálja a settings panelt
     */
    public ParabolicSAR() {
        Start = 0.02;
        Increment = 0.02;
        MaxValue = 0.2;

        save = new JPanel();
        save.setLayout(new BoxLayout(save, BoxLayout.Y_AXIS));

        JPanel StartPanel = new JPanel();
        JLabel StartLabel = new JLabel("Start");
        StartField = new JFormattedTextField(new DecimalFormat("0.00"));
        StartField.setValue(Start);
        StartPanel.add(StartLabel);
        StartPanel.add(StartField);

        JPanel IncrementPanel = new JPanel();
        JLabel IncrementLabel = new JLabel("Increment");
        IncrementField = new JFormattedTextField(new DecimalFormat("0.00"));
        IncrementField.setValue(Increment);
        IncrementPanel.add(IncrementLabel);
        IncrementPanel.add(IncrementField);

        JPanel MaxValuePanel = new JPanel();
        JLabel MaxValueLabel = new JLabel("MaxValue");
        MaxValueField = new JFormattedTextField(new DecimalFormat("0.00"));
        MaxValueField.setValue(MaxValue);
        MaxValuePanel.add(MaxValueLabel);
        MaxValuePanel.add(MaxValueField);

        save.add(StartPanel);
        save.add(IncrementPanel);
        save.add(MaxValuePanel);
        save.setVisible(false);
    }
    @Override
    public void draw(Graphics2D g, BarSeries stock, int width, int height){
        Num start = stock.numOf(Start);
        Num increment = stock.numOf(Increment);
        Num Max = stock.numOf(MaxValue);
        ParabolicSarIndicator Sar = new ParabolicSarIndicator(stock, start, Max, increment);

        Num min= stock.getFirstBar().getClosePrice(),
            max = stock.getFirstBar().getClosePrice();
        for(Bar bar : stock.getBarData()) {
            if(min.isGreaterThan(bar.getClosePrice())) min = bar.getClosePrice();
            if(max.isLessThan(bar.getClosePrice())) max = bar.getClosePrice();
        }

        //draving
        g.setColor(Color.RED);
        double Xdiff = (double) width/ (stock.getBarCount()-1);
        double Ydiff = height/(max.doubleValue()-min.doubleValue());
        double x = 0, y = 0;
        double rad = 3;
        for(int i = 0; i < stock.getBarCount()-1; i++){
            if(Sar.getValue(i).isGreaterThan(Sar.getValue(i+1))){
                g.setColor(Color.RED);
            } else
                g.setColor(Color.GREEN);

            y = height - Sar.getValue(i).minus(min).doubleValue()*Ydiff;
            g.fill(new Ellipse2D.Double(x-rad, y-rad, rad, rad));
            x += Xdiff;
        }
    }
    @Override
    public JPanel getPanel(){
        return save;
    }
    @Override
    public void save(){
        Start = Double.parseDouble(StartField.getText());
        Increment = Double.parseDouble(IncrementField.getText());
        MaxValue = Double.parseDouble(MaxValueField.getText());
    }
    @Override
    public String toString(){
        return "SAR"+Start+" "+Increment+" "+MaxValue;
    }
}
