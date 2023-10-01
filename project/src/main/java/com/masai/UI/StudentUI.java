package com.masai.UI;

import java.time.LocalDate;
import java.util.Scanner;

import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;
import com.masai.Services.IStudentService;
import com.masai.Services.StudentServiceImpl;

public class StudentUI {
	 private static Student loggedInStudent;
	 private static IStudentService studentService = new StudentServiceImpl();
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
		Student st = new Student();
		st.setUsername(username);
		st.setPassword(password);
		st.setName(name);
		st.setDateOfBirth(DOB);
		st.setMobileNo(mobileNo);

		IStudentService stu = new StudentServiceImpl();
		try {
			stu.singUp(st);
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

	        try {
	            loggedInStudent = studentService.login(username, password);
	            System.out.println("Log in Successfully");
	            StudentMenuAfterLogIn(sc);
	        } catch (NoRecordFoundException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	   static void submitAssignment(Scanner sc) {
		   System.out.println("Enter Student ID");
		   int studentid = sc.nextInt();
	
		   System.out.println("Enter Assignment ID");
		   int assignmentId = sc.nextInt();
		   IStudentService st = new StudentServiceImpl();
		    try {
				st.submitAssignments(studentid, assignmentId);
				
			} catch (SomethingWentWrongException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		   
		   
	   }
	   
	   static void accessLecture(Scanner sc) {
		   System.out.println("Enter Student username");
		   String username = sc.next();
	
		   System.out.println("Enter Student Password");
		   String password = sc.next();
		   IStudentService st = new StudentServiceImpl();
		    try {
				st.accessLectures(username, password);
				
			} catch (SomethingWentWrongException | NoRecordFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		   
		   
	   }

		static void accessVideo(Scanner sc) {
			System.out.println("Enter Student username");
			String username = sc.next();

			System.out.println("Enter Student Password");
			String password = sc.next();
			IStudentService st = new StudentServiceImpl();
			try {
				st.accessVideos(username, password);

			} catch (SomethingWentWrongException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

		}
		
		 static void accessReading(Scanner sc) {
			   System.out.println("Enter Student username");
			   String username = sc.next();
		
			   System.out.println("Enter Student Password");
			   String password = sc.next();
			   IStudentService st = new StudentServiceImpl();
			    try {
					st.accessReadings(username, password);
					
				} catch (SomethingWentWrongException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			   
			   
		   }
		
	   static void StudentMenuAfterLogIn(Scanner scanner) {
		    System.out.println("--- Student Menu ---");
		    System.out.println("1. Access Assignments");
		    System.out.println("2. Submit Assignment");
		    System.out.println("3. Track Progress");
		    System.out.println("4. Access Lectures");
		    System.out.println("5. Access Readings");
		    System.out.println("6. Access Videos");
		    System.out.println("7. View Enrolled Courses");
		    System.out.println("8. View Grades");
		    System.out.println("9. View Discussions");
		    System.out.println("10. Create Discussion Post");
		    System.out.println("11. View Discussion Posts");
		    System.out.println("0. Back to Main Menu");
		    System.out.print("Enter your choice: ");
		    int choice = scanner.nextInt();
		    scanner.nextLine();

		   IStudentService studentService = new StudentServiceImpl();
		    switch (choice) {
		        case 1:
		            studentService.accessAssignments(loggedInStudent);
		            break;
		        case 2:
		          submitAssignment(scanner);
		            break;
		        case 3:
//		            studentService.trackProgress();
		            break;
		        case 4:
		           accessLecture(scanner);
		            break;
		        case 5:
		           accessReading(scanner);
		            break;
		        case 6:
		          accessVideo(scanner);
		            break;
		        case 7:
//		            List<Course> enrolledCourses = studentService.getEnrolledCourses(loggedInStudent);
//		            // Display enrolled courses
		            break;
		        case 8:
//		            List<Grade> grades = studentService.getGradesByStudent(loggedInStudent);
//		            // Display grades
		            break;
		        case 9:
//		            List<Discussion> discussions = studentService.getDiscussionsByCourse(loggedInStudent.getCourse());
//		            // Display discussions
		            break;
		        case 10:
//		            System.out.println("Enter the content for the discussion post: ");
//		            String content = scanner.nextLine();
//		            studentService.createDiscussionPost(loggedInStudent, loggedInStudent.getCourse(), content);
		            break;
		        case 11:
//		            List<Discussion> discussionPosts = studentService.getDiscussionPostsByDiscussion(loggedInStudent.getDiscussion());
		            // Display discussion posts
		            break;
		        case 0:
		            break;
		        default:
		            System.out.println("Invalid choice. Please try again.");
		            break;
		    }
		}


}
