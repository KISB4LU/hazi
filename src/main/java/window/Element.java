package window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Element extends JPanel {
    private String symbol;
    private JLabel bid;
    private JLabel ask;
    private JPanel up;
    private JPanel BuySel;
    public Element(String symbol,ArrayList<Element>  others) {
        this.symbol = symbol;

        up = new JPanel();
        bid = new JLabel("123");
        ask = new JLabel("321");

        BuySel = new JPanel();
        JButton BUY =  new JButton("BUY");
        JButton SELL = new JButton("SELL");

        up.add(new JLabel(symbol));
        up.add(bid);
        up.add(ask);
        //up.setLayout(new GridLayout(1, 3));

        BuySel.add(BUY);
        BuySel.add(SELL);
        BuySel.setVisible(false);
        setLayout(new BorderLayout());
        add(up, BorderLayout.NORTH);
        add(BuySel, BorderLayout.CENTER);
        up.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                for(Element i : others){
                    i.getBuySel().setVisible(false);
                }
                BuySel.setVisible(true);
            }
        });
    }
    public JPanel getBuySel() {
        return BuySel;
    }
}
