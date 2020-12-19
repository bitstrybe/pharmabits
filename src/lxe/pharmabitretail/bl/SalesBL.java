/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.bl;

import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import lxe.pharmabitretail.entity.Sales;
import lxe.pharmabitretail.entity.SalesDetails;

/**
 *
 * @author JScare
 */
public class SalesBL extends DdsBL {

    @Override
    public int insertData(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateData(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int removeData(Object id) {
        try {
            em.getTransaction().begin();
            Sales c = em.find(Sales.class, id);
            em.refresh(c);
            em.remove(c);
            em.getTransaction().commit();
            em.clear();
            return 1;
        } catch (IllegalArgumentException ex) {
            return 0;
        }
    }

    public List<Sales> getAllSales() {
        TypedQuery q = em.createNamedQuery("Sales.findAll", Sales.class);
        return q.getResultList();

    }

    public List<SalesDetails> getAllSalesDetails() {
        TypedQuery q = em.createNamedQuery("SalesDetails.findAll", SalesDetails.class);
        return q.getResultList();

    }

    public List<SalesDetails> getAllSalesDetailsbySalesCode(int salesCode) {
        TypedQuery q = em.createQuery("SELECT s FROM SalesDetails s WHERE s.sales.salesId = :salesid", SalesDetails.class);
        q.setParameter("salesid", salesCode);
        return q.getResultList();

    }

    public Sales getAllSalesbySalesCode(int salesCode) {
        TypedQuery<Sales> q = em.createQuery("SELECT s FROM Sales s WHERE s.salesId = :salesid", Sales.class);
        q.setParameter("salesid", salesCode);
        return q.getSingleResult();
    }

    public Double getTotalCost(Integer salesid) {
        TypedQuery<Double> q = em.createQuery("SELECT SUM(s.quantity * s.costPrice) FROM SalesDetails s WHERE s.sales.salesId = :salesid", Double.class);
        q.setParameter("salesid", salesid);
        return q.getSingleResult();
    }
//     public Double getTotalSales(Integer salesid){
//        TypedQuery<Double> q = em.createQuery("SELECT SUM(s.quantity * s.salesPrice) FROM SalesDetails s WHERE s.saleId.salesId = :salesid",Double.class);
//        q.setParameter("salesid", salesid);
//        return q.getSingleResult();
//    }

    public Double getTotalSalesNoNHIS(Integer salesid) {
        try {
            TypedQuery<Double> q = em.createQuery("SELECT SUM ((s.quantity * s.salesPrice) - (s.quantity * s.salesPrice * s.discount /100)) FROM SalesDetails s WHERE s.sales.salesId = :salesid  AND s.nhisCondition = false", Double.class);
            q.setParameter("salesid", salesid);
            return q.getSingleResult();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public Double getTotalSalesNHIS(Integer salesid) {
        try {
            TypedQuery<Double> q = em.createQuery("SELECT SUM(((s.salesPrice - s.nhisPrice) * s.quantity) - (((s.salesPrice - s.nhisPrice) * s.quantity) * s.discount /100)) FROM SalesDetails s WHERE s.sales.salesId = :salesid AND s.nhisCondition = true", Double.class);
            q.setParameter("salesid", salesid);
            return q.getSingleResult();
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public List<Sales> getSalesDateRange(Date startdate, Date enddate) {
        TypedQuery q = em.createQuery("SELECT s FROM Sales s WHERE s.dateS BETWEEN :date1 AND :date2", Sales.class);
        q.setParameter("date1", startdate);
        q.setParameter("date2", enddate);
        q.setHint("org.hibernate.cacheMode", "IGNORE");
        return q.getResultList();
    }

    public List<Sales> getCustomerFullnamebysalescode(String cusid) {
        TypedQuery<Sales> q = em.createQuery("SELECT s FROM Sales s WHERE s.customer.customerId = :cusid", Sales.class);
        q.setParameter("cusid", cusid);
        return q.getResultList();
    }
    public List<Integer> getSalesCount(){
        TypedQuery<Integer> q = em.createQuery("SELECT s.salesId FROM Sales s ORDER BY s.salesId DESC", Integer.class);
        q.setMaxResults(1);
        return q.getResultList();
    }
//     public List<Integer> getSalesDetailsCount(){
//        TypedQuery<Integer> q = em.createQuery("SELECT s.salesDetailsId FROM SalesDetails s ORDER BY s.salesDetailsId DESC", Integer.class);
//        q.setMaxResults(1);
//        return q.getResultList();
//        
//    }

    public List<SalesDetails> getSalesDetailsbySalesId(int salesid){
        TypedQuery q = em.createQuery("SELECT s FROM SalesDetails s WHERE s.sales.salesId = :salesid", List.class);
        q.setParameter("salesid", salesid);
        return q.getResultList();
    }
//    public long getDailySales(Date today){
//        TypedQuery<Long> q = em.createQuery("SELECT COUNT(s) FROM Sales s WHERE s.date = :date", Long.class);
//        q.setParameter("date", today);
//        return q.getSingleResult();
//    }
//    public long getDurationSales(Date week, Date today){
//        TypedQuery<Long> q = em.createQuery("SELECT COUNT(s) FROM Sales s WHERE s.date BETWEEN :week AND :date1", Long.class);
//        q.setParameter("week", week);
//        q.setParameter("date1", today);
//        return q.getSingleResult();
//    }
    
}
