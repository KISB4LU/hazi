package indicators;

import org.ta4j.core.BarSeries;

import javax.swing.*;
import java.awt.*;

public abstract class indicator {
    public abstract void draw(Graphics2D g, BarSeries stock, int width, int height);
    public abstract JPanel getPanel();
    public abstract void save();
}
