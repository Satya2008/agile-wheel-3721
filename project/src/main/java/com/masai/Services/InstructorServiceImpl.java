package com.masai.Services;

import java.util.List;

import com.masai.Dao.IInstructorDao;
import com.masai.Dao.InstructorDaoImpl;
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
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class InstructorServiceImpl implements IInstructorService {

	@Override
	public void register(Instructor instructor) throws SomethingWentWrongException {
		IInstructorDao ins = new InstructorDaoImpl();
		ins.register(instructor);
		
	}

	@Override
	public Instructor login(String username, String password) throws NoRecordFoundException {
		IInstructorDao ins = new InstructorDaoImpl();
		return ins.login(username, password);
	}

	@Override
	public List<Course> getCoursesByInstructor(Instructor instructor) {
		IInstructorDao ins = new InstructorDaoImpl();
		return ins.getCoursesByInstructor(instructor);
	}

	public Instructor getInstructorByName(String name) throws SomethingWentWrongException {
	    EntityManager em = Utils.getEntityManager();

	    try {
	        TypedQuery<Instructor> query = em.createQuery("SELECT i FROM Instructor i WHERE i.name = :name", Instructor.class);
	        query.setParameter("name", name);
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null; // Instructor not found
	    } catch (PersistenceException e) {
	        throw new SomethingWentWrongException("Failed to retrieve instructor by name");
	    } finally {
	        em.close();
	    }
	}
	@Override
	public void createCourse(Course course) throws SomethingWentWrongException {
		IInstructorDao ins = new InstructorDaoImpl();
		ins.createCourse(course);
		
	}
	@Override
	public void updateCourse(Course course, int instructorId)
			throws SomethingWentWrongException, NoRecordFoundException {
		IInstructorDao ins = new InstructorDaoImpl();
		ins.updateCourse(course);
		System.out.println("Updated successfully!..");
		
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

	
	 public static Course findByName(String courseName) {
	       EntityManager em = Utils.getEntityManager();
	        Query query = em.createQuery("SELECT c FROM Course c WHERE c.name = :courseName");
	        query.setParameter("courseName", courseName);
	        return (Course) query.getSingleResult();
	    }
	
	@Override
	public void createAssignment(Assignment assignment, Course course) throws SomethingWentWrongException {
		IInstructorDao ins = new InstructorDaoImpl();
		ins.createAssignment(assignment, course);
		
		
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

	

	
}
