package indicators;

import com.google.gson.annotations.Expose;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.ichimoku.*;
import org.ta4j.core.num.Num;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

public class Ichimoku extends indicator {
    @Expose
    private int ConversionLine;
    @Expose
    private int BaseLine;
    @Expose
    private int SpanBLen;
    @Expose
    private int LaggingSpan;

    private JPanel save;
    private JFormattedTextField Conversion;
    private JFormattedTextField Base;
    private JFormattedTextField Span;
    private JFormattedTextField Lagging;
    /**
     * Ichimoku felhő konstruktora ami beállitja az alapértelmezett paramétereket
     * és konfigurálja a settings panelt
     */
    public Ichimoku() {
        ConversionLine = 9;
        BaseLine = 26;
        SpanBLen = 52;
        LaggingSpan = 26;

        save = new JPanel();
        save.setLayout(new BoxLayout(save, BoxLayout.Y_AXIS));

        NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
        numberFormatter.setMinimum(0);
        numberFormatter.setMaximum(Integer.MAX_VALUE);

        JPanel ConversionPanel = new JPanel();
        JLabel ConversionLabel = new JLabel("Conversion");
        Conversion = new JFormattedTextField(numberFormatter);
        Conversion.setValue(ConversionLine);
        ConversionPanel.add(ConversionLabel);
        ConversionPanel.add(Conversion);

        JPanel BasePanel = new JPanel();
        JLabel BaseLabel = new JLabel("Base");
        Base = new JFormattedTextField(numberFormatter);
        Base.setValue(BaseLine);
        BasePanel.add(BaseLabel);
        BasePanel.add(Base);

        JPanel SpanPanel = new JPanel();
        JLabel SpanLabel = new JLabel("Span");
        Span = new JFormattedTextField(numberFormatter);
        Span.setValue(SpanBLen);
        SpanPanel.add(SpanLabel);
        SpanPanel.add(Span);

        JPanel LaggingPanel = new JPanel();
        JLabel LaggingLabel = new JLabel("Lagging");
        Lagging = new JFormattedTextField(numberFormatter);
        Lagging.setValue(LaggingSpan);
        LaggingPanel.add(LaggingLabel);
        LaggingPanel.add(Lagging);

        save.add(ConversionPanel);
        save.add(BasePanel);
        save.add(SpanPanel);
        save.add(LaggingPanel);
        save.setVisible(false);
    }
    @Override
    public void draw(Graphics2D g, BarSeries stock, int width, int height){

        IchimokuChikouSpanIndicator Span  = new IchimokuChikouSpanIndicator(stock, LaggingSpan);
        IchimokuKijunSenIndicator KijuSen = new IchimokuKijunSenIndicator(stock, BaseLine);
        IchimokuTenkanSenIndicator TenkanSen = new IchimokuTenkanSenIndicator(stock, ConversionLine);
        IchimokuSenkouSpanAIndicator SpanA = new IchimokuSenkouSpanAIndicator(stock,ConversionLine, BaseLine);
        IchimokuSenkouSpanBIndicator SpanB = new IchimokuSenkouSpanBIndicator(stock, SpanBLen, LaggingSpan);

        Num min= stock.getFirstBar().getClosePrice(),
                max = stock.getFirstBar().getClosePrice();
        for(Bar bar : stock.getBarData()) {
            if(min.isGreaterThan(bar.getClosePrice())) min = bar.getClosePrice();
            if(max.isLessThan(bar.getClosePrice())) max = bar.getClosePrice();
        }

        double Xdiff = (double) width/ (stock.getBarCount()-1);
        double Ydiff = height/(max.doubleValue()-min.doubleValue());
        double x0 = 0;
        double x1 = Xdiff;
        double y0, y1;

        for(int i = 0; i < stock.getBarCount()-1; i++) {
            g.setColor(Color.gray);
            y0 = height - Span.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - Span.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));

            g.setColor(Color.red);
            y0 = height - KijuSen.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - KijuSen.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));

            g.setColor(Color.blue);
            y0 = height - TenkanSen.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - TenkanSen.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));

            g.setColor(Color.black);
            y0 = height - SpanA.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - SpanA.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));

            y0 = height - SpanB.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - SpanB.getValue(i+1).minus(min).doubleValue()*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));

            x0 += Xdiff;
            x1 += Xdiff;
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(Color.GRAY);
        x0 = 0;
        x1 = Xdiff;
        for(int i = 0; i < stock.getBarCount()-1; i++) {
            Polygon p = new Polygon();
            y0 = height - SpanA.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - SpanA.getValue(i+1).minus(min).doubleValue()*Ydiff;
            p.addPoint((int)x0,(int)y0);
            p.addPoint((int)x1,(int)y1);

            y0 = height - SpanB.getValue(i).minus(min).doubleValue()*Ydiff;
            y1 = height - SpanB.getValue(i+1).minus(min).doubleValue()*Ydiff;
            p.addPoint((int)x1,(int)y1);
            p.addPoint((int)x0,(int)y0);
            g.fillPolygon(p);
            x0 += Xdiff;
            x1 += Xdiff;
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    @Override
    public JPanel getPanel(){
        return save;
    }
    @Override
    public void save(){
        ConversionLine = Integer.parseInt(Conversion.getText());
        BaseLine = Integer.parseInt(Base.getText());
        SpanBLen = Integer.parseInt(Span.getText());
        LaggingSpan = Integer.parseInt(Lagging.getText());
    }
    @Override
    public String toString(){
        return "Ichimoku";
    }
}
