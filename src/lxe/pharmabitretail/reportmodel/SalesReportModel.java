/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.reportmodel;

import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import lxe.pharmabitretail.bl.ReceiptBL;
import lxe.pharmabitretail.bl.SalesBL;
import lxe.pharmabitretail.entity.Sales;
import lxe.pharmabitretail.utils.Utilities;

/**
 *
 * @author JScare
 */
public class SalesReportModel extends AbstractTableModel {

    private Object data[][];
    String[] colnames = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    List<Sales> s;

    public SalesReportModel(Date start, Date end) {
        s = new SalesBL().getSalesDateRange(start, end);
        convertListToReportData(start,end);
    }

    private void convertListToReportData(Date start, Date end) {
        data = new Object[s.size()][colnames.length];
        for (int x = 0; x < s.size(); x++) {
            SalesBL sb = new SalesBL();
            Sales c = s.get(x);
            String salescode = "DOD/00" + c.getSalesId();
            data[x][0] = salescode;
//            String cus = new CustomerBL().getCustomerNamebyId(c.getCustomer().getCustomerId());
//            data[x][1] = cus;
            data[x][2] = Utilities.convertDateToString(start);
            data[x][3] = Utilities.convertDateToString(end);
            double totalcost = Utilities.roundToTwoDecimalPlace(sb.getTotalCost(c.getSalesId()), 2);
            data[x][4] = totalcost;
            double totoalsalesnonhis;
            try {
                totoalsalesnonhis = Utilities.roundToTwoDecimalPlace(sb.getTotalSalesNoNHIS(c.getSalesId()), 2);
            } catch (Exception ex) {
                totoalsalesnonhis = 0.0;
            }
            double totoalsalesnhis;
            try {
                totoalsalesnhis = Utilities.roundToTwoDecimalPlace(sb.getTotalSalesNHIS(c.getSalesId()), 2);
            } catch (Exception ex) {
                totoalsalesnhis = 0.0;
            }
            double totalsales = totoalsalesnonhis + totoalsalesnhis;
            
            data[x][5] = totalsales;
             double totalpaid;
            try {
                totalpaid = Utilities.roundToTwoDecimalPlace(new ReceiptBL().getTotalPaidbySalesCode(c.getSalesId()), 2);
//                System.out.println(totalpaid);
            } catch (Exception ex) {
                totalpaid = 0;
            }
            data[x][6] = totalpaid;
             double balance = totalsales - totalpaid;
            String bal;
            if (balance <= 0) {
                bal = "Paid✔";
            } else {
                bal = String.valueOf(Utilities.roundToTwoDecimalPlace(balance, 2));
            }
            data[x][7] = bal;
            double profit = totalpaid - totalcost;
            data[x][8] = Utilities.roundToTwoDecimalPlace(profit, 2);
////            Sales sl = sb.getAllSalesbySalesCode(c.getSaleId().getSalesId());
////            data[x][4] = sl.getCustomer().getFName() + " " + sl.getCustomer().getLName();
////            data[x][5] = df.format(c.getQuantity() * c.getSalesPrice());
////            double totoalsalesnonhis;
////            try {
////                totoalsalesnonhis = Double.parseDouble(df.format(sb.getTotalSalesNoNHIS(c.getSaleId().getSalesId())));
////            } catch (Exception ex) {
////                totoalsalesnonhis = 0.0;
////            }
////            double totoalsalesnhis;
////            try {
////                totoalsalesnhis = Double.parseDouble(df.format(sb.getTotalSalesNHIS(c.getSaleId().getSalesId())));
////            } catch (Exception ex) {
////                totoalsalesnhis = 0.0;
////            }
////            double totalsales = totoalsalesnonhis + totoalsalesnhis;
////            data[x][6] = Double.parseDouble(df.format(totalsales));
////            List<Stockin> sk = new StockinBL().getItemStockinBatchNo(c.getBatchNo());
////            data[x][7] = sk.get(0).getItems().getItemCodeFullname();
////            double totalpaid;
////            double bal;
////            try {
////                totalpaid = new ReceiptBL().getTotalPaidbySalesCode(c.getSaleId().getSalesId());
////                bal = totalpaid - totalsales;
////                data[x][8] = totalpaid;
////                data[x][9] = Double.parseDouble(df.format(bal));
////            } catch (NullPointerException ex) {
////                data[x][8] = totalpaid = 0.00;
////                data[x][9] = totalpaid - totalsales;
////            }
////            data[x][10] = Utilities.convertDigitToCurrency(df.format(totalpaid));
////            data[x][11] = DateUtil.format2(sl.getDate());
////
//////            System.out.println("Balance:" + bal);
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
