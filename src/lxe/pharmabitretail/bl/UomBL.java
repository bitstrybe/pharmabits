
package lxe.pharmabitretail.bl;

import javax.persistence.TypedQuery;
import lxe.pharmabitretail.entity.UomDef;

/**
 *
 * @author JScare
 */
public class UomBL extends  DdsBL{

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
        em.getTransaction().begin();
        UomDef i = em.find(UomDef.class, id);
        em.remove(i);
        em.getTransaction().commit();
        return 1;
    }
    
    public UomDef getUombyItemId(String val){
        TypedQuery<UomDef> q = em.createQuery("SELECT u FROM UomDef u WHERE u.item.itemDesc = :itemCode", UomDef.class);
        q.setParameter("itemCode", val);
        return q.getSingleResult();
    }
//    public Integer getUomIdbyUom(String val){
//        TypedQuery<Integer> q = em.createQuery("SELECT u.uomItem FROM UomDef u WHERE u.uomCode.uomDesc = :uomCode", Integer.class);
//        q.setParameter("uomCode", val);
//        System.out.println(q.getSingleResult());
//        return q.getSingleResult();
//    }
    
}
