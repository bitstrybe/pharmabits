/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.bl;

import javax.persistence.RollbackException;

/**
 *
 * @author JScare
 */
public class InsertUpdateBL extends DdsBL {

    @Override
    public int insertData(Object object) {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            em.refresh(object);
            return 1;
        }catch (RollbackException ex) {
           return 0;
        }

    }

    @Override
    public int updateData(Object object) {
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
        return 1;
    }

    @Override
    public int removeData(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
