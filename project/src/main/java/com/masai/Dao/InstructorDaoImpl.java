package com.masai.Dao;

import java.util.ArrayList;
import java.util.List;

import com.masai.EMUtils.Utils;
import com.masai.Entities.Assignment;
import com.masai.Entities.AssignmentStatus;
import com.masai.Entities.Course;
import com.masai.Entities.Enrollment;
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
	
	
	 /**
     * Registers a new instructor in the system.
     *
     * @param instructor The instructor to register.
     * @throws SomethingWentWrongException If an error occurs during registration.
     */

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

	/**
     * Logs an instructor into the system.
     *
     * @param username The instructor's username.
     * @param password The instructor's password.
     * @return The logged-in instructor.
     * @throws NoRecordFoundException If the username or password is invalid.
     */
	@Override
	public Instructor login(String username, String password) throws NoRecordFoundException {
	    EntityManager em = Utils.getEntityManager();

	    try {
	        Query query = em.createQuery("SELECT i FROM Instructor i WHERE i.username = :username");
	        query.setParameter("username", username);
	        Instructor instructor = (Instructor) query.getSingleResult();

	        // Check if the entered password matches the stored hashed password
	        if (instructor.checkPassword(password)) {
	            return instructor;
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
     * Retrieves a list of courses associated with a specific instructor.
     *
     * @param instructorId The ID of the instructor.
     * @return A list of courses taught by the instructor.
     * @throws SomethingWentWrongException If an error occurs while fetching the courses.
     */
	  
	@Override
	public List<Course> getCoursesByInstructorId(int instructorId) throws SomethingWentWrongException {
		EntityManager em = Utils.getEntityManager();
		List<Course> list = new ArrayList<>();
		try {
			Query query = em.createQuery("SELECT c FROM Course c WHERE c.instructor.id = :instructorId");
			query.setParameter("instructorId", instructorId);
			list = query.getResultList();
		} catch (PersistenceException e) {
			throw new SomethingWentWrongException("An error occurred while fetching courses by instructor ID");
		} finally {
			em.close();
		}
		return list;
	}

	public Instructor getInstructorByName(String instructorName) {
		return null;
	}
	
	/**
	 * Creates a new course and associates it with a specific instructor.
	 *
	 * @param course      The course to create.
	 * @param instructorId The ID of the instructor who will teach the course.
	 * @throws SomethingWentWrongException If an error occurs during course creation.
	 */
	
	@Override
	public void createCourse(Course course, int instructorId) throws SomethingWentWrongException {
		EntityManager em = Utils.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Instructor instructor = em.find(Instructor.class, instructorId);
			if (instructor == null) {
				throw new NoRecordFoundException("Instructor with ID " + instructorId + " not found");
			}
			course.setInstructor(instructor);

			em.persist(course);
			et.commit();

		} catch (NoRecordFoundException e) {
			et.rollback();
			throw new SomethingWentWrongException("Error in createCourse method: " + e.getMessage());
		} catch (PersistenceException e) {
			et.rollback();
			e.printStackTrace();
			throw new SomethingWentWrongException("Failed to create course: " + e.getMessage());
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	/**
	 * Updates an existing course's details, including its name and associated instructor.
	 *
	 * @param course      The updated course information.
	 * @param courseId    The ID of the course to update.
	 * @param instructorId The ID of the new instructor for the course.
	 * @throws SomethingWentWrongException If an error occurs during course update.
	 * @throws NoRecordFoundException     If the specified course or instructor is not found.
	 */
	@Override
	public void updateCourse(Course course, int courseId, int instructorId)
	        throws SomethingWentWrongException, NoRecordFoundException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();
	        // Find the existing course by its ID in the database
	        Course existingCourse = em.find(Course.class, courseId);
	        if (existingCourse == null) {
	            throw new NoRecordFoundException("Course not found");
	        }

	        // Find the new instructor by their ID in the database
	        Instructor existingInstructor = em.find(Instructor.class, instructorId);
	        if (existingInstructor == null) {
	            throw new NoRecordFoundException("Instructor not found");
	        }

	        // Update the course's name and instructor
	        existingCourse.setName(course.getName());
	        existingCourse.setInstructor(existingInstructor);

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


	/**
	 * Deletes a course and its associated data, including assignments, discussions, etc.
	 *
	 * @param courseId The ID of the course to delete.
	 * @throws SomethingWentWrongException If an error occurs during course deletion.
	 * @throws NoRecordFoundException     If the specified course is not found.
	 */
	@Override
	public void deleteCourse(int courseId) throws SomethingWentWrongException, NoRecordFoundException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = null;

	    try {
	        et = em.getTransaction();
	        et.begin();

	        // Find the existing course by its ID in the database
	        Course existingCourse = em.find(Course.class, courseId);

	        if (existingCourse == null) {
	            throw new NoRecordFoundException("Course not found");
	        }

	        System.out.println("Deleting course: " + existingCourse.getName());

	        // Remove the course from the database
	        em.remove(existingCourse);
	        em.flush();

	        et.commit();
	    } catch (NoRecordFoundException e) {
	        if (et != null && et.isActive()) {
	            System.out.println("rollback1");
	            et.rollback();
	        }
	        throw e;
	    } catch (Exception e) {
	        if (et != null && et.isActive()) {
	            System.out.println("rollback2");
	            et.rollback();
	        }
	        throw new SomethingWentWrongException("Failed to delete the course"); // Include the exception as a cause
	    } finally {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }
	}


	/**
	 * Creates a new assignment and associates it with a specific course and student.
	 *
	 * @param assignment The assignment to create.
	 * @param courseId   The ID of the course to which the assignment belongs.
	 * @param studentId  The ID of the student who is assigned the task.
	 * @throws SomethingWentWrongException If an error occurs during assignment creation.
	 * @throws NoRecordFoundException     If the specified course or student is not found.
	 */
	@Override
	public void createAssignment(Assignment assignment, int courseId, int studentId)
	        throws SomethingWentWrongException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();
	        // Find the course by its ID in the database
	        Course course = em.find(Course.class, courseId);

	        if (course == null) {
	            throw new NoRecordFoundException("Course not found");
	        }

	        // Find the student by their ID in the database
	        Student student = em.find(Student.class, studentId);

	        if (student == null) {
	            throw new NoRecordFoundException("Student not found");
	        }

	        // Associate the assignment with the course and student
	        assignment.setCourse(course);
	        assignment.setStudent(student);
	        assignment.setAssignmentStatus(AssignmentStatus.OPEN);

	        // Persist the new assignment in the database
	        em.persist(assignment);
	        et.commit();

	        // Optionally, log a success message
	        // System.out.println("Assignment created successfully.");
	    } catch (NoRecordFoundException e) {
	        et.rollback();
	        throw new SomethingWentWrongException("No Record found");
	    } catch (Exception e) {
	        et.rollback();
	        throw new SomethingWentWrongException("Failed to create the assignment.");
	    } finally {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }
	}

	/**
	 * Retrieves a list of assignments associated with a specific course by its ID.
	 *
	 * @param courseId The ID of the course for which assignments are to be retrieved.
	 * @return A list of assignments belonging to the specified course.
	 * @throws SomethingWentWrongException If an error occurs while fetching assignments.
	 */
	@Override
	public List<Assignment> getAssignmentsByCourseId(int courseId) throws SomethingWentWrongException {
	    EntityManager em = Utils.getEntityManager();

	    try {
	        // Find the course by its ID in the database
	        Course course = em.find(Course.class, courseId);

	        if (course == null) {
	            throw new SomethingWentWrongException("Course not found for ID: " + courseId);
	        }

	        // Get the list of assignments associated with the course
	        List<Assignment> assignments = course.getAssignments();

	        if (assignments == null) {
	            throw new SomethingWentWrongException("No assignments found for course ID: " + courseId);
	        }

	        // Log courseId and the number of assignments retrieved
	        System.out.println("Course ID: " + courseId);
	        System.out.println("Number of assignments: " + assignments.size());

	        return assignments;
	    } catch (Exception e) {
	        throw new SomethingWentWrongException("Failed to retrieve assignments by course ID");
	    } finally {
	        em.close();
	    }
	}


	
	
	
	/**
	 * Updates the details of an assignment, such as its name, for a specific student and course.
	 *
	 * @param assignment    The updated assignment object.
	 * @param assignmentId  The ID of the assignment to be updated.
	 * @param courseId      The ID of the course to which the assignment belongs.
	 * @param studentId     The ID of the student to whom the assignment is assigned.
	 * @throws SomethingWentWrongException If an error occurs during the update process.
	 * @throws NoRecordFoundException      If the assignment, student, or course is not found in the database.
	 */
	@Override
	public void updateAssignment(Assignment assignment, int assignmentId, int courseId, int studentId)
	        throws SomethingWentWrongException, NoRecordFoundException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();

	        // Find the existing assignment by its ID
	        Assignment existingAssignment = em.find(Assignment.class, assignmentId);
	        if (existingAssignment == null) {
	            throw new NoRecordFoundException("Assignment not found");
	        }

	        // Find the student by their ID
	        Student existingStudent = em.find(Student.class, studentId);
	        if (existingStudent == null) {
	            throw new NoRecordFoundException("Student not found");
	        }

	        // Find the course by its ID
	        Course existingCourse = em.find(Course.class, courseId);
	        if (existingCourse == null) {
	            throw new NoRecordFoundException("Course not found");
	        }

	        // Ensure that the assignment belongs to the specified student and course
	        if (!existingAssignment.getStudent().equals(existingStudent) || !existingAssignment.getCourse().equals(existingCourse)) {
	            throw new NoRecordFoundException("Assignment does not belong to the specified student and course");
	        }

	        // Update the assignment name
	        existingAssignment.setName(assignment.getName());

	        et.commit();
	    } catch (NoRecordFoundException e) {
	        et.rollback();
	        throw e;
	    } catch (Exception e) {
	        et.rollback();
	        throw new SomethingWentWrongException("Failed to update the assignment");
	    } finally {
	        em.close();
	    }
	}


	/**
	 * Deletes an assignment based on its unique identifier.
	 *
	 * @param assignmentId The unique identifier of the assignment to be deleted.
	 * @throws SomethingWentWrongException If an error occurs during the deletion process.
	 * @throws NoRecordFoundException      If the assignment with the specified ID is not found in the database.
	 */
	@Override
	public void deleteAssignment(int assignmentId) throws SomethingWentWrongException, NoRecordFoundException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();

	        // Find the existing assignment by its ID
	        Assignment existingAssignment = em.find(Assignment.class, assignmentId);
	        if (existingAssignment == null) {
	            throw new NoRecordFoundException("Assignment not found");
	        }

	        // Remove the assignment from the database
	        em.remove(existingAssignment);
	        et.commit();
	    } catch (NoRecordFoundException e) {
	        et.rollback();
	        throw e;
	    } catch (Exception e) {
	        et.rollback();
	        throw new SomethingWentWrongException("Failed to delete the assignment");
	    } finally {
	        em.close();
	    }
	}

	@Override
	public void enrollCourseToStudent(int courseId, int studentId) throws NoRecordFoundException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();

	        // Retrieve the student and course objects by their IDs
	        Student student = em.find(Student.class, studentId);
	        Course course = em.find(Course.class, courseId);

	        if (student == null || course == null) {
	            // Handle the case where either the student or course is not found
	            throw new NoRecordFoundException("Student or Course not found");
	        }

	        // Create an Enrollment entity to represent the student's enrollment in the course
	        Enrollment enrollment = new Enrollment();
	        enrollment.setCourse(course);
	        enrollment.setStudent(student);

	        // Add the enrollment to the student's list of enrollments
	        student.getEnrollments().add(enrollment);

	        // Add the course to the student's list of courses
	        student.getCourses().add(course);

	        // Add the student to the course's list of students
	        course.getStudents().add(student);

	        // Update the student and course entities in the database
	        em.merge(student);
	        em.merge(course);

	        et.commit();
	        System.out.println("Enrolled Successfully");
	    } catch (Exception e) {
	        // Handle exceptions related to database access or other errors
	        et.rollback();
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}




	

}
