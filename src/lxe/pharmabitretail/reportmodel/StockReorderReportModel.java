package lxe.pharmabitretail.reportmodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import lxe.pharmabitretail.bl.StockinBL;
import lxe.pharmabitretail.entity.Stockin;

/**
 *
 * @author JScare
 */
public class StockReorderReportModel extends AbstractTableModel {

    private Object data[][];
    String[] colnames = {"0", "1", "2", "3", "4"};

    long balv;
    int size = 0;

    StockinBL skb = new StockinBL();
    List<Stockin> sk = skb.getAllStockinGroupBatch();
    List<Stockin> skn = new ArrayList<>();

    public StockReorderReportModel() {
        convertListToReportData();
    }
    

    public int getListSize() {

        sk.forEach(e -> {
            balv = skb.getStockBalance(e.getBatchNo());
            if (balv <= e.getItems().getRol()) {
                skn.add(e);
            }
        });
        return skn.size();
    }

    private void convertListToReportData() {
        size = getListSize();
        data = new Object[size][colnames.length];
        //int x = 0;
//        for (int x = 0; x < sk.size(); x++) {
//            Stockin e = sk.get(x);
//            //balv = skb.getStockBalance(e.getBatchNo());
//            //if (balv <= e.getItems().getRol()) {
//            System.out.println("e1: " + e.getBatchNo());
//        skn.forEach(e -> {
//            int x = 0;
        for (int x = 0; x < skn.size(); x++) {
            Stockin e = skn.get(x);
            //long bal = skb.getStockBalance(e.getBatchNo());
            //System.out.println("e1: " + e.getBatchNo());            
            //System.out.println("e2: " + x + " : "+ e.getBatchNo() + " "+ e.getItems().getItemCodeFullname()+ " "+ skb.getStockBalance(e.getBatchNo()));
            //if (balv <= e.getItems().getRol()) {
            data[x][0] = e.getItems().getItemDesc();
            data[x][1] = e.getBatchNo();
            data[x][2] = e.getItems().getRol();
            data[x][3] = skb.getStockBalance(e.getBatchNo());
            //x++;
            //}
            // }
        }

        //}
    }

    @Override
    public int getRowCount() {
        return this.size;
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
        //System.out.println("ri:"+rowIndex);
        return this.data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        this.fireTableCellUpdated(row, col);
    }
}
