package indicators;

import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.BarSeries;
import org.ta4j.core.num.PrecisionNum;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MovingAvrageTest {

    @Test
    public void testDraw() {
        BaseBarSeries series = new BaseBarSeries("MySeries");
        series.addBar(ZonedDateTime.now(), 1, 3, 0, 2);
        series.addBar(ZonedDateTime.now(), 2, 2, 2, 2);
        series.addBar(ZonedDateTime.now(), 3, 1, 1, 1);

        MovingAvrage movingAvrage = new MovingAvrage();
        movingAvrage.len = 2;

        BufferedImage image = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        movingAvrage.draw(graphics, series, 3, 3);

        // if all lines are drawn correctly, the image should be completely filled with yellow
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                assertEquals(Color.YELLOW.getRGB(), image.getRGB(i, j));
            }
        }
    }

    @Test
    public void testToString() {
        MovingAvrage movingAvrage = new MovingAvrage();
        movingAvrage.len = 5;
        assertEquals("Moving Avrage (5)", movingAvrage.toString());
    }

}