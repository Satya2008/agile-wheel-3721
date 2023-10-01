package com.masai.Dao;

import java.util.List;

import com.masai.EMUtils.Utils;
import com.masai.Entities.Administrator;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class AdministratorDaoImpl implements IAdministratorDao {

	@Override
	public Administrator login(String username, String password) throws SomethingWentWrongException {
	    EntityManager em = Utils.getEntityManager();

	    try {
	        Query query = em.createQuery("SELECT i FROM Administrator i WHERE i.username = :username");
	        query.setParameter("username", username);
	        Administrator administrator = (Administrator) query.getSingleResult();

	        // Check if the entered password matches the stored hashed password
	        if (administrator.checkPassword(password)) {
	            return administrator;
	        } else {
	            throw new SomethingWentWrongException("Invalid username or password");
	        }
	    } catch (NoResultException e) {
	        throw new SomethingWentWrongException("Invalid username or password");
	    } finally {
	        em.close();
	    }
	}


	@Override
	public void addAdminstrator(Administrator administrator) throws SomethingWentWrongException {
		EntityManager em = Utils.getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(administrator);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new SomethingWentWrongException("Failed to register adminstrator");
		} finally {
			em.close();
		}
		
	}

	 @Override
	    public List<Student> getAllStudent() {
	        EntityManager em = Utils.getEntityManager();
	        try {
	            // Create a query to retrieve all students
	            Query query = em.createQuery("SELECT s FROM Student s");
	            List<Student> students = query.getResultList();
	            return students;
	        } finally {
	            em.close();
	        }
	    }

	@Override
	public List<Instructor> getAllInstructor() {
		  EntityManager em = Utils.getEntityManager();
	        try {
	            // Create a query to retrieve all students
	            Query query = em.createQuery("SELECT s FROM Instructor s");
	            List<Instructor> instructors = query.getResultList();
	            return  instructors;
	        } finally {
	            em.close();
	        }
	}

}
