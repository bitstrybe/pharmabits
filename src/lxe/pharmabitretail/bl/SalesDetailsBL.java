/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.bl;

import java.util.List;
import javax.persistence.TypedQuery;
import lxe.pharmabitretail.entity.SalesDetails;

/**
 *
 * @author JScare
 */
public class SalesDetailsBL extends DdsBL {

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
            SalesDetails c = em.find(SalesDetails.class, id);
            em.refresh(c);
            em.remove(c);
            em.getTransaction().commit();
            em.clear();
            return 1;
        } catch (IllegalArgumentException ex) {
            return 0;
        }
    }

    public List getStockinFromSalesDetails(String batchno) {
        TypedQuery<String> q = em.createQuery("SELECT s.batchNo FROM SalesDetails s WHERE s.batchNo = :batchno", String.class);
        q.setParameter("batchno", batchno);
        q.setMaxResults(1);
        return q.getResultList();
    }

}
