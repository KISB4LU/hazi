package window;

import indicators.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * az indikátorok hozzzáadásáért és törléséért felel
 */
public class IndicatorWindow extends JFrame {
    private String[] Types = {"Simple Moving Avrage"," Exponencial Moving Avrage", "Bollinger Bands", "Parabolic Sar", "ichimoku"};
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
                if(!addNewIndicator) {
                    tmp.save();
                    listModel.addElement(tmp.toString());
                    indicators.add(tmp);
                    refresh.run();
                    addNewIndicator = true;
                }else{
                    listModel.removeElement(tmp.toString());
                    tmp.save();
                    listModel.addElement(tmp.toString());
                }

                tmp.getPanel().setVisible(false);
                tmp = null;
            }
        });

        delete.addActionListener(e -> {
            if(tmp != null) {
                addNewIndicator = true;
                tmp.getPanel().setVisible(false);
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
        //curr.clearSelection();
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
                    if(addNewIndicator) {
                        tmp = new ExpMovingAvrage();
                        rightPanel.add(tmp.getPanel());
                        tmp.getPanel().setVisible(true);
                        addNewIndicator = false;
                    }
                    break;
                case 2:
                    if(addNewIndicator) {
                        tmp = new BollingerBands();
                        rightPanel.add(tmp.getPanel());
                        tmp.getPanel().setVisible(true);
                        addNewIndicator = false;
                    }
                    break;
                case 3:
                    if(addNewIndicator) {
                        tmp = new ParabolicSAR();
                        rightPanel.add(tmp.getPanel());
                        tmp.getPanel().setVisible(true);
                        addNewIndicator = false;
                    }
                    break;
                case 4:
                    if(addNewIndicator) {
                        tmp = new Ichimoku();
                        rightPanel.add(tmp.getPanel());
                        tmp.getPanel().setVisible(true);
                        addNewIndicator = false;
                    }
                    break;
            }
        });
    }
}
