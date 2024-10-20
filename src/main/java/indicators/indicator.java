package indicators;

import org.example.Chart;

import java.awt.*;

public abstract class indicator {
    public abstract void draw(Graphics2D g, Chart chart[], int width, int height);
}
