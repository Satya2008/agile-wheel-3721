package com.masai.Dao;

import java.util.ArrayList;
import java.util.List;

import com.masai.EMUtils.Utils;
import com.masai.Entities.Assignment;
import com.masai.Entities.Course;
import com.masai.Entities.Discussion;
import com.masai.Entities.Grade;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class InstructorDaoImpl implements IInstructorDao {

	 @Override
	    public void register(Instructor instructor) throws SomethingWentWrongException {
	        EntityManager em = Utils.getEntityManager();

	        try {
	            em.getTransaction().begin();
	            em.persist(instructor);
	            em.getTransaction().commit();
	        } catch (PersistenceException e) {
	            em.getTransaction().rollback();
	            throw new SomethingWentWrongException("Failed to register instructor");
	        } finally {
	            em.close();
	        }
	    }

	  @Override
	    public Instructor login(String username, String password) throws NoRecordFoundException {
	        EntityManager em = Utils.getEntityManager();

	        try {
	            Query query = em.createQuery("SELECT i FROM Instructor i WHERE i.username = :username AND i.password = :password");
	            query.setParameter("username", username);
	            query.setParameter("password", password);
	            return (Instructor) query.getSingleResult();
	        } catch (NoResultException e) {
	            throw new NoRecordFoundException("Invalid username or password");
	        } finally {
	            em.close();
	        }
	    }


	  @Override
	    public List<Course> getCoursesByInstructor(Instructor instructor) {
	        EntityManager em = Utils.getEntityManager();
        List<Course> list = new ArrayList<>();
	        try {
	          Query query = em.createQuery("SELECT c FROM Course c WHERE c.instructor = :instructor");
	            query.setParameter("instructor", instructor);
	            list =  query.getResultList();
	        } catch(PersistenceException e) {
	        	e.getMessage();
	        }
	        finally {
	            em.close();
	        }
			return list;
	    }
	  public Instructor getInstructorByName(String instructorName) {
	        // Implement the logic to retrieve the instructor by name from your data source (e.g., database)

	        // Example implementation using Hibernate's EntityManager
	        EntityManager entityManager = Utils.getEntityManager();
            Query query = entityManager.createQuery("SELECT i FROM Instructor i WHERE i.name = :name");
	        query.setParameter("name", instructorName);

	        try {
	            return (Instructor) query.getSingleResult();
	        } catch (NoResultException e) {
	            return null; // Return null if the instructor is not found
	        }
	    }
	  @Override
	  public void createCourse(Course course) throws SomethingWentWrongException {
	      EntityManager em = Utils.getEntityManager();
	      EntityTransaction et = em.getTransaction();
	      try {
	          et.begin();

	          // Retrieve the instructor by name
	          String instructorName = course.getInstructor().getName();
	          Query query = em.createQuery("SELECT i FROM Instructor i WHERE i.name = :name");
	          query.setParameter("name", instructorName);
	          Instructor instructor = (Instructor) query.getSingleResult();

	          // Set the instructor for the course
	          course.setInstructor(instructor);

	          // Persist the course
	          em.persist(course);

	          et.commit();
	      } catch (PersistenceException e) {
	          et.rollback(); // Rollback the transaction in case of an exception
	          e.printStackTrace();
	          throw new SomethingWentWrongException("Failed to create course");
	      } finally {
	          em.close();
	      }
	  }



	  @Override
	  public void updateCourse(Course course, int instructorId) throws SomethingWentWrongException, NoRecordFoundException {
	      EntityManager em = Utils.getEntityManager();
	      EntityTransaction et = em.getTransaction();

	      try {
	          et.begin();

	          // Check if the course exists in the database
	          Course existingCourse = em.find(Course.class, course.getId());
	          if (existingCourse == null) {
	              throw new NoRecordFoundException("Course not found");
	          }
	          Instructor existingIns = em.find(Instructor.class, instructorId);
	          if (existingIns == null) {
	              throw new NoRecordFoundException("Instructor not found");
	          }

	          // Update the course properties
	          existingCourse.setName(course.getName());
	          existingCourse.setInstructor(existingIns);
	          et.commit();
	      } catch (NoRecordFoundException e) {
	          et.rollback();
	          throw e;
	      } catch (Exception e) {
	          et.rollback();
	          throw new SomethingWentWrongException("Failed to update the course");
	      } finally {
	          em.close();
	      }
	  }


	@Override
	public void deleteCourse(Course course) throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Assignment> getAssignmentsByCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createAssignment(Assignment assignment, Course course) throws SomethingWentWrongException {

		 EntityManager em= Utils.getEntityManager();
		 EntityTransaction et= em.getTransaction();
		try {
		       
		        assignment.setCourse(course);

		        		et.begin();
		        em.persist(assignment);
		        et.commit();

		        System.out.println("Assignment created successfully.");
		    } catch (Exception e) {
		        throw new SomethingWentWrongException("Failed to create the assignment.");
		    }

		
	}

	@Override
	public void updateAssignment(Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAssignment(Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Grade> getGradesByAssignment(Assignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gradeAssignment(Student student, Assignment assignment, int score)
			throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Discussion> getDiscussionsByCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDiscussion(Discussion discussion, Course course) throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDiscussion(Discussion discussion) throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDiscussion(Discussion discussion) throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCourse(Course course) throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}





}
