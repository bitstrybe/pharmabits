/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.bl;

import java.util.List;
import javax.persistence.TypedQuery;
import lxe.pharmabitretail.entity.Stockout;

/**
 *
 * @author JScare
 */
public class StockoutBL extends DdsBL{

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
            Stockout c = em.find(Stockout.class, id);
            em.remove(c);
            em.getTransaction().commit();
            return 1;
        } catch (IllegalArgumentException ex) {
            return 0;
        }
    }
    
    public List<Stockout> getAllStockout(){
        TypedQuery<Stockout> q = em.createNamedQuery("Stockout.findAll",Stockout.class);
        return q.getResultList();
    }
    public List<Stockout> getAllStockoutbyBatchNo(String batchNo){
        TypedQuery<Stockout> q = em.createNamedQuery("Stockout.findByBatchNo",Stockout.class);
        q.setParameter("batchNo", batchNo);
        return q.getResultList();
    }
    
    
    public Long getStockoutqty(String batchno){
        try{
        TypedQuery<Long> q = em.createQuery("SELECT SUM(s.quantity) FROM Stockout s WHERE s.batchNo = :batchno", Long.class);
        q.setParameter("batchno", batchno);
        return q.getSingleResult();
        }catch(Exception ex){
            return 0l;
        }
    }
    
    
}
