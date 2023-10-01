package com.masai.Dao;

import java.util.List;

import com.masai.EMUtils.Utils;
import com.masai.Entities.Assignment;
import com.masai.Entities.AssignmentStatus;
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
	 * @throws SomethingWentWrongException If an error occurs during the sign-up
	 *                                     process.
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
				// If no existing student is found with the same username, begin a transaction
				// and persist the new student
				et.begin();
				em.persist(student);
				et.commit();
			}
		} catch (NoResultException e) {
			// If no existing student is found, begin a transaction and persist the new
			// student
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
	 * Authenticates a student by verifying their username and password in the
	 * database.
	 *
	 * @param username The username of the student.
	 * @param password The password of the student.
	 * @return The authenticated student if the username and password match, or null
	 *         otherwise.
	 * @throws NoRecordFoundException If no student with the provided username and
	 *                                password is found.
	 */
	@Override
	public Student login(String username, String password) throws NoRecordFoundException {
		EntityManager em = Utils.getEntityManager();

		try {
			Query query = em.createQuery("SELECT s FROM Student s WHERE s.username = :username");
			query.setParameter("username", username);
			Student student = (Student) query.getSingleResult();

			// Check if the entered password matches the stored hashed password
			if (student.checkPassword(password)) {
				return student;
			} else {
				throw new NoRecordFoundException("Invalid username or password");
			}
		} catch (NoResultException e) {
			throw new NoRecordFoundException("Invalid username or password");
		} finally {
			em.close();
		}
	}

	/**
	 * Records a student's access to lectures by incrementing the lecture access
	 * count after successful login.
	 *
	 * @param username The username of the student.
	 * @param password The password of the student.
	 * @throws SomethingWentWrongException If an unexpected error occurs during
	 *                                     lecture access recording.
	 * @throws NoRecordFoundException      If no student with the provided username
	 *                                     and password is found.
	 */
	@Override
	public void accessLectures(String username, String password)
			throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = Utils.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();

			// Attempt to log in the student using the provided username and password
			Student loggedInStudent = login(username, password);

			// Check if the entity is detached; if so, reattach it
			if (!em.contains(loggedInStudent)) {
				loggedInStudent = em.merge(loggedInStudent);
			}

			// Retrieve the current lecture access count for the logged-in student
			int lectureAccessCount = loggedInStudent.getLectureAccessCount();

			// Increment the lecture access count to record the access
			lectureAccessCount++;
			loggedInStudent.setLectureAccessCount(lectureAccessCount);

			// Use merge to synchronize changes with the database
			em.merge(loggedInStudent);

			et.commit();
			System.out.println("You have access lecture " + lectureAccessCount + " time");
		} catch (NoRecordFoundException e) {
			// If no student with matching credentials is found, rethrow the exception
			et.rollback(); // Rollback the transaction
			throw new NoRecordFoundException("Invalid username or password");
		} finally {
			em.close();
		}
	}

	/**
	 * Records a student's access to readings by incrementing the readings access
	 * count after successful login.
	 *
	 * @param username The username of the student.
	 * @param password The password of the student.
	 * @throws SomethingWentWrongException If an unexpected error occurs during
	 *                                     readings access recording.
	 */
	@Override
	public void accessReadings(String username, String password) throws SomethingWentWrongException {

		try {

			// Attempt to log in the student using the provided username and password
			Student loggedInStudent = login(username, password);

			// Retrieve the current lecture access count for the logged-in student
			int readingAccessCount = loggedInStudent.getReadingAccessCount();
			// Increment the lecture access count to record the access
			readingAccessCount++;
			loggedInStudent.setReadingAccessCount(readingAccessCount);

			System.out.println("You have accessed readings " + readingAccessCount + " time(s).");
		} catch (NoRecordFoundException e) {
			// If no student with matching credentials is found, throw a
			// SomethingWentWrongException
			throw new SomethingWentWrongException("Invalid username or password");
		}
	}

	/**
	 * Records a student's access to videos by incrementing the videos access count
	 * after successful login.
	 *
	 * @param username The username of the student.
	 * @param password The password of the student.
	 * @throws SomethingWentWrongException If an unexpected error occurs during
	 *                                     videos access recording.
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
			System.out.println("You have access videos " + videosAccessCount + " time");
		} catch (NoRecordFoundException e) {
			// If no student with matching credentials is found, throw an exception
			throw new SomethingWentWrongException("Invalid username or password");
		}
	}

	/**
	 * Retrieves and displays assignments for the logged-in student.
	 *
	 * @param loggedInStudent The student who is logged in and for whom assignments
	 *                        are retrieved.
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
	public void submitAssignments(int studentId, int assignmentId) {
		EntityManager em = Utils.getEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();

			// Find the student by ID
			Student student = em.find(Student.class, studentId);

			// Find the assignment by ID
			Assignment assignment = em.find(Assignment.class, assignmentId);

			// Check if both student and assignment exist
			if (student == null || assignment == null) {
				System.out.println("Student or Assignment not found.");
				return;
			}

			// Check if the assignment belongs to the student
			if (!assignment.getStudent().equals(student)) {
				System.out.println("Assignment does not belong to the specified student.");
				return;
			}

			// Update the assignment status to CLOSE
			assignment.setAssignmentStatus(AssignmentStatus.CLOSE);

			transaction.commit();

			System.out.println("Assignment submitted successfully.");
		} catch (NoResultException e) {
			// Handle the case where find() does not find the entity
			System.out.println("Student or Assignment not found.");
			transaction.rollback();
		} catch (Exception e) {
			// Handle other exceptions, e.g., database access errors
			System.out.println("Failed to submit the assignment.");
			e.printStackTrace();
			transaction.rollback();
		} finally {
			em.close();
		}
	}

}
