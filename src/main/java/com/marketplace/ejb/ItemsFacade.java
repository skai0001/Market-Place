package com.marketplace.ejb;

import com.marketplace.entity.Items;
import com.marketplace.web.ItemsController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ItemsFacade extends AbstractFacade<Items> {

	@PersistenceContext(unitName = "com.mycompany_mavenproject_war_1.0-SNAPSHOTPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ItemsFacade() {
		super(Items.class);
	}
	
	public int countByCategory(int categoryId) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		javax.persistence.criteria.Root<Items> rt = cq.from(Items.class);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		cq.where(cb.equal(rt.get("category"), ItemsController.CATEGORIES[categoryId]));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}
	
	public List<Items> findWithFilters(int categoryId, int minPrice, int maxPrice) {
		if (categoryId != 0 && (minPrice == 0 && maxPrice == 0)) {
			return em.createNamedQuery("Items.findByCategory", Items.class)
				.setParameter("category", ItemsController.CATEGORIES[categoryId])
				.getResultList();
		}
		else if (categoryId == 0 && (minPrice != 0 || maxPrice != 0)) {
			return em.createNamedQuery("Items.findByPrice", Items.class)
				.setParameter("min", minPrice)
				.setParameter("max", (maxPrice <= 0) ? 1000000 : maxPrice)
				.getResultList();
		}
		else if (categoryId != 0 && (minPrice != 0 || maxPrice != 0)) {
			List<Items> categoryResult = em.createNamedQuery("Items.findByCategory", Items.class)
				.setParameter("category", ItemsController.CATEGORIES[categoryId])
				.getResultList();
			
			List<Items> priceResult = em.createNamedQuery("Items.findByPrice", Items.class)
				.setParameter("min", minPrice)
				.setParameter("max", (maxPrice <= 0) ? 1000000 : maxPrice)
				.getResultList();
			
			List<Items> result = new ArrayList<>();
			
			for (Items iter : categoryResult) {
				if (iter.getPrice() >= minPrice && (maxPrice != 0 && iter.getPrice() <= maxPrice)) {
					if (result.contains(iter) == false) {
						result.add(iter);
					}
				}
			}
			
			for (Items iter : priceResult) {
				if (iter.getCategory().equalsIgnoreCase(ItemsController.CATEGORIES[categoryId]) == true) {
					if (result.contains(iter) == false) {
						result.add(iter);
					}
				}
			}
			
			Collections.sort(result, new Comparator<Items>() {
				@Override
				public int compare(Items a, Items b) {
					return a.getPostInitTimestamp().after(b.getPostInitTimestamp()) ? 1 : 0;
				}
			});
			
			return result;
		}
		
		return findAll();
	}
}