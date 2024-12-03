package window.watchlist;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.util.List;


public class watchlistTable extends AbstractTableModel{
    private List<Element> elements;
    private String[] columnames = {"Symbol", "Bid", "Ask","Delete"};

    public watchlistTable(List<Element> elements) {
        this.elements = elements;
    }

    public void addElement(String symbol){
        for(Element element : elements){
            if(element.getSymbol().equals(symbol))
                return;
        }
        
        Element element = new Element(symbol);
        elements.add(element);
        fireTableRowsInserted(elements.size() - 1, elements.size() - 1);
    }
    public void DeleteElement(int index){
        elements.remove(index);
        fireTableRowsDeleted(index, index);
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
            case 1: return element.getBid();
            case 2: return element.getAsk();
            case 3: return "X";
            default: return null;
        }
    }
    @Override
    public String getColumnName(int column) {
        return columnames[column];
    }
    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
    public List<Element> getElements() {
        return elements;
    }
}
