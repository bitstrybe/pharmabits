/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lxe.pharmabitretail.bl;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author JScare
 */
public abstract class DdsBL {

    EntityManager em;

    public DdsBL() {
        //Map connect = new HashMap<>();
        //connect.put("javax.persistence.jdbc.user", "root");
        //connect.put("javax.persistence.jdbc.password", "1234");
        em = Persistence.createEntityManagerFactory("PharmabitRetailPU").createEntityManager();
    }

    abstract public int insertData(Object object);

    abstract public int updateData(Object object);

    abstract public int removeData(Object id);
}
