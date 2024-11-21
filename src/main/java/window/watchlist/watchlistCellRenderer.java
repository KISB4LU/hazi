package window.watchlist;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class watchlistCellRenderer implements TableCellRenderer {
    private TableCellRenderer renderer;
    public watchlistCellRenderer(TableCellRenderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column){
        Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        int modelRow = table.convertRowIndexToModel(row);
        watchlistTable model = (watchlistTable) table.getModel();
        Element element = model.getElementAt(modelRow);

        if (column == 1) {
            component.setForeground(element.getAskColor());
        } else if (column == 2) {
            component.setForeground(element.getBidColor());
        } else {
            component.setForeground(Color.black);
        }

        return component;
    }
}
