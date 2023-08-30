package com.masai.Dao;

import java.util.List;

import com.masai.EMUtils.Utils;
import com.masai.Entities.Assignment;
import com.masai.Entities.Course;
import com.masai.Entities.Discussion;
import com.masai.Entities.Grade;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class StudentsDaoImpl implements IStudentDao {

	
	@Override
	public void singUp(Student student) throws SomethingWentWrongException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        String username = student.getUsername();
	        Query query = em.createQuery("SELECT s FROM Student s WHERE s.username = :username");
	        query.setParameter("username", username);
	        Student existingStudent = (Student) query.getSingleResult();

	        if (existingStudent != null) {
	            System.out.println("Username already exists");
	        } else {
	            et.begin();
	            em.persist(student);
	            et.commit();
	        }
	    } catch (NoResultException e) {
	        et.begin();
	        em.persist(student);
	        et.commit();
	    } catch (Exception e) {
	        et.rollback();
	        throw new SomethingWentWrongException("Unable to add student");
	    } finally {
	    em.close();
	    }
	}

	@Override
	 public Student login(String username, String password) throws NoRecordFoundException {
        EntityManager em = Utils.getEntityManager();

        try {
            Student student = em.createQuery("SELECT s FROM Student s WHERE s.username = :username AND s.password = :password", Student.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            return student;
        } catch (NoResultException e) {
        	throw new NoRecordFoundException("Invalid username or password");
         
           
        } finally {
            em.close();
        }
    }

	@Override
	public void accessLectures(String username, String password) throws SomethingWentWrongException, NoRecordFoundException {
		try {
		  
		    Student loggedInStudent = login(username, password);
		

		    int lectureAccessCount = loggedInStudent.getLectureAccessCount();
		    lectureAccessCount++;
		    loggedInStudent.setLectureAccessCount(lectureAccessCount);

	
		} catch (NoRecordFoundException e) {
		   throw new NoRecordFoundException("Invalid username or password");
		}
		
	}
	@Override
	public void accessReadings(String username, String password) throws SomethingWentWrongException{
	    try {
	     
	        Student loggedInStudent = login(username, password);

	       
	        int readingsAccessCount = loggedInStudent.getReadingAccessCount();
	        readingsAccessCount++;
	        loggedInStudent.setReadingAccessCount(readingsAccessCount);

	       

	    } catch (NoRecordFoundException e) {
	        throw new SomethingWentWrongException("Invalid username or password");
	    }
	}

	@Override
	public void accessVideos(String username, String password) throws SomethingWentWrongException{
	    try {
	      
	        Student loggedInStudent = login(username, password);

	        
	        int videosAccessCount = loggedInStudent.getVideoAccessCount();
	        videosAccessCount++;
	        loggedInStudent.setVideoAccessCount(videosAccessCount);

	    } catch (NoRecordFoundException e) {
	        throw new SomethingWentWrongException("Invalid username or password");
	    }
	}

	@Override
    public void accessAssignments(Student loggedInStudent) {
        EntityManager em = Utils.getEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Assignment a WHERE a.student = :student");
            query.setParameter("student", loggedInStudent);
            List<Assignment> assignments = query.getResultList();

            if (assignments.isEmpty()) {
                System.out.println("No assignments available.");
            } else {
                System.out.println("Assignments:");
                for (Assignment assignment : assignments) {
                    System.out.println("Assignment ID: " + assignment.getId());
                    System.out.println("Assignment Name: " + assignment.getName());
                    
                    System.out.println("------------------------");
                }
            }
        } catch (PersistenceException e) {
            System.out.println("Failed to retrieve assignments for the student");
        } finally {
            em.close();
        }
    }


	

	@Override
	public void trackProgress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Course> getEnrolledCourses(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Assignment> getAssignmentsByStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void submitAssignment(Student student, Assignment assignment, String submission) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Grade> getGradesByStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Discussion> getDiscussionsByCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDiscussionPost(Student student, Course course, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Discussion> getDiscussionPostsByDiscussion(Discussion discussion) {
		// TODO Auto-generated method stub
		return null;
	}

	



	

}
