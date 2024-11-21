package window;

import indicators.MovingAvrage;
import indicators.indicator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IndicatorWindow extends JFrame {
    private String[] Types = {"Simple Moving Avrage", "Bollinger Bands", "RSI"};
    private indicator tmp = null;
    private DefaultListModel<String> listModel;
    private boolean addNewIndicator;
    public IndicatorWindow(ArrayList<indicator> indicators, Runnable refresh) {
        super("indicators");
        addNewIndicator = true;
        setSize(700, 500);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel buttons = new JPanel();
        JList types = new JList(Types);
        listModel = new DefaultListModel();
        JList curr = new JList(listModel);

        JButton apply = new JButton("Apply");
        JButton cancel = new JButton("Cancel");
        JButton delete = new JButton("Delete");
        buttons.add(apply);
        buttons.add(cancel);
        buttons.add(delete);

        leftPanel.add(new JScrollPane(types));
        leftPanel.add(new JScrollPane(curr));

        for(indicator i : indicators) {
            if(i != null) {
                listModel.addElement(i.toString());
                rightPanel.add(i.getPanel());
            }
        }
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        getContentPane().add(splitPane);
        add(buttons, BorderLayout.SOUTH);

        apply.addActionListener(e -> {
            if(tmp != null) {
                tmp.save();
                listModel.addElement(tmp.toString());
                indicators.add(tmp);
                refresh.run();
                addNewIndicator = true;
            }
        });

        delete.addActionListener(e -> {
            if(tmp != null) {
                addNewIndicator = true;
                indicators.remove(tmp);
                listModel.removeElement(tmp.toString());
                tmp = null;
                refresh.run();
                for(indicator i : indicators) {
                    if(i != null)
                        i.getPanel().setVisible(false);
                }
            }
        });

        cancel.addActionListener(e -> {
            tmp = null;
            addNewIndicator = true;
            for(indicator i : indicators) {
                if(i != null)
                    i.getPanel().setVisible(false);
            }
        });

        curr.addListSelectionListener(e -> {
            int idx = curr.getSelectedIndex();
            for(indicator i : indicators) {
                if(i != null)
                    i.getPanel().setVisible(false);
            }
            try {
                indicators.get(idx).getPanel().setVisible(true);
                tmp = indicators.get(idx);
            }
            catch(Exception ex) {
                //indicators.add(tmp);
                //System.out.println(ex.getMessage());
            }

        });

        types.addListSelectionListener(e -> {
            int idx = types.getSelectedIndex();
            for(indicator i : indicators) {
                if (i != null)
                    i.getPanel().setVisible(false);
            }
            switch (idx){
                case 0:
                    if(addNewIndicator) {
                        tmp = new MovingAvrage();
                        rightPanel.add(tmp.getPanel());
                        tmp.getPanel().setVisible(true);
                        addNewIndicator = false;
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        });
    }
}
