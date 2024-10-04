package window;

import javax.swing.*;
import java.awt.*;


public class MenuBar extends JPanel {
    private JButton line;
    private JButton candle;
    public MenuBar() {
        line = new JButton("Line");
        candle = new JButton("Candle");

        add(line);
        add(candle);
        add(new DatePicker("start: "));
        add(new DatePicker("end: "));
    }
    public JButton getLine() {
        return line;
    }
    public JButton getCandle() {
        return candle;
    }
}