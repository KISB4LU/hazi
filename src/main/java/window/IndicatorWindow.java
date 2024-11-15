package window;

import indicators.MovingAvrage;
import indicators.indicator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IndicatorWindow extends JFrame {
    private String[] Types = {"Simple Moving Avrage", "Bollinger Bands", "RSI"};
    private indicator tmp = null;
    public IndicatorWindow(ArrayList<indicator> indicators, Runnable refresh) {
        super("indicators");
        setSize(500, 300);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel buttons = new JPanel();
        JList types = new JList();
        JList curr = new JList(indicators.toArray());

        JButton apply = new JButton("Apply");
        JButton cancel = new JButton("Cancel");
        JButton delete = new JButton("Delete");
        buttons.add(apply);
        buttons.add(cancel);
        buttons.add(delete);

        leftPanel.add(new JScrollPane(types));
        leftPanel.add(new JScrollPane(curr));

        for(indicator i : indicators) {
            rightPanel.add(i.getPanel());
        }
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        getContentPane().add(splitPane);
        add(buttons, BorderLayout.SOUTH);

        apply.addActionListener(e -> {
            if(tmp != null) {
                indicators.add(tmp);
                refresh.run();
            }
        });

        delete.addActionListener(e -> {
            if(tmp != null) {
                curr.remove(indicators.indexOf(tmp));
                indicators.remove(tmp);
                tmp = null;
                refresh.run();
            }
        });

        cancel.addActionListener(e -> {
            tmp = null;
            for(indicator i : indicators) {
                i.getPanel().setVisible(false);
            }
        });

        curr.addListSelectionListener(e -> {
            int idx = curr.getSelectedIndex();
            for(indicator i : indicators) {
                i.getPanel().setVisible(false);
            }
            try {
                indicators.get(idx).getPanel().setVisible(true);
                tmp = indicators.get(idx);
            }
            catch(Exception ex) {
                indicators.add(tmp);
            }

        });

        types.addListSelectionListener(e -> {
            int idx = types.getSelectedIndex();
            switch (idx){
                case 0:
                    tmp = new MovingAvrage();
                    rightPanel.add(tmp.getPanel());
                    tmp.getPanel().setVisible(true);
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        });
    }
}
