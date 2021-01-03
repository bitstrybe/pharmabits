package lxe.pharmabitretail.bl;

import java.util.List;
import javax.persistence.TypedQuery;
import lxe.pharmabitretail.entity.Items;

/**
 *
 * @author JScare
 */
public class ItemsBL extends DdsBL {

    @Override
    public int insertData(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateData(Object id) {
        try {
            em.getTransaction().begin();
            Items c = em.find(Items.class, id);
            em.remove(c);
            em.getTransaction().commit();
            return 1;
        } catch (IllegalArgumentException ex) {
            return 0;
        }
    }

    @Override
    public int removeData(Object id) {
        em.getTransaction().begin();
        Items i = em.find(Items.class, id);
        em.remove(i);
        em.getTransaction().commit();
        return 1;
    }

    public List<Items> getAllItems() {
        TypedQuery<Items> q = em.createNamedQuery("Items.findAll", Items.class);
        return q.getResultList();
    }

    public List<String> getAllItemsName() {
        TypedQuery<String> q = em.createQuery("SELECT i.itemDesc FROM Items i", String.class);
        return q.getResultList();
    }

    public List<Items> searchAllItems(String p) {
        TypedQuery<Items> q = em.createQuery("SELECT s FROM Items s WHERE s.itemDesc LIKE :p OR s.itemName LIKE :p1 OR s.category.categoryName LIKE :p2", Items.class);
        q.setParameter("p", "%" + p + "%");
        q.setParameter("p1", "%" + p + "%");
        q.setParameter("p2", "%" + p + "%");
        return q.getResultList();
    }

    public List<String> searchItemsNames(String param) {
        TypedQuery<String> q = em.createQuery("SELECT i.itemDesc FROM Items i WHERE i.itemName LIKE :p1 OR i.itemDesc LIKE :p2", String.class);
        q.setParameter("p1", "%" + param + "%");
        q.setParameter("p2", "%" + param + "%");
        return q.getResultList();
    }

    public List getItemsFromCategory(String catid) {
        try {
            TypedQuery<String> q = em.createQuery("SELECT r.category.categoryName FROM Items r WHERE r.category.categoryName = :catid", String.class);
            q.setParameter("catid", catid);
//        System.out.println(q.getResultList());
            q.setMaxResults(1);
            return q.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List getItemsFromManufacturer(String manid) {
        try {
            TypedQuery<String> q = em.createQuery("SELECT r.manufacturer.manufacturer FROM Items r WHERE r.manufacturer.manufacturer = :manid", String.class);
            q.setParameter("manid", manid);
//        System.out.println(q.getResultList());
            q.setMaxResults(1);
            return q.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Items getImageItembyCode(String val) {
        TypedQuery<Items> q = em.createQuery("SELECT i FROM Items i WHERE i.itemDesc = :itemDesc", Items.class);
        q.setParameter("itemDesc", val);
        return q.getSingleResult();
    }

}
