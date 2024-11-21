package window.trades;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class tradeTable extends AbstractTableModel {
    private List<trade> trades;
    private String[] columnNames = {"Date","Symbol" , "Amount", "Profit"};
    public tradeTable(List<trade> trades) {
        this.trades = trades;
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
            default: return null;
        }
    }
}
