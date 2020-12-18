
package lxe.pharmabitretail.reportmodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import lxe.pharmabitretail.bl.ItemsBL;
import lxe.pharmabitretail.entity.Items;

/**
 *
 * @author JScare
 */
public class ItemListReportModel extends AbstractTableModel {

    private Object data[][];
    String[] colnames = {"0", "1", "2", "3"};
    List<Items> s;

    public ItemListReportModel() {
        s = new ItemsBL().getAllItems();
        convertListToReportData();
    }

    private void convertListToReportData() {
        data = new Object[s.size()][colnames.length];
        for (int x = 0; x < s.size(); x++) {
            Items c = s.get(x);
            data[x][0] = c.getItemName();
            data[x][1] = c.getCategory().getCategoryName();
            String umo = c.getVomDef()+ " " + c.getVom();
            data[x][2] = umo;
            data[x][3] = c.getManufacturer().getManufacturer();
        }
    }

    @Override
    public int getRowCount() {
        return this.s.size();
    }

    @Override
    public int getColumnCount() {
        return this.colnames.length;
    }

    @Override
    public String getColumnName(int col) {
        return colnames[col];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        this.fireTableCellUpdated(row, col);
    }
}
