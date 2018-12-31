package com.marketplace.ejb;

import com.marketplace.entity.Images;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ImagesFacade extends AbstractFacade<Images> {

	@PersistenceContext(unitName = "com.mycompany_mavenproject_war_1.0-SNAPSHOTPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ImagesFacade() {
		super(Images.class);
	}
}