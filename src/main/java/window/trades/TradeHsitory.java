package window.trades;

import threads.tradeThread;
import window.login.User;

import javax.swing.*;
import java.awt.*;

/**
 * kilistázza az összes eladott részvényt
 */
public class TradeHsitory extends JPanel {
    private User user;
    private JTable table;
    private tradeTable model;
    public void SetUser(User user) {
        this.user = user;
        model = new tradeTable(user.getTrades());
        table = new JTable(model);
        tradeThread thread = new tradeThread(user.getTrades(), model);
        thread.start();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 100));
        add(scrollPane);
        //pack();
    }
    public JTable table() {
        return table;
    }
    public tradeTable model() {
        return model;
    }
}
