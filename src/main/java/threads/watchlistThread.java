package threads;

import window.watchlist.Element;
import window.watchlist.watchlistTable;

import javax.swing.*;
import java.util.List;

public class watchlistThread extends Thread {
    List<Element> elements;
    watchlistTable model;
    public watchlistThread(List<Element> elements, watchlistTable model) {
        this.elements = elements;
        this.model = model;
    }
    @Override
    public void run() {
        while (true) {
            try {
                for (Element element : elements) {
                    element.update();
                }
                SwingUtilities.invokeLater(() ->model.fireTableDataChanged());
                sleep(1000);
            }catch (Exception e) {
                System.out.println(e);
            }

        }
    }
}
