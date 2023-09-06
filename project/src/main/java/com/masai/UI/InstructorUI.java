package com.masai.UI;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.management.IntrospectionException;

import com.masai.EMUtils.Utils;
import com.masai.Entities.Assignment;
import com.masai.Entities.Course;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;
import com.masai.Services.IInstructorService;
import com.masai.Services.InstructorServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;


public class InstructorUI {
	
    public static IInstructorService ins = new InstructorServiceImpl();
	
	 public static void SingUp(Scanner sc) {
		 System.out.println("Enter username");
			String username = sc.next();
			System.out.println("Enter password");
			String password = sc.next();
			System.out.println("Enter name");
			String name = sc.next();
			System.out.println("Enter DOB");
			LocalDate DOB = LocalDate.parse(sc.next());
			System.out.println("Enter MobileNo");
			String mobileNo = sc.next();
			Instructor ins = new Instructor();
			ins.setUsername(username);
			ins.setPassword(password);
			ins.setName(name);
			ins.setDateOfBirth(DOB);
			ins.setMobileNo(mobileNo);

			IInstructorService insSer = new InstructorServiceImpl();
			
			try {
				insSer.register(ins);
				System.out.println("successfully singup");
			} catch (SomethingWentWrongException e) {
				System.out.println(e.getMessage());
			}
	 }
	 public static void logIn(Scanner sc) {
		  System.out.println("Enter username");
			String username = sc.next();
			System.out.println("Enter password");
			String password = sc.next();
			IInstructorService i = new InstructorServiceImpl();
			try {
				i.login(username, password);
				System.out.println("Log in Sccessfully");
				instructorMenu(sc);
			} catch (NoRecordFoundException e) {
				
				e.printStackTrace();
			}
	 }
	 public static void createCourse(Scanner scanner) {
		    System.out.println("Enter the course name: ");
		    String courseName = scanner.next();

		    System.out.println("Enter the instructor's Id: ");
		    int instructorId = scanner.nextInt();
		    
		    try {
		        Course course = new Course();
		        course.setName(courseName);
		        ins.createCourse(course, instructorId);
		        System.out.println("Course created successfully.");
		    } catch (SomethingWentWrongException e) {
		        System.err.println("Error: " + e.getMessage());
		    }
		}

