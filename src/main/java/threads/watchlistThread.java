package threads;

import window.watchlist.Element;
import window.watchlist.watchlistTable;

import javax.swing.*;
import java.util.List;

/**
 * frissti a watchlist-ben szerplő részvények aktuális árát
 */
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
                    //System.out.println(element);
                }
                SwingUtilities.invokeLater(() ->model.fireTableDataChanged());
                sleep(1000);
                //System.out.println("ok");
            }catch (Exception e) {
                System.out.println(e);
            }

        }
    }
}
