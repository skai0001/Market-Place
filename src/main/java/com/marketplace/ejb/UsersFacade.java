package com.marketplace.ejb;

import com.marketplace.entity.Users;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UsersFacade extends AbstractFacade<Users> {
    @PersistenceContext(unitName = "com.mycompany_mavenproject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
	
	public List<Users> findEmail(String email){
		return em.createNamedQuery("Users.findByEmail", Users.class)
				.setParameter("email", email)
				.getResultList();
	}
	
	public List<Users> findRequestKey(String key){
		return em.createNamedQuery("Users.findByRequestKey", Users.class)
				.setParameter("key", key)
				.getResultList();
	}
}