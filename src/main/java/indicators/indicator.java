package indicators;

import org.ta4j.core.BarSeries;

import javax.swing.*;
import java.awt.*;

/**
 * absztrakt osztály ami segiti a töbféle indikátor heterogén kollekciőját
 */
public abstract class indicator {
    /**
     * a megatodd paraméterek segitségével rajzolja ki az indikátort
     * @param g
     * @param stock az adott részvény adatai ami alapján számolja ki a bollinger szallag értékét
     * @param width grafikon szélessége
     * @param height grafikon magassága
     */
    public abstract void draw(Graphics2D g, BarSeries stock, int width, int height);
    /**
     * visszatérési értk segitségével lehet konfigurálni az indikátort
     * @return settings panel
     */
    public abstract JPanel getPanel();
    /**
     * menti a változtatásokat
     */
    public abstract void save();
}
