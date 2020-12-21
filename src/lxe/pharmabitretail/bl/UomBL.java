/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.bl;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import lxe.pharmabitretail.entity.Uom;

/**
 *
 * @author JScare
 */
public class UomBL extends DdsBL{

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
            Uom c = em.find(Uom.class, id);
            em.remove(c);
            em.getTransaction().commit();
            return 1;
        } catch (IllegalArgumentException ex) {
            return 0;
        }
    }
    
    
      public List<Uom> getAllUom() {
        TypedQuery<Uom> q = em.createNamedQuery("Uom.findAll", Uom.class);
        return q.getResultList();
    }
       public String getUomById(String uom) {
        try {
            TypedQuery<String> q = em.createQuery("SELECT s.uomDesc FROM Uom s WHERE s.uomDesc = :uomDesc", String.class);
            q.setParameter("uomDesc", uom);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
       

  public List<Uom> searchAllUom(String p) {
        TypedQuery<Uom> q = em.createQuery("SELECT s FROM Uom s WHERE s.uomDesc LIKE :p", Uom.class);
        q.setParameter("p", "%" + p + "%");
        return q.getResultList();
    }  
}
