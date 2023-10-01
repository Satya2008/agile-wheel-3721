package com.masai.UI;

import java.util.List;
import java.util.Scanner;

import com.masai.Entities.Administrator;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.SomethingWentWrongException;
import com.masai.Services.AdministratorServiceImpl;
import com.masai.Services.IAdministratorService;

public class AdministratorUI {
	
	public static void logIn(Scanner sc) {
		
		System.out.println("Enter admistrator user name");
		String username = sc.next();
		System.out.println("Enter admistrator password");
		String password = sc.next();
		
		IAdministratorService ad = new AdministratorServiceImpl();
		try {
			ad.login(username, password);
			administratorMenu(sc);
		} catch (SomethingWentWrongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Admintrator Log in successfully");
	}
	
	public static void signUp(Scanner sc)  {
		System.out.println("Enter admistrator user name");
		String username = sc.next();
		System.out.println("Enter admistrator password");
		String password = sc.next();
		Administrator adm = new Administrator();
		adm.setUsername(username);
		adm.setPassword(password);
		IAdministratorService ad = new AdministratorServiceImpl();
		try {
			ad.addAdminstrator(adm);
			
		} catch (SomethingWentWrongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		System.out.println("Adminstrator signUp successfully");
		
		
	}
	
	  public static void getAllStudents() {
	        IAdministratorService ad = new AdministratorServiceImpl();
	        try {
	            // Retrieve the list of students
	            List<Student> students = ad.getAllStudent();
	            
	            // Print the list of students
	            for (Student student : students) {
	                System.out.println("Student ID: " + student.getId());
	                System.out.println("Student Name: " + student.getName());
	              
	                System.out.println("------------------------");
	            }
	        } catch (SomethingWentWrongException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void getAllInstructors() {
	        IAdministratorService ad = new AdministratorServiceImpl();
	        try {
	            // Retrieve the list of instructors
	            List<Instructor> instructors = ad.getAllInstructor();
	            
	            // Print the list of instructors
	            for (Instructor instructor : instructors) {
	                System.out.println("Instructor ID: " + instructor.getId());
	                System.out.println("Instructor Name: " + instructor.getName());
	                // Add other instructor details here
	                System.out.println("------------------------");
	            }
	        } catch (SomethingWentWrongException e) {
	            e.printStackTrace();
	        }
	    }
	
	public static void administratorMenu(Scanner sc) {
		AdministratorUI ad = new AdministratorUI();
		int i; 
		do {
			System.out.println("1. Get All Student ");
			System.out.println("2. Get All Intructor ");
		i = sc.nextInt();	
		switch (i) {
        case 1:
           getAllStudents();
            break;
        case 2:
            getAllInstructors();
            break;
		}
		}while(i!=0);
		
		
	}
	
	
	 

}