	 public static void updateCourse(Scanner scanner) {
		    System.out.println("Enter the new course name: ");
		    String newCourseName = scanner.next();

		    System.out.println("Enter the course Id: ");
		    int courseId = scanner.nextInt();

		    System.out.println("Enter the instructor's Id: ");
		    int instructorId = scanner.nextInt();

		    try {
		        Course updatedCourse = new Course();
		        updatedCourse.setName(newCourseName);

		        ins.updateCourse(updatedCourse, courseId, instructorId);
		        System.out.println("Course updated successfully.");
		    } catch (SomethingWentWrongException | NoRecordFoundException e) {
		        System.err.println("Error: " + e.getMessage());
		    }
		}
	 public static List<Course> getCoursesByInstructorId(Scanner scanner) {
		    System.out.println("Enter the instructor's Id: ");
		    int instructorId = scanner.nextInt();
		    
		    List<Course> courses = null;
		    
		    
		    IInstructorService ins = new InstructorServiceImpl(); 
		    
		    try {
		        courses = ins.getCoursesByInstructorId(instructorId);
		        System.out.println("Courses retrieved successfully:");
		        for (Course course : courses) {
		            System.out.println(course.getName());
		        }
		    } catch (SomethingWentWrongException e) {
		        System.err.println("Error: " + e.getMessage());
		    }
		    
		    return courses;
		}
	 public static void deleteCourse(Scanner scanner) {
		    System.out.println("Enter the course Id to delete: ");
		    int courseId = scanner.nextInt();

		    try {
		    	IInstructorService ins = new InstructorServiceImpl(); 
		        ins.deleteCourse(courseId);
		        
		        System.out.println("Course deleted successfully.");
		    } catch (SomethingWentWrongException | NoRecordFoundException e) {
		        System.err.println("Error: " + e.getMessage());
		    }
		}
	 public static void createAssignment(Scanner scanner) {
	        System.out.println("Enter the course Id to assign: ");
	        int courseId = scanner.nextInt();

	        System.out.println("Enter the student Id to assign: ");
	        int studentId = scanner.nextInt();

	        System.out.println("Enter the assignment name: ");
	        String assignmentName = scanner.next();

	        try {
	            IInstructorService ins = new InstructorServiceImpl();
	            Assignment assignment = new Assignment();
	            assignment.setName(assignmentName);
	            ins.createAssignment(assignment, courseId, studentId);

	            System.out.println("Assignment Created successfully.");
	        } catch (SomethingWentWrongException e) {
	            System.err.println("Error: " + e.getMessage());
	        }
	    }
	 public static void getAssignmentsByCourseId(Scanner scanner) {
		    System.out.println("Enter the course ID: ");
		    int courseId = scanner.nextInt();

		    try {
		        IInstructorService ins = new InstructorServiceImpl();
		        List<Assignment> assignments = ins.getAssignmentsByCourseId(courseId);

		        if (assignments == null) {
		            System.out.println("Failed to retrieve assignments for the given course ID.");
		        } else if (assignments.isEmpty()) {
		            System.out.println("No assignments found for the given course ID.");
		        } else {
		            System.out.println("Assignments for Course ID " + courseId + ":");
		            for (Assignment assignment : assignments) {
		                System.out.println("Assignment ID: " + assignment.getId());
		                System.out.println("Assignment Name: " + assignment.getName());
		            }
		        }
		    } catch (SomethingWentWrongException e) {
		        System.err.println("Error: " + e.getMessage());
		    }
		}
	 
	 
	 public static void deleteAssignment(Scanner scanner) {
		    IInstructorService instructorService = new InstructorServiceImpl();

		    
		    System.out.println("Enter the Assignment ID to delete:");
		    int assignmentId = scanner.nextInt();

		    try {
	
		        instructorService.deleteAssignment(assignmentId);
		        System.out.println("Assignment deleted successfully.");
		        System.out.println("Deleted Assignment ID: " + assignmentId);
		    } catch (SomethingWentWrongException | NoRecordFoundException e) {
		        System.err.println("Error: " + e.getMessage());
		    }
		}
	 public static void updateAssignment(Scanner scanner) {
		    IInstructorService instructorService = new InstructorServiceImpl();

		
		    System.out.println("Enter the Assignment ID to update:");
		    int assignmentId = scanner.nextInt();

		    System.out.println("Enter Student Id : ");
		    int studentId = scanner.nextInt();

		    System.out.println("Enter Course Id : ");
		    int courseId = scanner.nextInt();

		    System.out.println("Enter New Assignment Name : ");
		    String newAssName = scanner.next();

		    try {
		        Assignment assignment = new Assignment();
		        assignment.setName(newAssName);

		    
		        Student student = new Student();
		        student.setId(studentId);
		        assignment.setStudent(student);


		     
		        instructorService.updateAssignment(assignment, assignmentId, courseId, studentId);
		        System.out.println("Assignment updated successfully.");
		    } catch (SomethingWentWrongException | NoRecordFoundException e) {
		        System.err.println("Error: " + e.getMessage());
		    }
		}




	 public static void instructorMenu(Scanner sc) {
		    IInstructorService is = new InstructorServiceImpl();
		    int c;

		    do {
		        System.out.println("1. Create Course");
		        System.out.println("2. Update Course");
		        System.out.println("3. Get Course By instructor's Id ");
		        System.out.println("4. Delete Course ");
		        System.out.println("5. Create Assingment ");
		        System.out.println("6. Get Assingments");
		        System.out.println("7. Delete Assignment ");
		        System.out.println("8. Update Assignment ");
		        
		        System.out.println("0. Back to Main Menu");

		        c = sc.nextInt();
		        switch (c) {
		            case 1:
		                createCourse(sc);
		                break;
		            case 2:
                       updateCourse(sc);
		                break;
		            case 3:
		                getCoursesByInstructorId(sc);
		                break;
		            case 4:
		               deleteCourse(sc);
		                break;
		            case 5:
		            	createAssignment(sc);
		            	break;
		            case 6:
		            	getAssignmentsByCourseId(sc);
		            	break;
		            case 7:
		               deleteAssignment(sc);
		                break;
		            case 8:
		                updateAssignment(sc);
		                break;
		            case 9:
		                // Handle other options
		                break;
		        }
		    } while (c != 0);
		}

		   
	
}
