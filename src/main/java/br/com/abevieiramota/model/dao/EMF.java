package br.com.abevieiramota.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
			.createEntityManagerFactory("loteria");
	
	public static EntityManager buildManager() {
		return ENTITY_MANAGER_FACTORY.createEntityManager();
	}

}
