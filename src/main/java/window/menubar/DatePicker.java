package window.menubar;

import javax.swing.*;
import org.jdatepicker.impl.*;

import java.util.Calendar;
import java.util.Properties;

/**
 * dátumválasztó
 */
public class DatePicker extends JPanel {
    private JDatePickerImpl datePicker;
    public DatePicker(String title, Calendar Date) {
        UtilDateModel model = new UtilDateModel();

        model.setDate(Date.get(Calendar.YEAR), Date.get(Calendar.MONTH), Date.get(Calendar.DAY_OF_MONTH));
        model.setSelected(true);

        Properties p = new Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        add(new JLabel(title));
        add(datePicker);
    }
    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
