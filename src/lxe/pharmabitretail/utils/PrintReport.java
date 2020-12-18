/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import lxe.pharmabitretail.reportmodel.ItemListReportModel;
import lxe.pharmabitretail.reportmodel.ReceiptReportModel;
import lxe.pharmabitretail.reportmodel.SalesReportModel;
import lxe.pharmabitretail.reportmodel.StockReorderReportModel;
import lxe.pharmabitretail.reportmodel.StockReportModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author JScare
 */
public class PrintReport extends JFrame {

    private static final long serialVersionUID = 1L;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public void showReceiptReport(int id) throws JRException, ClassNotFoundException, SQLException, IOException {

        //String reportSrcFile = "data/Blank_A4.jrxml";
        // First, compile jrxml file.
        InputStream inputStream = getClass().getResourceAsStream("/lxe/pharmabitretail/reports/ReceiptReport.jasper");
        // Fields for report
        Map param = new HashMap();
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/logo.png"));
         BufferedImage imageback = ImageIO.read(getClass().getResourceAsStream("/resources/logoback.png"));
        param.put("LOGO", image);
        param.put("LOGO1", imageback);
        JRTableModelDataSource jrtmds = new JRTableModelDataSource(new ReceiptReportModel(id));
        JasperPrint print = JasperFillManager.fillReport(inputStream, param, jrtmds);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
//        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.add(viewer);
        this.setSize(dim);
        this.setVisible(true);
        System.out.print("Done!");

    }

    public void showSalesDateRangeReport(Date start, Date end) throws JRException, ClassNotFoundException, SQLException, IOException {

        //String reportSrcFile = "data/Blank_A4.jrxml";
        // First, compile jrxml file.
        InputStream inputStream = getClass().getResourceAsStream("/lxe/pharmabitretail/reports/SalesReport.jasper");
        // Fields for report
        Map param = new HashMap();
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/logo.png"));
        param.put("LOGO", image);
        JRTableModelDataSource jrtmds = new JRTableModelDataSource(new SalesReportModel(start, end));
        JasperPrint print = JasperFillManager.fillReport(inputStream, param, jrtmds);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
//        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.add(viewer);
        this.setSize(dim);
        this.setVisible(true);
        System.out.print("Done!");

    }

    public void showSalesUserDateRangeReport(Date start, Date end) throws JRException, ClassNotFoundException, SQLException, IOException {

        //String reportSrcFile = "data/Blank_A4.jrxml";
        // First, compile jrxml file.
        InputStream inputStream = getClass().getResourceAsStream("/lxe/pharmabitretail/reports/SalesRangeReport.jasper");
        // Fields for report
        Map param = new HashMap();
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/logo.png"));
        param.put("LOGO", image);
        JRTableModelDataSource jrtmds = new JRTableModelDataSource(new SalesReportModel(start, end));
        JasperPrint print = JasperFillManager.fillReport(inputStream, param, jrtmds);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
//        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.add(viewer);
        this.setSize(dim);
        this.setVisible(true);
        System.out.print("Done!");

    }

    public void showStockReorderReport() throws JRException, ClassNotFoundException, SQLException, IOException {

        //String reportSrcFile = "data/Blank_A4.jrxml";
        // First, compile jrxml file.
        InputStream inputStream = getClass().getResourceAsStream("/lxe/pharmabitretail/reports/StockReorderLevelReport.jasper");
        // Fields for report
        Map param = new HashMap();
//        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/dodtitle1.png"));
//        BufferedImage image1 = ImageIO.read(getClass().getResourceAsStream("/resources/dodtitle2.png"));
//        param.put("LOGO", image);
//        param.put("LOGO1", image1);
        JRTableModelDataSource jrtmds = new JRTableModelDataSource(new StockReorderReportModel());
        JasperPrint print = JasperFillManager.fillReport(inputStream, param, jrtmds);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
//        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.add(viewer);
        this.setSize(dim);
        this.setVisible(true);
        System.out.print("Done!");

    }
    
    
    
     public void showStocksReport() throws JRException, ClassNotFoundException, SQLException, IOException {

        //String reportSrcFile = "data/Blank_A4.jrxml";
        // First, compile jrxml file.
        InputStream inputStream = getClass().getResourceAsStream("/lxe/pharmabitretail/reports/StockReport.jasper");
        // Fields for report
        Map param = new HashMap();
//        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/dodtitle1.png"));
//        BufferedImage image1 = ImageIO.read(getClass().getResourceAsStream("/resources/dodtitle2.png"));
//        param.put("LOGO", image);
//        param.put("LOGO1", image1);
        JRTableModelDataSource jrtmds = new JRTableModelDataSource(new StockReportModel());
        JasperPrint print = JasperFillManager.fillReport(inputStream, param, jrtmds);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
//        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.add(viewer);
        this.setSize(dim);
        this.setVisible(true);
        System.out.print("Done!");

    }
     public void showItemListReport() throws JRException, ClassNotFoundException, SQLException, IOException {

        //String reportSrcFile = "data/Blank_A4.jrxml";
        // First, compile jrxml file.
        InputStream inputStream = getClass().getResourceAsStream("/lxe/pharmabitretail/reports/ItemListReport.jasper");
        // Fields for report
        Map param = new HashMap();
//        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/resources/dodtitle1.png"));
//        BufferedImage image1 = ImageIO.read(getClass().getResourceAsStream("/resources/dodtitle2.png"));
//        param.put("LOGO", image);
//        param.put("LOGO1", image1);
        JRTableModelDataSource jrtmds = new JRTableModelDataSource(new ItemListReportModel());
        JasperPrint print = JasperFillManager.fillReport(inputStream, param, jrtmds);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
//        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.add(viewer);
        this.setSize(dim);
        this.setVisible(true);
        System.out.print("Done!");

    }

}
