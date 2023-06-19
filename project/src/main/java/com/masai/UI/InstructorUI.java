package com.masai.UI;

import java.time.LocalDate;
import java.util.Scanner;

import javax.management.IntrospectionException;

import com.masai.EMUtils.Utils;
import com.masai.Entities.Assignment;
import com.masai.Entities.Course;
import com.masai.Entities.Instructor;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;
import com.masai.Services.IInstructorService;
import com.masai.Services.InstructorServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;


public class InstructorUI {
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

		    System.out.println("Enter the instructor's name: ");
		    String instructorName = scanner.next();

		    Course course = new Course();
		    course.setName(courseName);

		    InstructorServiceImpl instructorService = new InstructorServiceImpl();
		    try {
		        // Fetch the instructor by name
		        Instructor instructor = instructorService.getInstructorByName(instructorName);
		        if (instructor != null) {
		            // Set the retrieved instructor for the course
		            course.setInstructor(instructor);

		            // Create the course
		            instructorService.createCourse(course);

		            System.out.println("Course added successfully");
		        } else {
		            System.out.println("Instructor not found");
		        }
		    } catch (SomethingWentWrongException e) {
		        System.out.println("Failed to create course: " + e.getMessage());
		    }
		}



	public static void instructorMenu(Scanner sc) {
		IInstructorService is = new InstructorServiceImpl();
	    int c;
	   
	    do {

	        System.out.println("1. Create Course");
	        System.out.println("2. Create Assingment");
	        
	        
	        c = sc.nextInt();
	        switch(c) {
	         
	            case 1: 
				createCourse(sc);
	                break;
	            case 2:
	                System.out.println("Enter assignment name:");
	                String assignmentName = sc.next();

	                System.out.println("Enter course name:");
	                String courseName = sc.next();

	               
	                Assignment assignment = new Assignment();
	                assignment.setName(assignmentName);
                
	            InstructorServiceImpl im = new InstructorServiceImpl();
	            		
	                Course course = im.findByName(courseName);

	               
	                assignment.setCourse(course);

	                try {
	                    is.createAssignment(assignment, course);
	                    System.out.println("Assignment created successfully.");
	                } catch (SomethingWentWrongException e) {
	                    e.printStackTrace();
	                    System.out.println("Failed to create the assignment.");
	                }
	                break;

	        }
	    } while(c != 0);
	}


}
