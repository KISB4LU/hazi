package indicators;

import org.example.Chart;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Arrays;

public class MovingAvrage extends indicator {
    Color style;
    int len;
    public MovingAvrage(Color color, int len) {
        style = color;
        this.len = len;
    }
    @Override
    public void draw(Graphics2D g, Chart chart[], int width, int height) {
        System.out.println("Moving Avrage");
        g.setColor(style);
        double min= chart[0].close(), max = chart[0].close();
        for(Chart c : chart) {
            if(min>c.close()) min = c.close();
            if(max<c.close()) max = c.close();
        }
        /*
        double Ydiff = height / (max - min);
        int Xdiff = width / (len - 1);
        double ma[] = new double[chart.length];
        double sum = 0;
        for(int i = 0; i < ma.length; i++) {
            ma[i] = (chart[i].close() - min)*Ydiff;
            if(i<len && i>0){
                sum += ma[i];
                ma[i] = sum/i;
            }else if(i>0){
                sum = 0;
                for(int j = 0; j <len; j++){
                    sum += ma[i-j];
                }
                ma[i] = sum/len;
            }
        }

        int x0 = 0;
        int x1 = Xdiff;
        for(int i = 0; i < ma.length-1; i++) {
            System.out.println(ma[i]);
            int y0 = height - (int)ma[i];
            int y1 = height - (int)ma[i+1];
            g.drawLine(x0, y0, x1, y1);
            x0 += Xdiff;
            x1 += Xdiff;
        }*/
        double ma[] = new double[chart.length];
        double sum = 0;
        for(int i = 0; i < len; i++){
            sum += chart[i].close();
            if(i >0) ma[i] = sum / (i+1);
            else ma[i] = sum;

        }
        //double min = ma[0];
        //double max = ma[0];
        for (int i = len; i < chart.length; i++) {
            sum = 0;
            for (int j = 0; j < len; j++) {
                sum += chart[i-j].close();
            }
            ma[i] = sum / len;
            System.out.println(sum);
            //if(min > ma[i]) min = ma[i];
            //if(max < ma[i]) max = ma[i];
        }

        //draving
        double Xdiff = (double) width/ (ma.length-1);
        double Ydiff = height/(max-min);
        double x0 = 0;
        double x1 = Xdiff;
        System.out.println("min: "+min + " max: "+max);
        for (int i = 0; i < ma.length-1; i++) {
            double y0 = height-(ma[i]-min)*Ydiff;
            double y1 = height-(ma[i+1]-min)*Ydiff;
            g.draw(new Line2D.Double(x0, y0, x1, y1));
            System.out.println("ma["+i+"]:"+ma[i]+" x0: "+x0+" y0: "+y0+" x1: "+x1+" y1: "+y1);
            x0 += Xdiff;
            x1 += Xdiff;
        }
    }
}
