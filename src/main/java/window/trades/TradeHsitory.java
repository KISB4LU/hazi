package window.trades;

import threads.tradeThread;
import window.login.User;

import javax.swing.*;

public class TradeHsitory extends JPanel {
    private User user;
    private JTable table;
    public void SetUser(User user) {
        this.user = user;
        tradeTable model = new tradeTable(user.getTrades());
        table = new JTable(model);
        tradeThread thread = new tradeThread(user.getTrades(), model);
        thread.start();
        add(table);
    }
}
