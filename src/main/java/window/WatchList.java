package window;

import javax.swing.*;
import java.awt.*;

public class WatchList extends JPanel {
    public WatchList() {

        add(new JTextField("Kereső "), BorderLayout.NORTH);
        add(new JList<String>(), BorderLayout.CENTER);
    }
}
