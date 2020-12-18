
package lxe.pharmabitretail.reportmodel;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import lxe.pharmabitretail.bl.ReceiptBL;
import lxe.pharmabitretail.bl.SalesBL;
import lxe.pharmabitretail.bl.StockinBL;
import lxe.pharmabitretail.entity.Sales;
import lxe.pharmabitretail.entity.SalesDetails;
import lxe.pharmabitretail.entity.Stockin;
import lxe.pharmabitretail.utils.Utilities;
import lxe.utility.date.DateUtil;

/**
 *
 * @author JScare
 */
public class ReceiptReportModel extends AbstractTableModel {

    private Object data[][];
    String[] colnames = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13","14"};
    List<SalesDetails> s;

    public ReceiptReportModel(int salesid) {
        s = new SalesBL().getAllSalesDetailsbySalesCode(salesid);
        convertListToReportData();
    }

    private void convertListToReportData() {
        data = new Object[s.size()][colnames.length];
        for (int x = 0; x < s.size(); x++) {
            SalesBL sb = new SalesBL();
            SalesDetails c = s.get(x);
            String salescode = "DOD/00" + c.getSaleId().getSalesId();
            data[x][0] = salescode;
            data[x][1] = c.getBatchNo();
            data[x][2] = c.getQuantity();
            data[x][3] = Utilities.roundToTwoDecimalPlace(c.getSalesPrice(), 2);
            Sales sl = sb.getAllSalesbySalesCode(c.getSaleId().getSalesId());
//            String cus = new CustomerBL().getCustomerNamebyId(c.getSaleId().getCustomer().getCustomerId());
//            data[x][4] = null;
            double totalsalestb;
            double totalpv = c.getQuantity() * c.getSalesPrice();
            double totalnhis = c.getQuantity() * c.getNhisPrice();

            if (c.getNhisCondition()==1) {
                double nhistotal = totalpv - totalnhis;
                totalsalestb = (nhistotal) - (nhistotal * (c.getDiscount() / 100));
            } else {
                totalsalestb = (totalpv) - (totalpv * (c.getDiscount() / 100));
            }
            data[x][5] = Utilities.roundToTwoDecimalPlace(totalsalestb, 2);
            double totoalsalesnonhis;
            try {
                totoalsalesnonhis = Utilities.roundToTwoDecimalPlace(sb.getTotalSalesNoNHIS(c.getSaleId().getSalesId()), 2);
            } catch (Exception ex) {
                totoalsalesnonhis = 0.0;
            }
            double totoalsalesnhis;
            try {
                totoalsalesnhis = Utilities.roundToTwoDecimalPlace(sb.getTotalSalesNHIS(c.getSaleId().getSalesId()), 2);
            } catch (Exception ex) {
                totoalsalesnhis = 0.0;
            }
            double totalsales = totoalsalesnonhis + totoalsalesnhis;

            data[x][6] = Utilities.roundToTwoDecimalPlace(totalsales, 2);
            List<Stockin> sk = new StockinBL().getItemStockinBatchNo(c.getBatchNo());
            data[x][7] = sk.get(0).getItems().getItemDesc().toUpperCase();
            double totalpaid;
            double bal;
            try {
                totalpaid = new ReceiptBL().getTotalPaidbySalesCode(c.getSaleId().getSalesId());
                bal = totalpaid - totalsales;
                data[x][8] = totalpaid;
                data[x][9] = Utilities.roundToTwoDecimalPlace(bal, 2);
            } catch (NullPointerException ex) {
                data[x][8] = totalpaid = 0.00;
                data[x][9] = totalpaid - totalsales;
            }
            data[x][10] = Utilities.convertDigitToCurrency(new DecimalFormat("0.00").format(totalpaid));
            data[x][11] = DateUtil.format2(sl.getDateS());
            String nhisstatus;
            if (c.getNhisCondition() == 1) {
                nhisstatus = "Yes";
            } else {
                nhisstatus = "No";
            }
            data[x][12] = nhisstatus;
            data[x][13] = c.getNhisPrice();
            data[x][14] = c.getDiscount();

//            System.out.println("Balance:" + bal);
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
