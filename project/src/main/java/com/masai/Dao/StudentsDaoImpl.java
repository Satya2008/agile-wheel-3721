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

	
	/**
	 * Signs up a new student by persisting their information in the database.
	 *
	 * @param student The student object containing the registration information.
	 * @throws SomethingWentWrongException If an error occurs during the sign-up process.
	 */
	@Override
	public void signUp(Student student) throws SomethingWentWrongException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        String username = student.getUsername();

	        // Check if a student with the same username already exists
	        Query query = em.createQuery("SELECT s FROM Student s WHERE s.username = :username");
	        query.setParameter("username", username);

	        // Attempt to retrieve an existing student with the same username
	        Student existingStudent = (Student) query.getSingleResult();

	        if (existingStudent != null) {
	            System.out.println("Username already exists");
	        } else {
	            // If no existing student is found with the same username, begin a transaction and persist the new student
	            et.begin();
	            em.persist(student);
	            et.commit();
	        }
	    } catch (NoResultException e) {
	        // If no existing student is found, begin a transaction and persist the new student
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

	
	
	/**
	 * Authenticates a student by verifying their username and password in the database.
	 *
	 * @param username The username of the student.
	 * @param password The password of the student.
	 * @return The authenticated student if the username and password match, or null otherwise.
	 * @throws NoRecordFoundException If no student with the provided username and password is found.
	 */
	@Override
	public Student login(String username, String password) throws NoRecordFoundException {
	    EntityManager em = Utils.getEntityManager();

	    try {
	        // Query the database for a student with the provided username and password
	        Student student = em.createQuery("SELECT s FROM Student s WHERE s.username = :username AND s.password = :password", Student.class)
	                .setParameter("username", username)
	                .setParameter("password", password)
	                .getSingleResult();
	        
	        // If a student with matching credentials is found, return the authenticated student
	        return student;
	    } catch (NoResultException e) {
	        // If no student with matching credentials is found, throw a NoRecordFoundException
	        throw new NoRecordFoundException("Invalid username or password");
	    } finally {
	        // Ensure the EntityManager is closed
	        em.close();
	    }
	}
	/**
	 * Records a student's access to lectures by incrementing the lecture access count
	 * after successful login.
	 *
	 * @param username The username of the student.
	 * @param password The password of the student.
	 * @throws SomethingWentWrongException If an unexpected error occurs during lecture access recording.
	 * @throws NoRecordFoundException If no student with the provided username and password is found.
	 */
	@Override
	public void accessLectures(String username, String password) throws SomethingWentWrongException, NoRecordFoundException {
	    try {
	        // Attempt to log in the student using the provided username and password
	        Student loggedInStudent = login(username, password);

	        // Retrieve the current lecture access count for the logged-in student
	        int lectureAccessCount = loggedInStudent.getLectureAccessCount();

	        // Increment the lecture access count to record the access
	        lectureAccessCount++;
	        loggedInStudent.setLectureAccessCount(lectureAccessCount);

	    } catch (NoRecordFoundException e) {
	        // If no student with matching credentials is found, rethrow the exception
	        throw new NoRecordFoundException("Invalid username or password");
	    }
	}

	/**
	 * Records a student's access to readings by incrementing the readings access count
	 * after successful login.
	 *
	 * @param username The username of the student.
	 * @param password The password of the student.
	 * @throws SomethingWentWrongException If an unexpected error occurs during readings access recording.
	 */
	@Override
	public void accessReadings(String username, String password) throws SomethingWentWrongException {
	    try {
	        // Attempt to log in the student using the provided username and password
	        Student loggedInStudent = login(username, password);

	        // Retrieve the current readings access count for the logged-in student
	        int readingsAccessCount = loggedInStudent.getReadingAccessCount();

	        // Increment the readings access count to record the access
	        readingsAccessCount++;
	        loggedInStudent.setReadingAccessCount(readingsAccessCount);

	    } catch (NoRecordFoundException e) {
	        // If no student with matching credentials is found, throw an exception
	        throw new SomethingWentWrongException("Invalid username or password");
	    }
	}

	/**
	 * Records a student's access to videos by incrementing the videos access count
	 * after successful login.
	 *
	 * @param username The username of the student.
	 * @param password The password of the student.
	 * @throws SomethingWentWrongException If an unexpected error occurs during videos access recording.
	 */
	@Override
	public void accessVideos(String username, String password) throws SomethingWentWrongException {
	    try {
	        // Attempt to log in the student using the provided username and password
	        Student loggedInStudent = login(username, password);

	        // Retrieve the current videos access count for the logged-in student
	        int videosAccessCount = loggedInStudent.getVideoAccessCount();

	        // Increment the videos access count to record the access
	        videosAccessCount++;
	        loggedInStudent.setVideoAccessCount(videosAccessCount);

	    } catch (NoRecordFoundException e) {
	        // If no student with matching credentials is found, throw an exception
	        throw new SomethingWentWrongException("Invalid username or password");
	    }
	}


	/**
	 * Retrieves and displays assignments for the logged-in student.
	 *
	 * @param loggedInStudent The student who is logged in and for whom assignments are retrieved.
	 */
	@Override
	public void accessAssignments(Student loggedInStudent) {
	    EntityManager em = Utils.getEntityManager();
	    try {
	        // Create a query to retrieve assignments for the logged-in student
	        Query query = em.createQuery("SELECT a FROM Assignment a WHERE a.student = :student");
	        query.setParameter("student", loggedInStudent);
	        
	        // Execute the query and obtain a list of assignments
	        List<Assignment> assignments = query.getResultList();

	        // Check if there are any assignments for the student
	        if (assignments.isEmpty()) {
	            System.out.println("No assignments available.");
	        } else {
	            System.out.println("Assignments:");
	            
	            // Display each assignment's ID and name
	            for (Assignment assignment : assignments) {
	                System.out.println("Assignment ID: " + assignment.getId());
	                System.out.println("Assignment Name: " + assignment.getName());
	                
	                System.out.println("------------------------");
	            }
	        }
	    } catch (PersistenceException e) {
	        // Handle exceptions related to database access
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
