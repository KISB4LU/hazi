package indicators;

import org.junit.jupiter.api.Test;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Bar;
import org.ta4j.core.num.DecimalNum;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ExpMovingAvrageTest {

    private ExpMovingAvrage expMovingAvrage = new ExpMovingAvrage();

    @Test
    public void shouldNotThrowExceptionWithValidArguments() {
        // Generate a dummy bar series
        BaseBarSeries priceSeries = new BaseBarSeries("Dummy data");
        for (int i = 1; i <= 10; i++) {
            Bar bar = new BaseBar(i, i + 1, i + 2, i + 3, i + 4, DecimalNum.valueOf(i));
            priceSeries.addBar(bar);
        }

        // Initialize the image
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        try {
            expMovingAvrage.draw(graphics, priceSeries, image.getWidth(), image.getHeight());
        } catch (Exception e) {
            fail("Test Failed: Exception thrown when valid arguments were passed to draw method");
        }
    }

    @Test
    public void testDefaultStyleIsYellow() {
        assertEquals(Color.YELLOW, expMovingAvrage.style);
    }

    @Test
    public void testDefaultLenIsNine() {
        assertEquals(9, expMovingAvrage.len);
    }

    @Test
    public void checkPanelNotNull() {
        assertNotNull(expMovingAvrage.getPanel());
    }
}