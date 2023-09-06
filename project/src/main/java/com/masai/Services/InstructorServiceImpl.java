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
import jakarta.persistence.Query;

public  class InstructorServiceImpl implements IInstructorService {
    static IInstructorDao ins = new InstructorDaoImpl();
	@Override
	public void register(Instructor instructor) throws SomethingWentWrongException {
		IInstructorDao ins = new InstructorDaoImpl();
		ins.register(instructor);
		
	}
	
	@Override
	public void updateCourse(Course course, int courseId, int instructorId)
			throws SomethingWentWrongException, NoRecordFoundException {
		ins.updateCourse(course, courseId, instructorId);
		
	}
	
	@Override
	public Instructor login(String username, String password) throws NoRecordFoundException {
		IInstructorDao ins = new InstructorDaoImpl();
		return ins.login(username, password);
	}

	@Override
	public List<Course> getCoursesByInstructorId(int instructorId) throws SomethingWentWrongException {
		IInstructorDao ins = new InstructorDaoImpl();
		return ins.getCoursesByInstructorId(instructorId);
	}






	@Override
	public void deleteCourse(int courseId) throws SomethingWentWrongException, NoRecordFoundException {
		IInstructorDao ins = new InstructorDaoImpl();
		ins.deleteCourse(courseId);
		
	}

	
	 public static Course findByName(String courseName) {
	       EntityManager em = Utils.getEntityManager();
	        Query query = em.createQuery("SELECT c FROM Course c WHERE c.name = :courseName");
	        query.setParameter("courseName", courseName);
	        return (Course) query.getSingleResult();
	    }
	
	@Override
	public void createAssignment(Assignment assignment, int courseId, int studentId) throws SomethingWentWrongException {
		IInstructorDao ins = new InstructorDaoImpl();
		ins.createAssignment(assignment, courseId, studentId);
		
		
	}

	@Override
	public void createCourse(Course course, int instructorId) throws SomethingWentWrongException {
		ins.createCourse(course, instructorId);
		
	}

	@Override
	public List<Assignment> getAssignmentsByCourseId(int courseId) throws SomethingWentWrongException {
		return ins.getAssignmentsByCourseId(courseId);
	}

	@Override
	public void updateAssignment(Assignment assignment, int assignmentId, int courseId, int studentId) throws SomethingWentWrongException, NoRecordFoundException {
	
		ins.updateAssignment(assignment, assignmentId, courseId, studentId);
	}

	@Override
	public void deleteAssignment(int assignmentId) throws SomethingWentWrongException, NoRecordFoundException {
		ins.deleteAssignment(assignmentId);
		
	}

	@Override
	public List<Grade> getGradesByAssignmentId(int assignmentId) throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gradeAssignment(Student student, Assignment assignment, int score)
			throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Discussion> getDiscussionsByCourseId(int courseId) throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDiscussion(Discussion discussion, int courseId) throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDiscussion(int discussionId) throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDiscussion(int discussionId) throws SomethingWentWrongException, NoRecordFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}








	
}
