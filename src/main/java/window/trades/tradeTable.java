package window.trades;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class tradeTable extends AbstractTableModel {
    private List<trade> trades;
    private final String[] columnNames = {"Date","Symbol" , "Amount", "Profit","Status"};
    public tradeTable(List<trade> trades) {
        this.trades = trades;
    }

    public trade getElementAt(int index) {
        return trades.get(index);
    }
    @Override
    public int getRowCount() {
        return trades.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        trade trade = trades.get(rowIndex);
        switch (columnIndex) {
            case 0: return trade.getTradeDate();
            case 1: return trade.getSymbol();
            case 2: return trade.getQuantity();
            case 3: return trade.getProfit();
            case 4:
                if(trade.isOpen())
                    return "Open";
                else return "Closed";
            default: return null;
        }
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
