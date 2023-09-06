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
			Query query = em
					.createQuery("SELECT i FROM Instructor i WHERE i.username = :username AND i.password = :password");
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

	@Override
	public void updateCourse(Course course, int courseId, int instructorId)
			throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = Utils.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Course existingCourse = em.find(Course.class, courseId);
			if (existingCourse == null) {
				throw new NoRecordFoundException("Course not found");
			}

			Instructor existingInstructor = em.find(Instructor.class, instructorId);
			if (existingInstructor == null) {
				throw new NoRecordFoundException("Instructor not found");
			}
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

	@Override
	public void deleteCourse(int courseId) throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = Utils.getEntityManager();
		EntityTransaction et = null;

		try {
			et = em.getTransaction();
			et.begin();

			Course existingCourse = em.find(Course.class, courseId);

			if (existingCourse == null) {
				throw new NoRecordFoundException("Course not found");
			}

			System.out.println("Deleting course: " + existingCourse.getName());

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

	@Override
	public void createAssignment(Assignment assignment, int courseId, int studentId)
			throws SomethingWentWrongException {
		EntityManager em = Utils.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Course course = em.find(Course.class, courseId);

			if (course == null) {
				throw new NoRecordFoundException("Course not found");
			}

			Student student = em.find(Student.class, studentId);

			if (student == null) {
				throw new NoRecordFoundException("Student not found");
			}

			assignment.setCourse(course);
			assignment.setStudent(student);
			em.persist(assignment);
			et.commit();

//			System.out.println("Assignment created successfully.");
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
	@Override
	public List<Assignment> getAssignmentsByCourseId(int courseId) throws SomethingWentWrongException {
	    EntityManager em = Utils.getEntityManager();

	    try {
	        Course course = em.find(Course.class, courseId);

	        if (course == null) {
	            throw new SomethingWentWrongException("Course not found for ID: " + courseId);
	        }

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


	
	
	

	@Override
	public void updateAssignment(Assignment assignment, int assignmentId, int courseId, int studentId)
	        throws SomethingWentWrongException, NoRecordFoundException {
	    EntityManager em = Utils.getEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();

	
	        Assignment existingAssignment = em.find(Assignment.class, assignmentId);
	        if (existingAssignment == null) {
	            throw new NoRecordFoundException("Assignment not found");
	        }
	        
	        Student existingStudent = em.find(Student.class, studentId);
	        if (existingStudent == null) {
	            throw new NoRecordFoundException("Student not found");
	        }

	        Course existingCourse = em.find(Course.class, courseId);
	        if (existingCourse == null) {
	            throw new NoRecordFoundException("Course not found");
	        }

	        if (!existingAssignment.getStudent().equals(existingStudent) || !existingAssignment.getCourse().equals(existingCourse)) {
	            throw new NoRecordFoundException("Assignment does not belong to the specified student and course");
	        }

	  
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

	@Override
	public void deleteAssignment(int assignmentId) throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = Utils.getEntityManager();
		EntityTransaction et = em.getTransaction();

		try {
			et.begin();
			Assignment existingAssignment = em.find(Assignment.class, assignmentId);
			if (existingAssignment == null) {
				throw new NoRecordFoundException("Assignment not found");
			}
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
