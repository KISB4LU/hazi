package window.watchlist;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.util.List;


public class watchlistTable extends AbstractTableModel{
    private List<Element> elements;
    private String[] columnames = {"Symbol", "Ask", "Bid"};

    public watchlistTable(List<Element> elements) {
        this.elements = elements;
    }

    public void addElement(String symbol){
        Element element = new Element(symbol);
        elements.add(element);
    }
    public Element getElementAt(int row) {
        return elements.get(row);
    }
    @Override
    public int getRowCount() {
        return elements.size();
    }
    @Override
    public int getColumnCount() {
        return columnames.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Element element = elements.get(rowIndex);
        switch (columnIndex) {
            case 0: return element.getSymbol();
            case 1: return element.getAsk();
            case 2: return element.getBid();
            default: return null;
        }
    }
    @Override
    public String getColumnName(int column) {
        return columnames[column];
    }

}
