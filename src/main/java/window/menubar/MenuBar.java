package window.menubar;

import org.jdatepicker.impl.JDatePickerImpl;
import window.login.User;

import javax.swing.*;
import java.util.Calendar;

/**
 * menu
 */
public class MenuBar extends JPanel {
    private Profile profile;
    private JButton line;
    private JButton candle;
    private JButton indicators;
    private DatePicker start;
    private DatePicker end;
    private  JComboBox  TimeFrameBox;
    public MenuBar() {
        String TimeFrame[] = {"1Min", "5Min", "15Min", "30Min", "1Hour","2Hour", "4hour", "1Day", "1Week"};

        profile = new Profile();
        line = new JButton("Line");
        candle = new JButton("Candle");
        indicators = new JButton("Indicators");
        TimeFrameBox = new JComboBox(TimeFrame);
        Calendar Start = Calendar.getInstance();
        Start.set(Calendar.DAY_OF_MONTH, Start.get(Calendar.DAY_OF_MONTH)-1);
        start = new DatePicker("start: ", Start);
        end = new DatePicker("end: ",Calendar.getInstance());

        add(profile);
        add(line);
        add(candle);
        add(indicators);
        add(start);
        add(end);
        add(TimeFrameBox);
    }
    public void updateProfile(){
        profile.update();
    }
    public JButton getLine() {
        return line;
    }
    public JButton getCandle() {
        return candle;
    }
    public JButton getIndicators() {
        return indicators;
    }
    public JDatePickerImpl getStart() {
        return start.getDatePicker();
    }
    public JDatePickerImpl getEnd() {
        return end.getDatePicker();
    }
    public String getTimeFrame() {
        return TimeFrameBox.getSelectedItem().toString();
    }
    public JComboBox getTimeFrameBox() {
        return TimeFrameBox;
    }

    public void setProfile(User user) {
        profile.setUser(user);
    }
}