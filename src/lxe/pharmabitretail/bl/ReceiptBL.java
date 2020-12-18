package lxe.pharmabitretail.bl;

import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import lxe.pharmabitretail.entity.Receipt;

/**
 *
 * @author JScare
 */
public class ReceiptBL extends DdsBL {

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
            Receipt c = em.find(Receipt.class, id);
            em.remove(c);
            em.getTransaction().commit();
            return 1;
        } catch (IllegalArgumentException ex) {
            return 0;
        }

    }

    public List<Receipt> getReciptbySales(Integer salesid) {
        TypedQuery q = em.createQuery("SELECT r FROM Receipt r WHERE r.sales.salesId = :salesid", Receipt.class);
        q.setParameter("salesid", salesid);
        return q.getResultList();

    }

    public Double getTotalPaidbySalesCode(Integer salesid) {
        try {
            TypedQuery<Double> q = em.createQuery("SELECT SUM(r.amountPaid) FROM Receipt r WHERE r.sales.salesId = :salescode", Double.class);
            q.setParameter("salescode", salesid);
            return q.getSingleResult();
        } catch (Exception ex) {
            return 0.00;

        }
    }

    public List getSalesbyReceipt(Integer salesid) {
        try {
            TypedQuery<Integer> q = em.createQuery("SELECT r.sales.salesId FROM Receipt r WHERE r.sales.salesId = :salesid", Integer.class);
            q.setParameter("salesid", salesid);
            return q.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Integer> getReceiptCount() {
        TypedQuery<Integer> q = em.createQuery("SELECT s.receiptId FROM Receipt s ORDER BY s.receiptId DESC", Integer.class);
        q.setMaxResults(1);
        return q.getResultList();

    }

    public double getDailySalesReceipt(Date today) {
        try {
            TypedQuery<Double> q = em.createQuery("SELECT SUM(s.amountPaid) FROM Receipt s WHERE s.date = :date", Double.class);
            q.setParameter("date", today);
            return q.getSingleResult();

        } catch (Exception ex) {
            return 0.0;
        }

    }

    public double getDurationSalesReceipt(Date week, Date today) {
        try {
            TypedQuery<Double> q = em.createQuery("SELECT SUM(s.amountPaid) FROM Receipt s WHERE s.date BETWEEN :week AND :date1", Double.class);
            q.setParameter("week", week);
            q.setParameter("date1", today);
            return q.getSingleResult();

        } catch (Exception ex) {
            return 0.00;
        }

    }

}
