package indicators;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;

import javax.swing.*;
import java.awt.*;

public class BollingerBands extends indicator{
    public BollingerBands(double start, double end) {

    }
    @Override
    public void draw(Graphics2D g, BarSeries stock, int width, int height){
       /* BollingerBandsUpperIndicator up = new BollingerBandsUpperIndicator();
        BollingerBandsMiddleIndicator md = new BollingerBandsMiddleIndicator();
        BollingerBandsLowerIndicator lw = new BollingerBandsLowerIndicator();
*/
    }
    @Override
    public JPanel getPanel(){
        return null;
    }

    @Override
    public void save(){

    }

}
