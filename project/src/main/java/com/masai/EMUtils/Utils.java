package com.masai.EMUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Utils {
    
	static  EntityManagerFactory emf =null;
	
	
	static  {
		emf = Persistence.createEntityManagerFactory("project");
	
	}
	static public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
